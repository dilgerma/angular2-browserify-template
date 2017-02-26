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

@Component({
    selector: 'app',
    // template: '<a (click)=submit()>sendRequest</a>'
    templateUrl: 'src/main/typescript/components/app-component.html'
})
export class AppComponent implements OnInit, AfterViewInit{

    constructor(private eventService:EventsService, private loggerService:LoggerService, public applicationState:ApplicationState,private ngZone: NgZone) {
        Observable.interval(2000) q
                         .subscribe((x) => {
                             this.ngZone.run(()=>this.submit());
                         });
    }

    public submit() {
        this.eventService.requestEvents();
    }


    ngOnInit(): void{
    }

    ngAfterViewInit(): void {
    }

}
