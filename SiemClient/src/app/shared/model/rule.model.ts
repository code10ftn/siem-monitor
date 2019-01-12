export class Rule {

    id: number;

    name: string;

    interval: number;

    repetitions: number;

    priority = 'MEDIUM';

    logSystem: string;

    logIpAddress: string;

    logHostName: string;

    logSourceName: string;

    logProcessId: string;

    logFacility: string;

    logSeverity: string;

    logMessage: string;

    logRaw: string;

    logIpAddressRepeated: boolean;

    logHostNameRepeated: boolean;

    logSourceNameRepeated: boolean;

    logProcessIdRepeated: boolean;

    logFacilityRepeated: boolean;

    logSeverityRepeated: boolean;

    logMessageRepeated: boolean;

    logRawRepeated: boolean;
}
