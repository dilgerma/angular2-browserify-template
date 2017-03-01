import {
    Component, AfterViewInit, OnInit, Inject, Output, NgZone
} from "@angular/core";
import {HttpModule, Http, Response} from "@angular/http";
import {LinkService} from "../support/LinkService";
import {Resource} from "../domain/Resource";
import {Observable} from "rxjs";
import {EventsService} from "../service/EventsService";
import {ApplicationState} from "../service/ApplicationState";
import {LoggerService} from "../support/LoggerService";
import {TimeElapsed} from "../filter/TimeElapsed";
import {Entry} from "../domain/Entry";

@Component({
    selector: 'app',
    templateUrl: 'src/main/typescript/components/app-component.html',
})
export class AppComponent implements OnInit, AfterViewInit {

    constructor(private eventService: EventsService, private loggerService: LoggerService, public applicationState: ApplicationState, private ngZone: NgZone) {
    }

    public submit() {
        this.eventService.requestEvents();
        this.eventService.requestExercises();
        this.eventService.requestCounts();
    }



    ngOnInit(): void {
    }

    ngAfterViewInit(): void {
        Observable.interval(5000)
            .subscribe((x) => {
                this.ngZone.run(() => this.submit());
            });

    }

}
