import {Entry} from "../domain/Entry";
import {Injectable} from "@angular/core";
import {Exercise} from "../domain/Exercise";

@Injectable()
export class ApplicationState {

    public entries:Entry[] = [];
    public currentlyDisplayedDescription:string;
    public exercises:Exercise[];
}