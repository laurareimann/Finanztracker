# Table of Contents
1. [General Info](#general-info)
2. [Installation](#installation)
3. [Tests](#tests)
5. [FAQs](#faqs)
6. [Credits](#credits)


## General Info
<p align="center">
  <img src="app/src/main/res/drawable/logo_earny_bunt.png" width="350" title="Earny_Logo">
</p>

### Finanztracker App - Earny
Software Engineering Projekt, HAW Hamburg, 4. Semester, Erstellungsjahr: 2022/2023

Dieses Repository beinhaltet eine Android App zum Tracken von Finanzen.
Earny soll der nutzenden Person dabei helfen, einen Überblick über Ein- und Ausgaben zu behalten, so dass man die eigenen Finanzen besser managen kann.
Die App wurde mit Android Studio entwickelt und kann per Emulator sowie über ein Android Smartphone in Android Studio ausgeführt werden.


## Installation:

1. Clone oder downloade dieses Repository.
2. Öffne Android Studio.
3. In Android Studio: Select File -> New ->  Project from Version Control auswählen.
4. Füge die URL des Repository ein und wähle ein Directory/Pfad aus. Dann auf Clone klicken.
5. Das Projekt sollte sich nun öffnen. Es kann einige Minuten dauern bis Gradle alles aufgebaut hat und die Projektstruktur angezeigt wird.
6. Um das Gerät per Emulator zu bedienen, unter Device Manager -> Virtual ->  Create Device das gewünschte Gerät erzeugen und die API 33 auswählen.
   Alternativ auch möglich die App mit richtigem Android Gerät zu starten: Device Manager -> Physical ->  Pair using Wi-Fi
7. Auf "Run 'App'" klicken, um die Applikation zu starten.

    Die App sollte nun ein "Willkommen" Screen anzeigen, damit man zur Registrierung oder zum Login gelangt.
 
/// Eine Dateiliste
Die Liste der enthaltenen Dateien ist gut, obwohl sie von der Größe Ihres Codes abhängt. Das bedeutet, dass Sie den Dateibaum weglassen können. Welchen Weg Sie auch immer wählen, geben Sie zumindest eine Erklärung, wie Sie sich durch den Code bewegen.
Sie können zum Beispiel erklären, wie der Code modularisiert ist. Wurde die MVC-Methode angewandt? Solche Informationen liefern Details über die Dateistruktur.


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

## FAQs:

### Fehlerbehebung
Es kann vorkommen, dass das Virtual Device im Emulator nicht funktioniert bzw. abstürzt. 
In diesem Fall das Gerät löschen und noch mal über Create Device neu anlegen und es über ein anderes Geräte Model versuchen.

### Bekannte Bugs
Bug Ticket #XX

Bug Ticket #XX

Bug Ticket #XX

Bug Ticket #XX

Bug Ticket #XX

## Credits

Referenzen und Tutorials, die zur Erstellung des Projekts geholfen und die wir verwendet haben, wurden im Quellcode festgehalten.
