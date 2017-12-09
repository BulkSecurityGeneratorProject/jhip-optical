import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OrderDetails } from './order-details.model';
import { OrderDetailsService } from './order-details.service';

@Component({
    selector: 'jhi-order-details-detail',
    templateUrl: './order-details-detail.component.html'
})
export class OrderDetailsDetailComponent implements OnInit, OnDestroy {

    orderDetails: OrderDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderDetailsService: OrderDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderDetails();
    }

    load(id) {
        this.orderDetailsService.find(id).subscribe((orderDetails) => {
            this.orderDetails = orderDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderDetailsListModification',
            (response) => this.load(this.orderDetails.id)
        );
    }
}
