import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    CustomerorderService,
    CustomerorderPopupService,
    CustomerorderComponent,
    CustomerorderDetailComponent,
    CustomerorderDialogComponent,
    CustomerorderPopupComponent,
    CustomerorderDeletePopupComponent,
    CustomerorderDeleteDialogComponent,
    customerorderRoute,
    customerorderPopupRoute,
    CustomerorderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerorderRoute,
    ...customerorderPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CustomerorderComponent,
        CustomerorderDetailComponent,
        CustomerorderDialogComponent,
        CustomerorderDeleteDialogComponent,
        CustomerorderPopupComponent,
        CustomerorderDeletePopupComponent,
    ],
    entryComponents: [
        CustomerorderComponent,
        CustomerorderDialogComponent,
        CustomerorderPopupComponent,
        CustomerorderDeleteDialogComponent,
        CustomerorderDeletePopupComponent,
    ],
    providers: [
        CustomerorderService,
        CustomerorderPopupService,
        CustomerorderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterCustomerorderModule {}
