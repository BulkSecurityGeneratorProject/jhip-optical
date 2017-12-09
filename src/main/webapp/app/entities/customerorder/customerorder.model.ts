import { BaseEntity } from './../../shared';

export const enum PaymentType {
    'CREDITCARD',
    'CASH',
    'DEBITCARD'
}

export const enum OrderStatus {
    'NEW',
    'INPROGESS',
    'COMPLETED',
    'ONHOLD'
}

export class Customerorder implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public orderdate?: any,
        public orderfullfilled?: any,
        public paymentype?: PaymentType,
        public paymentdate?: any,
        public totalAmount?: number,
        public orderStatus?: OrderStatus,
        public orderdetails?: BaseEntity[],
        public customer?: BaseEntity,
    ) {
    }
}
