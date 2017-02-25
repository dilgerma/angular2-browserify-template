import {
    Component, ElementRef, ViewChild,
    AfterViewInit, AfterContentChecked, OnInit, OnChanges, SimpleChanges, AfterViewChecked
} from "@angular/core";
import * as $ from "jquery";
import {Observable, Subject} from "rxjs";

@Component({
    selector: 'app',
    template: `Hallo Angular World`
})
export class AppComponent implements OnInit, AfterViewInit{

    constructor() {
    }

    ngOnInit(): void{
    }

    ngAfterViewInit(): void {
    }

}
