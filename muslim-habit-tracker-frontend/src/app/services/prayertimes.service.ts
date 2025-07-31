import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PrayerTime {
  Fajr: string;
  Sunrise: string;
  Dhuhr: string;
  Asr: string;
  Maghrib: string;
  Isha: string;
}

export interface CityPrayerTimes {
  [date: string]: PrayerTime;
}

@Injectable({
  providedIn: 'root'
})
export class PrayertimesService {
  private apiURL = 'http://localhost:8080/api/prayertimes';

  constructor(private http: HttpClient) { }

  getPrayertimesForCity(city: string): Observable<CityPrayerTimes> {
    return this.http.get<CityPrayerTimes>(`${this.apiURL}/${city}`);
  }

  getPrayertimeForCityAndDate(city: string, date: string): Observable<PrayerTime> {
    return this.http.get<PrayerTime>(`${this.apiURL}/${city}/${date}`);
  }

}
