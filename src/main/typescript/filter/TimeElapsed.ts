
import {Pipe, PipeTransform} from "@angular/core";
import moment = require("moment");
import Diff = moment.unitOfTime.Diff;
@Pipe({name: 'timeElapsed'})
export class TimeElapsed implements PipeTransform{

    transform(value: Date): string {
        return '' + moment().diff(moment(value),'minutes');
     }
}