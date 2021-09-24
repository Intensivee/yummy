import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'align'
})
export class AlignPipe implements PipeTransform {

  transform(text: string, expectedLength: number, char: string): string {
    const numberOfCharsToAdd = expectedLength - text.length;
    if (numberOfCharsToAdd <= 0) {
      return text;
    }
    return text + ' ' + char.repeat(numberOfCharsToAdd);
  }

}
