
import {Component, Input} from "@angular/core";
import {Entry} from "../../domain/Entry";
import {ApplicationState} from "../../service/ApplicationState";
@Component({
    "selector" : "line-entry",
    templateUrl: "src/main/typescript/components/entry/lineEntryTemplate.html"
})
export class LineEntryCompoent {

    @Input()
    entry:Entry;

    constructor(public applicationState: ApplicationState) {
    }

    public applyDescription(entry: Entry) {
        this.applicationState.currentlyDisplayedDescription = entry.description;
    }
}