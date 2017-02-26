import {Entry} from "../domain/Entry";
import {Injectable} from "@angular/core";

@Injectable()
export class ApplicationState {

    public entries:Entry[] = [];
}