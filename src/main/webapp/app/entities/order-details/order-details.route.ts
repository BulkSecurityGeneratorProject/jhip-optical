import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrderDetailsComponent } from './order-details.component';
import { OrderDetailsDetailComponent } from './order-details-detail.component';
import { OrderDetailsPopupComponent } from './order-details-dialog.component';
import { OrderDetailsDeletePopupComponent } from './order-details-delete-dialog.component';

export const orderDetailsRoute: Routes = [
    {
        path: 'order-details',
        component: OrderDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-details/:id',
        component: OrderDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderDetailsPopupRoute: Routes = [
    {
        path: 'order-details-new',
        component: OrderDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-details/:id/edit',
        component: OrderDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-details/:id/delete',
        component: OrderDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
