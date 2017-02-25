
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
import {AppComponent} from "./components/app.component";

@NgModule({
    imports: [ BrowserModule, HttpModule],
    bootstrap: [AppComponent]
})
class AppModule {
}


document.addEventListener('DOMContentLoaded', function () {
    platformBrowserDynamic()
        .bootstrapModule(AppModule);
});
