import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderDetails } from './order-details.model';
import { OrderDetailsPopupService } from './order-details-popup.service';
import { OrderDetailsService } from './order-details.service';
import { Customerorder, CustomerorderService } from '../customerorder';
import { Product, ProductService } from '../product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-order-details-dialog',
    templateUrl: './order-details-dialog.component.html'
})
export class OrderDetailsDialogComponent implements OnInit {

    orderDetails: OrderDetails;
    isSaving: boolean;

    customerorders: Customerorder[];

    products: Product[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderDetailsService: OrderDetailsService,
        private customerorderService: CustomerorderService,
        private productService: ProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.customerorderService.query()
            .subscribe((res: ResponseWrapper) => { this.customerorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.productService.query()
            .subscribe((res: ResponseWrapper) => { this.products = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderDetailsService.update(this.orderDetails));
        } else {
            this.subscribeToSaveResponse(
                this.orderDetailsService.create(this.orderDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrderDetails>) {
        result.subscribe((res: OrderDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrderDetails) {
        this.eventManager.broadcast({ name: 'orderDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCustomerorderById(index: number, item: Customerorder) {
        return item.id;
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-details-popup',
    template: ''
})
export class OrderDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderDetailsPopupService: OrderDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderDetailsPopupService
                    .open(OrderDetailsDialogComponent as Component, params['id']);
            } else {
                this.orderDetailsPopupService
                    .open(OrderDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
