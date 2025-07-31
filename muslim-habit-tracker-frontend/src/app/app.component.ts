import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PrayertimesOverviewComponent } from "./components/prayertimes-overview/prayertimes-overview.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PrayertimesOverviewComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'muslim-habit-tracker-frontend';
}
