import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    OrderDetailsService,
    OrderDetailsPopupService,
    OrderDetailsComponent,
    OrderDetailsDetailComponent,
    OrderDetailsDialogComponent,
    OrderDetailsPopupComponent,
    OrderDetailsDeletePopupComponent,
    OrderDetailsDeleteDialogComponent,
    orderDetailsRoute,
    orderDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...orderDetailsRoute,
    ...orderDetailsPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrderDetailsComponent,
        OrderDetailsDetailComponent,
        OrderDetailsDialogComponent,
        OrderDetailsDeleteDialogComponent,
        OrderDetailsPopupComponent,
        OrderDetailsDeletePopupComponent,
    ],
    entryComponents: [
        OrderDetailsComponent,
        OrderDetailsDialogComponent,
        OrderDetailsPopupComponent,
        OrderDetailsDeleteDialogComponent,
        OrderDetailsDeletePopupComponent,
    ],
    providers: [
        OrderDetailsService,
        OrderDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterOrderDetailsModule {}
