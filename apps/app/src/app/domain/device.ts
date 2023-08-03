import {Color} from "./color";
import {Animation} from "./animation"

export class Device {
  id: string
  type: string
  name: string
  connected: boolean
  active: boolean
  brightness: number
  color: Color
  animation: Animation
  timeAdjustment: number
}
