export class ReportRequest {

    startDate: Date;

    endDate: Date;

    getFormatted(): ReportRequest {
        const formatted = JSON.parse(JSON.stringify(this));

        try {
            formatted.startDate = new Date(this.startDate).toISOString();
        } catch (err) {
            formatted.startDate = null;
        }

        try {
            formatted.endDate = new Date(this.endDate).toISOString();
        } catch (err) {
            formatted.endDate = null
        }

        return formatted;
    }
}
