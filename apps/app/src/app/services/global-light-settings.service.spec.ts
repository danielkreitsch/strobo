import { TestBed } from '@angular/core/testing';

import { GlobalLightSettingsService } from './global-light-settings.service';

describe('GlobalLightSettingsService', () => {
  let service: GlobalLightSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GlobalLightSettingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
