/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/order-details/order-details-detail.component';
import { OrderDetailsService } from '../../../../../../main/webapp/app/entities/order-details/order-details.service';
import { OrderDetails } from '../../../../../../main/webapp/app/entities/order-details/order-details.model';

describe('Component Tests', () => {

    describe('OrderDetails Management Detail Component', () => {
        let comp: OrderDetailsDetailComponent;
        let fixture: ComponentFixture<OrderDetailsDetailComponent>;
        let service: OrderDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [OrderDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrderDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
