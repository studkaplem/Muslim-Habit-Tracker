import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <-- Wichtig für *ngIf
import { PrayertimesService, PrayerTime } from '../../services/prayertimes.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-prayertimes-overview',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prayertimes-overview.component.html',
  styleUrls: ['./prayertimes-overview.component.css']
})

export class PrayertimesOverviewComponent implements OnInit{
  city: string = "Rosenheim";
  currentDate: string;
  currentPrayerTimes: PrayerTime | null = null;
  errorMessage: string = "";

  constructor(private prayertimesService: PrayertimesService) {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const year = today.getFullYear();
    this.currentDate = `${day}.${month}.${year}`; // german format
  }

  ngOnInit(): void {
    this.loadPrayerTimesForToday();
  }

  loadPrayerTimesForToday(): void {
    this.errorMessage = '';

    this.prayertimesService.getPrayertimeForCityAndDate(this.city, this.currentDate)
      .subscribe({
        next: (data: PrayerTime) => {
          this.currentPrayerTimes = data;
          console.log('Gebetszeiten für heute geladen:', data);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Fehler beim Laden der Gebetszeiten:', error);
          if (error.status === 404) {
            this.errorMessage = `Keine Gebetszeiten für ${this.city} am ${this.currentDate} gefunden.`;
          } else {
            this.errorMessage = `Fehler beim Laden der Gebetszeiten: ${error.statusText || error.message}. Bitte überprüfe das Backend.`;
          }
        },
        complete: () => {
          console.log('Gebetszeiten-Anfrage abgeschlossen.');
        }
      });
  }

}
