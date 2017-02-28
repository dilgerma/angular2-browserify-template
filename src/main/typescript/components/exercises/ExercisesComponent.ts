
import {Component} from "@angular/core";
import {ApplicationState} from "../../service/ApplicationState";

@Component({
    selector : "exercises",
    templateUrl: "src/main/typescript/components/exercises/exercisesTemplate.html"
})
export class ExercisesComponent {

    constructor(public applicationState: ApplicationState) { }

    exercises(){
        return this.applicationState.exercises;
    }
}