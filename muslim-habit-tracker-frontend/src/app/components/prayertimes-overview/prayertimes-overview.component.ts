import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <-- Wichtig für *ngIf
import { PrayertimesService, PrayerTime } from '../../services/prayertimes.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-prayertimes-overview',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prayertimes-overview.component.html',
  styleUrls: ['./prayertimes-overview.component.css']
})

export class PrayertimesOverviewComponent implements OnInit{
  city: string = "Rosenheim";
  dateDisplay: Date = new Date();
  currentPrayerTimes: PrayerTime | null = null;
  errorMessage: string = "";

  constructor(private prayertimesService: PrayertimesService) {}

  ngOnInit(): void {
    this.loadPrayerTimes();
  }

  getFormattedDate(date: Date): string {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`; // german format
  }

  loadPrayerTimes(): void {
    this.currentPrayerTimes = null; // Reset current prayer times
    this.errorMessage = '';

    const dateString = this.getFormattedDate(this.dateDisplay);
    console.log(`Lade Gebetszeiten für ${this.city} am ${dateString}...`);

    this.prayertimesService.getPrayertimeForCityAndDate(this.city, dateString)
      .subscribe({
        next: (data: PrayerTime) => {
          this.currentPrayerTimes = data;
          console.log('Gebetszeiten für heute geladen:', data);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Fehler beim Laden der Gebetszeiten:', error);
          if (error.status === 404) {
            this.errorMessage = `Keine Gebetszeiten für ${this.city} am ${dateString} gefunden.`;
          } else {
            this.errorMessage = `Fehler beim Laden der Gebetszeiten: ${error.statusText || error.message}. Bitte überprüfe das Backend.`;
          }
        }
      });
  }

  skipForward(): void {
    this.dateDisplay.setDate(this.dateDisplay.getDate() + 1);
    this.loadPrayerTimes();
  }

  goToToday(): void {
    this.dateDisplay = new Date();
    this.loadPrayerTimes();
  }

  isToday(): boolean {
    const today = new Date();
    // Assuming you have a dateDisplay property of type Date or string
    const current = new Date(this.dateDisplay);
    return (
      today.getFullYear() === current.getFullYear() &&
      today.getMonth() === current.getMonth() &&
      today.getDate() === current.getDate()
    );
  }

}
