import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColorPickerComponent } from '../components/color-picker/color-picker.component';

@NgModule({
  imports: [CommonModule],
  declarations: [ColorPickerComponent],
  exports: [ColorPickerComponent],
})
export class SharedModule {}
