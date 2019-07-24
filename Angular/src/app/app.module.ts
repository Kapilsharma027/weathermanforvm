import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule, MatDialogModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { AgmCoreModule } from '@agm/core';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { HttpClientModule } from '@angular/common/http';
import { DialogComponent } from './shared/dialog/dialog.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import {MatCardModule} from '@angular/material/card';
import {
  MapModule,
  MapAPILoader,
  MarkerTypeId,
  IMapOptions,
  IBox,
  IMarkerIconInfo,
  WindowRef,
  DocumentRef,
  MapServiceFactory,
  BingMapAPILoaderConfig,
  BingMapAPILoader,
  GoogleMapAPILoader,
  GoogleMapAPILoaderConfig
} from 'angular-maps';
import {MatIconModule} from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { RouteDefinitions } from './app.routing';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { HomeComponent } from './home/home.component';
import { MatRadioModule } from '@angular/material/radio';
import { DatePipe } from '../../node_modules/@angular/common';
import { LoginSignupComponent } from './login-signup/login-signup.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
export function MapServiceProviderFactory() {
  let bc: BingMapAPILoaderConfig = new BingMapAPILoaderConfig();
  bc.apiKey = "AnmnQq1ugWhkgjJ4HmVNpdKy5izbUsrG5S1c566UD1lWkAO44t6ttDFFdvLTlkpl"; // your bing map key
  bc.branch = "experimental";
  // to use the experimental bing brach. There are some bug fixes for
  // clustering in that branch you will need if you want to use 
  // clustering.
  return new BingMapAPILoader(bc, new WindowRef(), new DocumentRef());
}
@NgModule({
  declarations: [
    AppComponent,
    ResetPasswordComponent,
    DialogComponent,
    HomeComponent,
    LoginSignupComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [MatRadioModule,
    MatTooltipModule,
    MatInputModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatDialogModule,
    MatCardModule,
    MatIconModule,
    MatTabsModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(RouteDefinitions, { useHash: true }),
    MatButtonModule,
    ReactiveFormsModule,
    MapModule.forRoot(),
    AgmCoreModule.forRoot({
      // please get your own API key here:
      // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en
      apiKey: 'AIzaSyCaKbVhcX_22R_pRKDYuNA7vox-PtGaDkI',
      libraries: ['places']
      // PPS app keys

    })
  ],
  providers: [
    DatePipe,
    {
      provide: MapAPILoader, deps: [], useFactory: MapServiceProviderFactory
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    // LoginDialogComponent,
    // NewAccountComponent,
    DialogComponent,
  ],
})
export class AppModule {

}

