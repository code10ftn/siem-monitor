class LogItem:
    def __init__(self):
        self.timestamp = ''
        self.host_name = ''
        self.source_name = ''
        self.process_id = ''
        self.facility = ''
        self.severity = ''
        self.system = ''
        self.message = ''

    def __str__(self):
        attributes = vars(self)
        return ' | '.join(str(item[1]) for item in attributes.items())
