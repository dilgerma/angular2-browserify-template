
import {Http, RequestOptionsArgs, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Resource} from "../domain/Resource";
import {Link} from "../domain/Link";
import {log} from "util";
import {LoggerService} from "./LoggerService";
import {Observable} from "rxjs";

@Injectable()
export class LinkService {

    constructor(private http:Http, private log:LoggerService){}

    public call(resource:Resource, rel:string):Promise<Response> {
        var link:Link = this.linkRel(resource, rel);
        if(link != undefined) {
            var promise = this.http.get(link.href).toPromise();
            return promise;
        } else {
            this.log.warn("resource has no link of type rel: {0}", [rel]);
            return Promise.reject("invalid rel type, link has no link of type rel: " + rel)
        }
    }

    private linkRel(resource:Resource, rel:string): Link {
        return resource.links.find((link:Link) => link.rel === rel);
    }


}