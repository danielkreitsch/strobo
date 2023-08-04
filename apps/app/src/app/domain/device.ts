import {Color} from "./color";
import {Animation} from "./animation"

export class Device {
  id?: string
  type?: string
  name?: string
  connected?: boolean
  active?: boolean
  brightness?: number
  color: Color = new Color(0, 0, 0, 0)
  animation?: Animation | null
  timeAdjustment?: number
}
