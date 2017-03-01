import {Inject, Injectable} from "@angular/core";
import {ApplicationState} from "../service/ApplicationState";
import {Response} from "@angular/http";
import {LoggerService} from "../support/LoggerService";
import {LinkService} from "../support/LinkService";
import {Entry} from "../domain/Entry";
import moment = require("moment");
import {Exercise} from "../domain/Exercise";

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
            this.applicationState.entries = payload.map((entry: any) => {
                return this.map(entry);
            });

        }).catch((r) => {
            this.loggerService.warn("Received error {0}", [r])
        });
    }

    public requestExercises() {
        this.loggerService.debug("Request triggered");
        var res: Promise<Response> = this.linkService.call((<any>this.window).pageResource, "exercises");
        res.then((r) => {
            var payload = r.json();
            this.loggerService.info("received response {0}", [payload]);

            var exercises = payload.map((exercise:any) => {
                var exercises:Entry[] = exercise.exercises.map((e:any) => this.map(e));
                return Object.assign({}, exercise, {
                    entries: exercises
                })
            })
            this.applicationState.exercises = exercises;

        }).catch((r) => {
            this.loggerService.warn("Received error {0}", [r])
        });
    }

    private map(entry: any) {

        let e: Entry = Object.assign({}, entry, {
            date: new Date(entry.localDateTime)
        });
        //new Entry(entry.content, new Date(entry.localDateTime), entry.lineNumber,);
        return e;
    }

    public requestCounts() {
        var res: Promise<Response> = this.linkService.call((<any>this.window).pageResource, "commands");
          res.then((r) => {
              var payload = r.json();
              this.loggerService.info("received response {0}", [payload]);

              this.applicationState.counts = payload;

          }).catch((r) => {
              this.loggerService.warn("Received error {0}", [r])
          });
    }
}