import { LogItem } from './log-item.model';
import { Rule } from './rule.model';

export class Alarm {

    id: number;

    timestamp: Date;

    rule: Rule;

    logs = new Array<LogItem>();
}
