import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrayertimesOverviewComponent } from './prayertimes-overview.component';

describe('PrayertimesOverviewComponent', () => {
  let component: PrayertimesOverviewComponent;
  let fixture: ComponentFixture<PrayertimesOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrayertimesOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrayertimesOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
