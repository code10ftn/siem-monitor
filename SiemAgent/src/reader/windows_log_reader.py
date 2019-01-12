import win32event
import win32evtlog

from model.log_item import LogItem
from model.log_severity import LogSeverity
from service import siem_service
from util.utc_converter import local_to_utc

SEVERITY = {0: LogSeverity.EMERGENCY, 1: LogSeverity.CRITICAL, 2: LogSeverity.ERROR,
            3: LogSeverity.WARNING, 4: LogSeverity.INFO, 5: LogSeverity.DEBUG}


def read_logs(api_url, regex):
    server = 'localhost'
    logtype = 'Security'
    filehandler = win32evtlog.OpenEventLog(server, logtype)
    eventhandler = win32event.CreateEvent(None, 1, 0, 'wait')
    flags = win32evtlog.EVENTLOG_FORWARDS_READ | win32evtlog.EVENTLOG_SEQUENTIAL_READ
    win32evtlog.NotifyChangeEventLog(filehandler, eventhandler)

    cursorlog = win32evtlog.GetNumberOfEventLogRecords(filehandler)
    new_logs = skip_old_logs(cursorlog, filehandler, flags)
    map(lambda e: handle_event(e, api_url, regex), new_logs)

    while True:
        result = win32event.WaitForSingleObject(eventhandler, 1)
        if not result:
            cursorlog = win32evtlog.GetNumberOfEventLogRecords(filehandler)
            while True:
                readlog = win32evtlog.ReadEventLog(filehandler, flags, cursorlog)
                if len(readlog) == 0:
                    break
                for event in readlog:
                    handle_event(event, api_url, regex)


def handle_event(event, api_url, regex):
    log = LogItem()
    log.timestamp = str(local_to_utc(event.TimeGenerated.isoformat()))
    log.host_name = event.ComputerName
    log.source_name = event.SourceName
    log.process_id = event.EventID
    log.message = str(event.StringInserts)

    if event.EventType in SEVERITY:
        log.severity = SEVERITY[event.EventType].name
    else:
        log.severity = LogSeverity.UNKNOWN.name

    siem_service.parse_log(api_url, regex, log)


def skip_old_logs(log_count, filehandler, flags):
    """
    Due to a known bug on Windows:
    https://support.microsoft.com/en-us/help/177199/bug-readeventlog-fails-with-error-87
    we need our own implementation of file seeking.
    """
    curr_count = 0
    new_events = []
    found_new = False
    while not found_new and curr_count < log_count:
        readlog = win32evtlog.ReadEventLog(filehandler, flags, 0)

        for e in readlog:
            curr_count += 1
            if curr_count > log_count:
                found_new = True
                new_events.append(e)

    return new_events
