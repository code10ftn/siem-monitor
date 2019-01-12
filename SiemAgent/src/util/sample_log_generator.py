"""
Periodically writes logs for a simulated mobile banking API server.
"""

import logging
import os
import random
import sys
from time import sleep


def write_log(severity, message):
    if severity == 'DEBUG':
        logging.debug(message)
    elif severity == 'INFO':
        logging.info(message)
    elif severity == 'WARNING':
        logging.warning(message)
    elif severity == 'ERROR':
        logging.error(message)
    elif severity == 'CRITICAL':
        logging.critical(message)

    logger = logging.getLogger()
    for handler in logger.handlers:
        handler.flush()
        handler.close()


def generate_log(log_samples):
    log_sample = random.choice(log_samples).strip()
    # Format the string by replacing each pair of brackets with a random number:
    num_placeholders = log_sample.count('{}')
    log_sample = log_sample.format(*[random.randint(1, 9999) for _ in range(num_placeholders)])
    return tuple(log_sample.split(':'))


def main():
    if len(sys.argv) < 2:
        print('Missing command line argument 1: path to sample log file!')
        return

    path = sys.argv[1]

    root_path = os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__))))
    log_samples_path = os.path.join(root_path, 'resources', 'log_samples.txt')

    with open(log_samples_path) as f:
        log_samples = f.readlines()

    logging.basicConfig(filename=path, level=logging.NOTSET)

    while True:
        sleep(random.random() * 10)
        severity, message = generate_log(log_samples)
        print('{}: {}'.format(severity, message))
        write_log(severity, message)


if __name__ == '__main__':
    main()
