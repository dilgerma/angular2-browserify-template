import {Inject, Injectable} from "@angular/core";
import {ApplicationState} from "../service/ApplicationState";
import {Response} from "@angular/http";
import {LoggerService} from "../support/LoggerService";
import {LinkService} from "../support/LinkService";

@Injectable()
export class EventsService {

    constructor(private linkService: LinkService,
                @Inject("Window") private window: Window,
                private applicationState: ApplicationState,
                private loggerService: LoggerService) {
    }

    public requestEvents() {
        this.loggerService.debug("Request triggered");
        var res: Promise<Response> = this.linkService.call((<any>this.window).pageResource, "view");
        res.then((r) => {
            var payload = r.json();
            this.loggerService.info("received response {0}", [payload]);
            this.applicationState.entries = payload;

        }).catch((r) => {
            this.loggerService.warn("Received error {0}", [r])
        });
    }
}