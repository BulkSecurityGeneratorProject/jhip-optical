import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CustomerorderComponent } from './customerorder.component';
import { CustomerorderDetailComponent } from './customerorder-detail.component';
import { CustomerorderPopupComponent } from './customerorder-dialog.component';
import { CustomerorderDeletePopupComponent } from './customerorder-delete-dialog.component';

@Injectable()
export class CustomerorderResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const customerorderRoute: Routes = [
    {
        path: 'customerorder',
        component: CustomerorderComponent,
        resolve: {
            'pagingParams': CustomerorderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Customerorders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customerorder/:id',
        component: CustomerorderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Customerorders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerorderPopupRoute: Routes = [
    {
        path: 'customerorder-new',
        component: CustomerorderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Customerorders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customerorder/:id/edit',
        component: CustomerorderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Customerorders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customerorder/:id/delete',
        component: CustomerorderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Customerorders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
