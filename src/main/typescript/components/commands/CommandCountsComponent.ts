
import {Component} from "@angular/core";
import {ApplicationState} from "../../service/ApplicationState";
@Component({
    selector : "commands",
    templateUrl: "src/main/typescript/components/commands/commandCountsTemplate.html"
})
export class CommandCountsComponent {
 q
    constructor(public applicationState:ApplicationState) {}
}