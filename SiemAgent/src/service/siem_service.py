import os
import re
import platform

import requests_pkcs12 as requests

KEY_STORE_PATH = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__)))),
                              'resources', 'agentKeyStore.p12')
CA_CERT_PATH = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__)))),
                            'resources', 'server.pem')


def parse_log(url, regex, log):
    log.system = platform.system()
    if re.match(regex, str(log)):
        payload = log.__dict__
        r = requests.post(url, json=payload,
                          pkcs12_filename=KEY_STORE_PATH,
                          pkcs12_password=os.environ['CODE10_SIEM_CERT_CLIENT_KEY_PASS'],
                          verify=CA_CERT_PATH)
        print(log.timestamp + "\t" + r.text)
