package com.habittracker.muslim_habit_tracker_backend.controller;

import com.habittracker.muslim_habit_tracker_backend.model.Prayertime;
import com.habittracker.muslim_habit_tracker_backend.service.PrayertimesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // Markiert diese Klasse als Spring REST Controller
@RequestMapping("/api/prayertimes") // Definiert den Basis-Pfad für alle Endpunkte in diesem Controller
@CrossOrigin(origins = "http://localhost:4200") // WICHTIG: Erlaubt Anfragen von deinem Angular Frontend
public class PrayertimesController {
    private final PrayertimesService prayertimesService;

    // Konstruktor-Injektion: Spring injiziert automatisch eine Instanz von GebetszeitenService
    public PrayertimesController(PrayertimesService prayertimesService) {
        this.prayertimesService = prayertimesService;
    }

    /**
     * Endpunkt zum Abrufen aller Gebetszeiten für eine bestimmte Stadt.
     * Beispiel-URL: GET http://localhost:8080/api/prayertimes/Aachen
     * @param cityName Der Name der Stadt (aus dem URL-Pfad).
     * @return Eine ResponseEntity mit der Map der Gebetszeiten oder 404 Not Found.
     */
    @GetMapping("/{cityName}")
    public ResponseEntity<Map<String, Prayertime>> getGebetszeitenFuerStadt(@PathVariable String cityName) {
        Map<String, Prayertime> prayertimes = prayertimesService.getPrayerTimesForCity(cityName);
        if (prayertimes.isEmpty()) {
            // Wenn die Stadt nicht gefunden wird oder keine Daten hat, gib 404 Not Found zurück
            return ResponseEntity.notFound().build();
        }
        // Gib die Daten mit dem HTTP-Status 200 OK zurück
        return ResponseEntity.ok(prayertimes);
    }

    /**
     * Endpunkt zum Abrufen der Gebetszeiten für eine bestimmte Stadt und ein bestimmtes Datum.
     * Beispiel-URL: GET http://localhost:8080/api/gebetszeiten/Aachen/01.01.2025
     * @param stadtName Der Name der Stadt.
     * @param datum Das Datum im Format "dd.MM.yyyy".
     * @return Eine ResponseEntity mit dem Gebetszeit-Objekt oder 404 Not Found.
     */
    @GetMapping("/{stadtName}/{datum}")
    public ResponseEntity<Prayertime> getGebetszeitFuerStadtUndDatum(@PathVariable String stadtName, @PathVariable String datum) {
        Prayertime prayertime = prayertimesService.getPrayertimesForCityAndDate(stadtName, datum);
        if (prayertime == null) {
            // Wenn keine Gebetszeit für das Datum gefunden wird, gib 404 Not Found zurück
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prayertime);
    }
}
