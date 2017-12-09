import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Customerorder } from './customerorder.model';
import { CustomerorderPopupService } from './customerorder-popup.service';
import { CustomerorderService } from './customerorder.service';

@Component({
    selector: 'jhi-customerorder-delete-dialog',
    templateUrl: './customerorder-delete-dialog.component.html'
})
export class CustomerorderDeleteDialogComponent {

    customerorder: Customerorder;

    constructor(
        private customerorderService: CustomerorderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerorderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerorderListModification',
                content: 'Deleted an customerorder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customerorder-delete-popup',
    template: ''
})
export class CustomerorderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerorderPopupService: CustomerorderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerorderPopupService
                .open(CustomerorderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
