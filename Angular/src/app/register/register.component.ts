import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AddUserRequestModel } from '../shared/models/add-user.model';
import { MatDialog } from '@angular/material';
import { StorageService } from '../services/storage.service';
import { DialogService } from '../shared/dialog/dialog.service';
import { UtilityService } from '../shared/utility.service';
import { RegisterService } from './register.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  addUserForm: FormGroup;
  htmlContent: any;
  onsubmitbtn = false;
  addUserRequestModel: AddUserRequestModel;
  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private storageService: StorageService,
    private dialogService: DialogService,
    private utilityService: UtilityService,
    private addUserService: RegisterService
  ) {
    this.addUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['',
        [Validators.required, Validators.maxLength(100),
          Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$')]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
  });
  }

  ngOnInit() {
    console.log(this.storageService.read('userToken'));
  }

  onSubmit(addUserData) {
    this.onsubmitbtn = true;
    console.log('###addUserData##', addUserData);
    if (addUserData.valid) {
      if (addUserData.value.password === addUserData.value.confirmPassword) {

        this.addUserRequestModel = new AddUserRequestModel();
        this.addUserRequestModel.username = addUserData.value.email;
        this.addUserRequestModel.password = addUserData.value.password;
        this.addUserService.saveData(this.addUserRequestModel).subscribe(data => {
          console.log(data);
          this.htmlContent = `<p>${data.message}</p>`;
          this.dialogService.successDialogBox(this.htmlContent, true);
        }, error => {
          this.utilityService.handleErrorResponse(error);
          // this.htmlContent = `<p>${error.error ? error.error.message : 'There is some issue please try again.'}</p>`;
          // this.dialogService.errorDialogBox(this.htmlContent, false);
        });
      } else {
        this.htmlContent = `<p>Your password should match with confirm password.</p>`;
        this.dialogService.errorDialogBox(this.htmlContent);
      }
}
  }

}
