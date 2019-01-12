export class LogQuery {

    timestampStart: string;

    timestampEnd: string;

    ipAddress: string;

    hostName: string;

    sourceName: string;

    processId: string;

    facility: string;

    severity: string;

    system: string;

    message: string;

    regex: string;

    constructor() {
        const now = new Date();

        //this.timestampStart = new Date(now.getTime() - now.getTimezoneOffset() * 60000).toISOString().substring(0, 16);
        //this.timestampEnd = new Date(now.getTime() - now.getTimezoneOffset() * 60000).toISOString().substring(0, 16);
    }

    getFormatted(): LogQuery {
        const formatted = JSON.parse(JSON.stringify(this));

        try {
            formatted.timestampStart = new Date(this.timestampStart).toISOString();
        } catch (err) {
            formatted.timestampStart = null;
        }

        try {
            formatted.timestampEnd = new Date(this.timestampEnd).toISOString();
        } catch (err) {
            formatted.timestampEnd = null
        }

        return formatted;
    }
}
