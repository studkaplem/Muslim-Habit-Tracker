import { TestBed } from '@angular/core/testing';

import { PrayertimesService } from './prayertimes.service';

describe('PrayertimesService', () => {
  let service: PrayertimesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrayertimesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
