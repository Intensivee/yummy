import {TestBed} from '@angular/core/testing';

import {ErrorHttpInterceptorService} from './error-http-interceptor.service';

describe('ErrorHttpInterceptorService', () => {
  let service: ErrorHttpInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ErrorHttpInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
