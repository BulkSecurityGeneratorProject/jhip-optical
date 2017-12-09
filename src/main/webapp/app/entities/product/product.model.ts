import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public productcode?: string,
        public description?: string,
        public imageContentType?: string,
        public image?: any,
        public price?: number,
        public quantity?: number,
        public availability?: boolean,
        public orderdetails?: BaseEntity[],
        public categories?: BaseEntity[],
    ) {
        this.availability = false;
    }
}
