import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Color } from '../../domain/color';
import { interval } from 'rxjs';

declare const KellyColorPicker: any;

@Component({
  selector: 'strobo-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.scss'],
})
export class ColorPickerComponent implements OnInit {
  @Input()
  initialColor: Color = new Color(255, 255, 255, 0);

  @Input()
  size = 300;

  @Output()
  colorInput: EventEmitter<Color> = new EventEmitter<Color>();

  colorPicker: any;

  color: Color = new Color(
    this.initialColor.r,
    this.initialColor.g,
    this.initialColor.b,
    this.initialColor.w
  );

  colorUpdatedSinceLastEmission = false;

  constructor() {}

  ngOnInit() {
    this.colorPicker = new KellyColorPicker({
      place: 'picker',
      input: 'color',
      size: this.size,
      color: '#fffffff',
      method: 'triangle',
      inputColor: true,
      inputFormat: 'rgba',
      alpha: 1,
      alphaSlider: false,
      resizeWith: true,
      userEvents: {
        change: (self: any) => {
          const color = self.getCurColorRgb();
          if (color != null) {
            this.color.r = color.r;
            this.color.g = color.g;
            this.color.b = color.b;

            this.colorUpdatedSinceLastEmission = true;
          }
        },
      },
    });

    const setInitialColor = interval(20).subscribe(() => {
      if (this.initialColor != null) {
        this.colorPicker.setColorByHex(
          '#' +
            this.rgbToHex(
              this.initialColor.r,
              this.initialColor.g,
              this.initialColor.b
            )
        );
        setInitialColor.unsubscribe();
      }
    });

    interval(50).subscribe(() => {
      if (this.colorUpdatedSinceLastEmission) {
        this.colorInput.emit(this.color);
        this.colorUpdatedSinceLastEmission = false;
      }
    });
  }

  componentToHex(c: number) {
    const hex = c.toString(16);
    return hex.length == 1 ? '0' + hex : hex;
  }

  rgbToHex(r: number, g: number, b: number) {
    return (
      this.componentToHex(r) + this.componentToHex(g) + this.componentToHex(b)
    );
  }
}
