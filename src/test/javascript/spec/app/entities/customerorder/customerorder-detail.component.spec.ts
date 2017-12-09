/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerorderDetailComponent } from '../../../../../../main/webapp/app/entities/customerorder/customerorder-detail.component';
import { CustomerorderService } from '../../../../../../main/webapp/app/entities/customerorder/customerorder.service';
import { Customerorder } from '../../../../../../main/webapp/app/entities/customerorder/customerorder.model';

describe('Component Tests', () => {

    describe('Customerorder Management Detail Component', () => {
        let comp: CustomerorderDetailComponent;
        let fixture: ComponentFixture<CustomerorderDetailComponent>;
        let service: CustomerorderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [CustomerorderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerorderService,
                    JhiEventManager
                ]
            }).overrideTemplate(CustomerorderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerorderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerorderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Customerorder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerorder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
