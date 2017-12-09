import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderDetails } from './order-details.model';
import { OrderDetailsPopupService } from './order-details-popup.service';
import { OrderDetailsService } from './order-details.service';

@Component({
    selector: 'jhi-order-details-delete-dialog',
    templateUrl: './order-details-delete-dialog.component.html'
})
export class OrderDetailsDeleteDialogComponent {

    orderDetails: OrderDetails;

    constructor(
        private orderDetailsService: OrderDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderDetailsListModification',
                content: 'Deleted an orderDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-details-delete-popup',
    template: ''
})
export class OrderDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderDetailsPopupService: OrderDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderDetailsPopupService
                .open(OrderDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
