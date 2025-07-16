package com.habittracker.muslim_habit_tracker_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generiert automatisch Getter, Setter, equals(), hashCode(), und toString()
@NoArgsConstructor // Generiert einen Konstruktor ohne Argumente
@AllArgsConstructor // Generiert einen Konstruktor mit allen Argumenten
public class Prayertime {
    @JsonProperty("Fajr") // Mappt das JSON-Feld "Fajr" auf das Java-Feld 'fajr'
    private String fajr;
    @JsonProperty("Sunrise")
    private String sunrise;
    @JsonProperty("Dhuhr")
    private String dhuhr;
    @JsonProperty("Asr")
    private String asr;
    @JsonProperty("Maghrib")
    private String maghrib;
    @JsonProperty("Isha")
    private String isha;
}
