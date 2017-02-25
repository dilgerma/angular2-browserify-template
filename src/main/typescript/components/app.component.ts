import {
    Component, AfterViewInit, OnInit
} from "@angular/core";

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
