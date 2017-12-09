import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Customerorder } from './customerorder.model';
import { CustomerorderService } from './customerorder.service';

@Component({
    selector: 'jhi-customerorder-detail',
    templateUrl: './customerorder-detail.component.html'
})
export class CustomerorderDetailComponent implements OnInit, OnDestroy {

    customerorder: Customerorder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private customerorderService: CustomerorderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerorders();
    }

    load(id) {
        this.customerorderService.find(id).subscribe((customerorder) => {
            this.customerorder = customerorder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerorders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerorderListModification',
            (response) => this.load(this.customerorder.id)
        );
    }
}
