import { Component, OnInit, ViewChild, ElementRef, NgZone } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormControl } from '@angular/forms';
import { MakerModel } from '../shared/models/marker.model';
import { MatDialogRef, MatDialog } from '@angular/material';
import { GlobalService } from '../services/global.service';
import { StorageService } from '../services/storage.service';
import { MapsAPILoader } from '@agm/core';
import { Cookie } from 'ng2-cookies';
import { } from '@types/googlemaps';
import { AgmMap, LatLngBounds } from '@agm/core';
import { routerPath } from '../shared/constant';
import { Router } from '@angular/router';
import { DialogService } from '../shared/dialog/dialog.service';
import { LoginService } from '../login/login.service';
import { RecentLocationRequestModcel } from '../shared/models/recentLocation.model';
declare var google;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  showLogin = false;
  showLoginButton = true;
  weatherInfoAccu;
  weatherInfoDarkSky;
  weatherInfoOpenWeather;
  weatherInfoWeatherbit;
  weatherInfoUnlock;
  zoom = 8;
  lat: Number = 51.673858;
  lng: Number = 7.815982;
  username;
  cityName = '';
  cities;
  selectedProvider;
  feedbackSuccess;
  AvgProvider;
  FAV_PROVIDER_ID = 0;
  @ViewChild('search')
  public searchElementRef: ElementRef;
  public searchControl: FormControl;
  markers: Array<MakerModel> = [];
  htmlContent: string;
  recentLocations: Array<RecentLocationRequestModcel> = [];
  recentLocation: RecentLocationRequestModcel;
  constructor(public dialog: MatDialog,
    private dialogService: DialogService,
    private datePipe: DatePipe,
    public globalService: GlobalService,
    private storageService: StorageService,
    private loginService: LoginService,
    private mapsAPILoader: MapsAPILoader,
    private route: Router,
    private ngZone: NgZone) {

  }
  ngOnInit() {
    this.getFavProvider();
    this.searchControl = new FormControl();
    if (Cookie.get('TheWeatherManToken') && Cookie.get('TheWeatherManUsername')) {

      this.storageService.write('userToken', Cookie.get('TheWeatherManToken'));
      this.storageService.write('username', Cookie.get('TheWeatherManUsername'));
      this.username = this.storageService.read('username');
      this.showLogin = true;
      this.showLoginButton = false;
    } else {
      this.storageService.delete('username');
      this.storageService.delete('userToken');
    }
    this.setCurrentPosition();
    this.loadPlaceAutoComplete();
  }

  clearCache() {
    this.storageService.clearStorage();
    Cookie.deleteAll();
    this.showLogin = false;
    this.showLoginButton = true;
  }

  loadPlaceAutoComplete() {
    this.mapsAPILoader.load().then(() => {
      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ['address']

      });

      autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          let place: google.maps.places.PlaceResult =
            autocomplete.getPlace();
          console.log(place.formatted_address);
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }
          this.recentLocation = new RecentLocationRequestModcel();
          this.recentLocation.name = place.formatted_address ? place.formatted_address : '';
          this.recentLocation.lat = place.geometry.location.lat();
          this.recentLocation.lng = place.geometry.location.lng();
          this.recentLocations.push(this.recentLocation);
          console.log(this.recentLocations);
          this.setmarker(place.geometry.location.lat(), place.geometry.location.lng(), "additional info")
          this.lat = place.geometry.location.lat();
          this.lng = place.geometry.location.lng();

          this.selectedCity(place.geometry.location.lat(), place.geometry.location.lng());
        });
      });
    });
  }

  setmarker(lat, lng, label) {
    this.markers = [];
    const mark = new MakerModel();
    mark.lat = lat;
    mark.lng = lng;
    mark.label = null;
    mark.draggable = true;
    this.markers.push(mark);

  }

  openLoginSignUp() {
    this.route.navigate([routerPath.loginSignUp]);
  }
  getLocation(location: RecentLocationRequestModcel) {
    //     this.searchControl.setValue(location.name);
    //         console.log(location);
    // this.selectedCity(location.lat , location.lng);
    // this.setmarker(location.lat, location.lng, 'initial');
    this.searchControl.setValue(location.name);
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = location.lat;
        this.lng = location.lng;
        this.selectedCity(this.lat, this.lng);
        this.setmarker(this.lat, this.lng, 'initial');
        console.log('current location', this.lat, this.lng);
      });
    }
  }

  selectedCity(lat, lng) {
    return new Promise((resolve, reject) => {
      this.loginService.getAccuweatherData(lat, lng).subscribe(res => {
        if (res.data) {
          this.weatherInfoAccu = res.data.accuweather;
        }
      }, error => {
        console.log(error);
      });

      this.loginService.getDarkskyData(lat, lng).subscribe(res => {
        if (res.data) {
          this.weatherInfoDarkSky = res.data.darksky;
        }
      }, error => {
        console.log(error);
      });

      this.loginService.getOpenweathermapData(lat, lng).subscribe(res => {
        if (res.data) {
          this.weatherInfoOpenWeather = res.data.openweathermap;
        }
      }, error => {
        console.log(error);
      });

      this.loginService.getWeatherbitData(lat, lng).subscribe(res => {
        if (res.data) {
          this.weatherInfoWeatherbit = res.data.weatherbit;
        }
      }, error => {
        console.log(error);
      });

      this.loginService.getWeatherunlockedData(lat, lng).subscribe(res => {
        if (res.data) {
          this.weatherInfoUnlock = res.data.weatherunlocked;
          this.getAvgOfProviders();
        }
      }, error => {
        console.log(error);
      });

    });


  }

  clickedMarker(label: string, index: number) {
    console.log(`clicked the marker: ${label || index}`);
  }
  private setCurrentPosition() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        this.selectedCity(this.lat, this.lng);
        this.setmarker(this.lat, this.lng, 'initial');
        console.log('current location', this.lat, this.lng);
      });
    }
    // this.setmarker(this.lat, this.lng , "initial");
  }


  twoDigits(d) {
    if (0 <= d && d < 10) return "0" + d.toString();
    if (-10 < d && d < 0) return "-0" + (-1 * d).toString();
    return d.toString();
  }

  radioChange() {

    // let now = new Date();
    // this.datePipe.transform(now, "YYYY-MM-DD HH:mm:SS"); //whatever format you need. 

    // now.getUTCFullYear() + "-" + this.twoDigits(1 + now.getUTCMonth()) + "-" + this.twoDigits(now.getUTCDate()) + " " + this.twoDigits(now.getUTCHours()) + ":" + this.twoDigits(now.getUTCMinutes()) + ":" + this.twoDigits(now.getUTCSeconds());

    let json = {
      // "date": now.getUTCFullYear() + "-" + this.twoDigits(1 + now.getUTCMonth()) + "-" + this.twoDigits(now.getUTCDate()) + " " + this.twoDigits(now.getUTCHours()) + ":" + this.twoDigits(now.getUTCMinutes()) + ":" + this.twoDigits(now.getUTCSeconds()),
      "userId": this.storageService.read("userId"),
      "providerId": this.selectedProvider
    }

    this.loginService.sendUserVote(json).subscribe(data => {
      if (data.code == 200) {
        this.htmlContent = `<p>${data.data}</p>`;
        this.dialogService.successDialogBox(this.htmlContent, false);
      }

    })

  }

  getFavProvider() {
    this.loginService.getFavProvider().subscribe(data => {
      if (data.code == 200) {
        this.FAV_PROVIDER_ID = data.data['FAV_PROVIDER_ID'];
      }
      console.log(data)
    });
  }

  getAvgOfProviders() {
    this.loginService.getAvgOfProviders(this.lat.toString() + this.lng.toString()).subscribe(data => {
      if (data.code == 200) {

        this.AvgProvider = data.data;
        console.log(data);
      }
    });
  }


}
