import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Customerorder } from './customerorder.model';
import { CustomerorderService } from './customerorder.service';

@Injectable()
export class CustomerorderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private customerorderService: CustomerorderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.customerorderService.find(id).subscribe((customerorder) => {
                    customerorder.orderdate = this.datePipe
                        .transform(customerorder.orderdate, 'yyyy-MM-ddTHH:mm:ss');
                    customerorder.orderfullfilled = this.datePipe
                        .transform(customerorder.orderfullfilled, 'yyyy-MM-ddTHH:mm:ss');
                    customerorder.paymentdate = this.datePipe
                        .transform(customerorder.paymentdate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.customerorderModalRef(component, customerorder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.customerorderModalRef(component, new Customerorder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    customerorderModalRef(component: Component, customerorder: Customerorder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerorder = customerorder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
