<p align="center">
  <img src="app/src/main/res/drawable/logo_earny_bunt.png" width="350" title="Earny_Logo">
</p>

# Finanztracker Earny
Software Engineering Projekt, HAW Hamburg, 4. Semester

Dieses Repository beinhaltet eine Android App zum Tracken von Finanzen.
Die App wurde mit Android Studio erstellt.

## Installation:
tbc

## Tests:
Es steht ein Espresso-Test zur Verfügung (EarnyTest).
Getestet wird:
- Registrierung eines neuen Accounts
- Anmeldung zu diesem Account
- Korrekte Wiedergabe des registrierten Kontostands
- Hinzufügen von neuen Einnahmen und Ausgaben
- Korrente Verrechnung der neuen Eingaben mit dem Kontostand und den Statistik-Werten

Der Test genereiert folgende randomisierte Werte, um möglichst breit zu testen:
- Nutzername (mittels Timestamp)
- Kontostand (Double Wert mit 2 Nachkommastellen)
- Neuer Eintrag-Wert (Double Wert mit 2 Nachkommastellen)
- Eintrags-Datum (Integer für Jahr, Monat und Tag (ausgenommen der Monatstage 29-31 zur vereinfachung der Testung)

Zur individuellen Testung lassen sich folgende Variablen manipulieren:
- Maximale Höhe des Registrierungs-Kontostands
- Anzahl der Einträge (je 1x Einnahme und Ausgabe) pro Testdurchlauf
- Maximale Höhes der Einträge
- Frühestes und spätestes Eintragsjahr

<img width="466" alt="grafik" src="https://user-images.githubusercontent.com/65171891/212616973-633f10c1-47cd-4c80-bf4a-62ed47fc9a7e.png">

