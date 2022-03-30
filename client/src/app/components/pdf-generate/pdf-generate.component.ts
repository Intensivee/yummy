import {Component, Inject, OnInit} from '@angular/core';
import {PdfDetails} from '../../model/pdf-details';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {RecipeService} from '../../service/recipe.service';

@Component({
  selector: 'app-pdf-generate',
  templateUrl: './pdf-generate.component.html',
  styleUrls: ['./pdf-generate.component.css']
})
export class PdfGenerateComponent implements OnInit {

  pdfDetails = new PdfDetails();
  recipeId: number;
  fileName: string;

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private dialogRef: MatDialogRef<PdfGenerateComponent>,
              private recipeService: RecipeService) {
    this.recipeId = data.recipeId;
    this.fileName = data.title.concat('.pdf');
  }

  ngOnInit(): void {
    this.dialogRef.updateSize('300px', '300px');
  }

  onSubmit(): void {
    this.dialogRef.close();
    this.recipeService.exportToPdf(this.recipeId, this.pdfDetails)
      .subscribe(data => {
        const file = new Blob([data], {type: 'application/pdf'});
        const fileUrl = URL.createObjectURL(file);
        const anchor = document.createElement('a');
        anchor.download = this.fileName;
        anchor.href = fileUrl;
        anchor.click();
      });
  }

}
