import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public phonenumber?: string,
        public age?: number,
        public cylindrical?: string,
        public spherical?: string,
        public power?: string,
        public longsight?: string,
        public shortsight?: string,
        public address?: string,
        public customerorders?: BaseEntity[],
    ) {
    }
}
