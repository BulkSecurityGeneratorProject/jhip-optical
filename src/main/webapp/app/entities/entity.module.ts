import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterCustomerModule } from './customer/customer.module';
import { JhipsterProductModule } from './product/product.module';
import { JhipsterCategoryModule } from './category/category.module';
import { JhipsterCustomerorderModule } from './customerorder/customerorder.module';
import { JhipsterOrderDetailsModule } from './order-details/order-details.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterCustomerModule,
        JhipsterProductModule,
        JhipsterCategoryModule,
        JhipsterCustomerorderModule,
        JhipsterOrderDetailsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
