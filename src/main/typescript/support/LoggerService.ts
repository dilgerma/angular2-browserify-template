import {Injectable} from "@angular/core";
import {LogConfig} from "./LogConfig";
@Injectable()
export class LoggerService {

    constructor(private logConfig:LogConfig) {}

    public log(msg: string, params: any[]) {
        if (params == undefined) {
            params = [];
        }
        for (var i = 0; i < params.length; i++) {
            var replace = '{' + i + '}';
            msg = msg.replace(replace, params[i]);
        }
        console.log(msg);
    }

    public debug(msg: string, params?: any[]) {
        if(this.logConfig.debugEnabled) {
            this.log(msg, params);
        }
    }

    public info(msg: string, params?: any[]) {
        this.log(msg, params);
    }

    public warn(msg: string, params?: any[]) {
        this.log(msg, params);
    }

    public error(msg: string, params?: any[]) {
        this.log(msg, params);
    }
}