"""
Starts threads for listening to incoming logs.
Supports simultaneous reading of OS (Windows or Linux) logs and additional application logs.
"""

import os
import sys
from threading import Thread

from reader import file_log_reader
from util import config_loader

if os.name == 'nt':
    from reader import windows_log_reader


def start_thread(target, name, args):
    thread = Thread(target=target, name=name, args=args)
    thread.daemon = False
    thread.start()


def main():
    if len(sys.argv) < 2:
        print('Missing command line argument: path to config file!')
        return

    config_path = sys.argv[1]

    config = config_loader.load(config_path)
    api_url = config['api-url']
    read_os_logs = config['read-os-logs']
    logs = config['logs']

    if logs is None:
        logs = []

    for log in logs:
        log['os_log'] = False

    if read_os_logs:
        os_logs_regex = config['os-logs-regex']
        if os.name == 'nt':
            print('Listening for changes on Windows security logs')
            start_thread(windows_log_reader.read_logs, 'windows log reader', (api_url, os_logs_regex))
        else:
            os_logs_interval = config['os-logs-interval']
            logs.append(
                {'path': '/var/log/auth.log', 'regex': os_logs_regex, 'interval': os_logs_interval, 'os_log': True})

    if logs:
        start_thread(file_log_reader.read_logs, 'text log reader', (api_url, logs))


if __name__ == '__main__':
    main()
