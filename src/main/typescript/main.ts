"use strict";

import * as $ from "jquery";
(<any>global)['jQuery'] = $;

import "es6-shim";
import "zone.js";
import "reflect-metadata";
import "rxjs/Rx";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./components/app-component";
import {LinkService} from "./support/LinkService";
import {LoggerService} from "./support/LoggerService";
import {EventsService} from "./service/EventsService";
import {ApplicationState} from "./service/ApplicationState";
import {LogConfig} from "./support/LogConfig";

@NgModule({
    imports: [BrowserModule, HttpModule],
    declarations: [AppComponent],
    bootstrap: [AppComponent],
    providers: [LogConfig,  LinkService, ApplicationState, EventsService, LoggerService, {provide: 'Window', useValue: window}]

})
class AppModule {
}

document.addEventListener('DOMContentLoaded', function () {
    platformBrowserDynamic()
        .bootstrapModule(AppModule);
});
