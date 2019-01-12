import datetime
import os
import re
import time

from watchdog.events import PatternMatchingEventHandler
from watchdog.observers import Observer
from watchdog.observers.polling import PollingObserver

from model.log_item import LogItem
from service import siem_service
from util.utc_converter import local_to_utc


class LogEventHandler(PatternMatchingEventHandler):
    def __init__(self, api_url, regex, log_name):
        pattern = '*{}{}'.format(os.sep, log_name)
        super(LogEventHandler, self).__init__(patterns=[pattern], ignore_directories=True, case_sensitive=False)

        self._api_url = api_url
        self._regex = regex
        self.offset = -1

    def on_modified(self, event):
        path = event.src_path
        with open(path) as f:
            data = f.readlines()
            if self.offset > -1:
                for i in range(self.offset, len(data)):
                    line = data[i]
                    log = self.parse_line(line, path)
                    siem_service.parse_log(self._api_url, self._regex, log)
                self.offset = len(data)
            else:
                line = data[-1]
                log = self.parse_line(line, path)
                siem_service.parse_log(self._api_url, self._regex, log)

    def parse_line(self, line, path=''):
        log = LogItem()

        log.message = line

        return log


class TextLogEventHandler(LogEventHandler):
    def parse_line(self, line, path=''):
        log = LogItem()

        severity_pattern = re.compile(
            'debug|informational|information|info|notice|warning|warn|error|err|critical|crit|alert|emergency|emerg',
            re.IGNORECASE)
        severity_matcher = severity_pattern.search(line)
        severity = severity_matcher.group(0)
        line = line.replace(severity, '', 1)

        log.timestamp = datetime.datetime.utcnow().isoformat()
        log.source_name = path
        log.message = line
        log.severity = severity

        return log


class LinuxLogEventHandler(LogEventHandler):
    def parse_line(self, line, path=''):
        log = LogItem()

        tokens = [x.strip() for x in line.split('|', 7)]
        log.timestamp = str(local_to_utc(tokens[0]))
        log.host_name = tokens[1]
        log.source_name = tokens[2]
        log.process_id = tokens[3]
        log.facility = tokens[4]
        log.severity = tokens[5].upper()
        log.message = tokens[6]

        return log


def read_logs(api_url, logs):
    observers = []

    for log in logs:
        path = log['path']
        dir_name, log_name = os.path.split(path)
        print('Listening for changes on {} in {}'.format(log_name, dir_name))

        if log['os_log']:
            event_handler = LinuxLogEventHandler(api_url, log['regex'], log_name)
        else:
            event_handler = TextLogEventHandler(api_url, log['regex'], log_name)

        interval = log['interval']
        if interval > 0:
            observer = PollingObserver(interval)
            # Initialize the offset to the end of the file:
            event_handler.offset = sum(1 for _ in open(path))
        else:
            observer = Observer()

        observer.schedule(event_handler, dir_name, recursive=False)
        observer.start()
        observers.append(observer)

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        for observer in observers:
            observer.stop()

    for observer in observers:
        observer.join()
