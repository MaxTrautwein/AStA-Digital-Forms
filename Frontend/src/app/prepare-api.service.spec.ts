import { TestBed } from '@angular/core/testing';

import { PrepareAPIService } from './prepare-api.service';

describe('PrepareAPIService', () => {
  let service: PrepareAPIService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrepareAPIService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
