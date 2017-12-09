import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Customerorder } from './customerorder.model';
import { CustomerorderPopupService } from './customerorder-popup.service';
import { CustomerorderService } from './customerorder.service';
import { Customer, CustomerService } from '../customer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customerorder-dialog',
    templateUrl: './customerorder-dialog.component.html'
})
export class CustomerorderDialogComponent implements OnInit {

    customerorder: Customerorder;
    isSaving: boolean;

    customers: Customer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private customerorderService: CustomerorderService,
        private customerService: CustomerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => { this.customers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerorder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerorderService.update(this.customerorder));
        } else {
            this.subscribeToSaveResponse(
                this.customerorderService.create(this.customerorder));
        }
    }

    private subscribeToSaveResponse(result: Observable<Customerorder>) {
        result.subscribe((res: Customerorder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Customerorder) {
        this.eventManager.broadcast({ name: 'customerorderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customerorder-popup',
    template: ''
})
export class CustomerorderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerorderPopupService: CustomerorderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerorderPopupService
                    .open(CustomerorderDialogComponent as Component, params['id']);
            } else {
                this.customerorderPopupService
                    .open(CustomerorderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
