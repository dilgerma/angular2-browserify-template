
import {Component} from "@angular/core";
import {ApplicationState} from "../../service/ApplicationState";
import {Entry} from "../../domain/Entry";
@Component({
    selector: "description",
    templateUrl: "src/main/typescript/components/description/descriptionTemplate.html"
})
export class DescriptionComponent {

    constructor(public applicationState:ApplicationState) {}

}