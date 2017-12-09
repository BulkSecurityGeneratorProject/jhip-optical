import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Customerorder } from './customerorder.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CustomerorderService {

    private resourceUrl = SERVER_API_URL + 'api/customerorders';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/customerorders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(customerorder: Customerorder): Observable<Customerorder> {
        const copy = this.convert(customerorder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(customerorder: Customerorder): Observable<Customerorder> {
        const copy = this.convert(customerorder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Customerorder> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Customerorder.
     */
    private convertItemFromServer(json: any): Customerorder {
        const entity: Customerorder = Object.assign(new Customerorder(), json);
        entity.orderdate = this.dateUtils
            .convertDateTimeFromServer(json.orderdate);
        entity.orderfullfilled = this.dateUtils
            .convertDateTimeFromServer(json.orderfullfilled);
        entity.paymentdate = this.dateUtils
            .convertDateTimeFromServer(json.paymentdate);
        return entity;
    }

    /**
     * Convert a Customerorder to a JSON which can be sent to the server.
     */
    private convert(customerorder: Customerorder): Customerorder {
        const copy: Customerorder = Object.assign({}, customerorder);

        copy.orderdate = this.dateUtils.toDate(customerorder.orderdate);

        copy.orderfullfilled = this.dateUtils.toDate(customerorder.orderfullfilled);

        copy.paymentdate = this.dateUtils.toDate(customerorder.paymentdate);
        return copy;
    }
}
