import { Device } from './device';
import { Color } from './color';
import { Animation } from './animation';

export class Group {
  id?: string;
  name?: string;
  devices: Device[] = [];
  active?: boolean;
  brightness?: number;
  color?: Color;
  animation?: Animation | null;
}
