package com.habittracker.muslim_habit_tracker_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habittracker.muslim_habit_tracker_backend.model.Prayertime;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PrayertimesService {

    @Value("classpath:roPrayertimes.json")
    private Resource prayertimesResource;

    // ConcurrentHashMap für Thread-Sicherheit, falls später mehrere Anfragen kommen könnten
    // Map<Stadtname, Map<Datum als String, Gebetszeit Objekt>>
    private final Map<String, Map<String, Prayertime>> allPrayertimesCache = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson Mapper zum Parsen von JSON

    // Diese Methode wird automatisch nach der Initialisierung des Beans aufgerufen
    @PostConstruct
    public void init() {
        try (InputStream is = prayertimesResource.getInputStream()) {
            // TypeReference wird benötigt, um komplexe generische Typen (Map von Map von Gebetszeit)
            // korrekt von Jackson deserialisieren zu lassen.
            Map<String, Map<String, Prayertime>> loadedData = objectMapper.readValue(is, new TypeReference<>() {
            });
            allPrayertimesCache.putAll(loadedData); // Füge geladene Daten zum Cache hinzu
            System.out.println("Gebetszeiten erfolgreich aus gebetszeiten.json geladen!");
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Gebetszeiten-Datei: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Konnte Gebetszeiten nicht laden!", e);
        }
    }

    /**
     * Gibt alle Gebetszeiten für eine bestimmte Stadt zurück.
     * @param stadtName Der Name der Stadt (z.B. "Aachen").
     * @return Eine Map von Datum (String) zu Gebetszeit-Objekten. Leer, wenn die Stadt nicht gefunden wird.
     */
    public Map<String, Prayertime> getPrayerTimesForCity (String stadtName) {
        // Da du im MVP nur eine Stadt hast, könntest du hier auch direkt alleGebetszeitenCache.get("Aachen") zurückgeben.
        // Diese Methode ist aber flexibler für zukünftige Erweiterungen.
        return allPrayertimesCache.getOrDefault(stadtName, Collections.emptyMap());
    }

    /**
     * Gibt die Gebetszeit für eine bestimmte Stadt und ein bestimmtes Datum zurück.
     * @param stadtName Der Name der Stadt.
     * @param datum Das Datum im Format "dd.MM.yyyy".
     * @return Das Gebetszeit-Objekt für den Tag, oder null, wenn nicht gefunden.
     */
    public Prayertime getPrayertimesForCityAndDate(String stadtName, String datum) {
        Map<String, Prayertime> cityPrayertimes = getPrayerTimesForCity(stadtName);
        return cityPrayertimes.get(datum);
    }

}
