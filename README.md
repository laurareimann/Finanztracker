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
Es steht ein Espresso-Test zur Verfügung (EarnyTest). In der Testung wurde diese Form des UI Testings JUnit-Tests vorgezogen, weil diese eher im Development Prozess hilfreich sind, um die Funktion von Methoden zu testen, während sich noch viel an der App ändert. 

Da die Tests erst zum Ende des Projekts umgesetzt werden konnten, wurde deshalb ein UI-Test vorgezogen, der zwar langsamer läuft, dafür jedoch das Endergebnis besser testet. Die Methoden, die ansonsten per JUnit Test überprüft würden, lassen sich so über das im User Interface angezeigte Ergebnis testen.


Getestet wird:
- Registrierung eines neuen Accounts
- Anmeldung zu diesem Account
- Korrekte Wiedergabe des registrierten Kontostands
- Hinzufügen von neuen Einnahmen und Ausgaben
- Korrekte Verrechnung der neuen Eingaben mit dem Kontostand und den Statistik-Werten


Der Test generiert folgende randomisierte Werte, um möglichst breit zu testen:
- Nutzername (mittels Timestamp)
- Kontostand (Double Wert mit 2 Nachkommastellen)
- Neuer Eintrag-Wert (Double Wert mit 2 Nachkommastellen)
- Eintrags-Datum (Integer für Jahr, Monat und Tag (ausgenommen der Monatstage 29-31 zur vereinfachung der Testung)


Zur individuellen Testung lassen sich folgende Variablen manipulieren:
- Maximale Höhe des Registrierungs-Kontostands
- Anzahl der Einträge (je 1x Einnahme und Ausgabe) pro Testdurchlauf
- Maximale Höhes der Einträge
- Frühestes und spätestes Eintragsjahr


