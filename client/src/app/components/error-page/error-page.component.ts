import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  errorMessage = 'An error occurred, please contact administrator or try again';

  constructor() {
  }

  ngOnInit(): void {
  }

}
