import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'deviceType'
})
export class DeviceTypePipe implements PipeTransform
{
  /**
   * Transform the device type enum name to a human readable name.
   */
  transform(value: string): string
  {
    switch (value)
    {
      case 'StripWS2812':
        return 'RGB-Streifen'
      case 'StripSK6812':
        return 'RGBW-Streifen'
    }
    return ''
  }
}
