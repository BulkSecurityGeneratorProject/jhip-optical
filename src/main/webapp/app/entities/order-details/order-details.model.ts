import { BaseEntity } from './../../shared';

export class OrderDetails implements BaseEntity {
    constructor(
        public id?: number,
        public quantity?: number,
        public unitprice?: number,
        public customerorder?: BaseEntity,
        public product?: BaseEntity,
    ) {
    }
}
