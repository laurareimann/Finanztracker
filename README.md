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
    3. In Android Studio: Select File -> New -> Project from Version Control auswählen.
    4. Füge die URL des Repository ein und wähle ein Directory/Pfad aus. Dann auf Clone klicken.
    5. Das Projekt sollte sich nun öffnen. Es kann einige Minuten dauern bis Gradle alles aufgebaut 
       hat und die Projektstruktur angezeigt wird.
    6. Um das Gerät per Emulator zu bedienen, unter Device Manager -> Virtual -> Create Device 
       und das gewünschte Gerät erzeugen und die API 33 auswählen.
       Alternativ auch möglich die App mit richtigem Android Gerät zu starten: Device Manager -> Physical -> Pair using Wi-Fi
    7. Auf "Run 'App'" klicken, um die Applikation zu starten.

Die App sollte nun ein "Willkommen" Screen anzeigen, damit man zur Registrierung oder zum Login gelangt.
![image](https://user-images.githubusercontent.com/94016790/213174072-6352b1fb-b55f-42e5-961a-185d3bc844e1.png)

#### Hinweis: Die App wurde optimiert für das Pixel 3a. Eventuell müssen noch Gradle Updates vorgenommen sowie diverse Pakete heruntergeladen werden. Android Studio zeigt dies auch an und stellt die Downloads zur Verfügung.
 
## Tests:
Es steht ein Espresso-Test zur Verfügung (EarnyTest).

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
- Eintrags-Datum (Integer für Jahr, Monat und Tag (ausgenommen der Monatstage 29-31 zur Vereinfachung der Testung)


Zur individuellen Testung lassen sich folgende Variablen manipulieren:
- Maximale Höhe des Registrierungs-Kontostands
- Anzahl der Einträge (je 1x Einnahme und Ausgabe) pro Testdurchlauf
- Maximale Höhe der Einträge
- Frühestes und spätestes Eintragsjahr

## FAQs:

### Fehlerbehebung
Es kann vorkommen, dass das Virtual Device im Emulator nicht funktioniert bzw. abstürzt. 
In diesem Fall das Gerät löschen und noch mal über Create Device ein neues Gerät anlegen und es über ein anderes Geräte Model versuchen.

### Bekannte Bugs
Durch die unterschiedlichen, angewandten Testing Verfahren, haben sich einige Bugs herauskristallisiert, die es noch zu beseitigen gilt. Die Bugs sind auf dem Github Ticketboard festgehalten. Zu den bekannten, größeren Bugs gehören:

#### No. 1: (Github Tickets: #98, #106, #109)
Generell muss die Sicherheit der Eingabemasken erhöht werden, indem klar definierte Regeln festgelegt werden, die zu der jeweiligen Eingabe passen. Dies ließe sich durch reguläre Ausdrücke umsetzen. Dadurch kann verhindert werden, dass Systembefehle zum Auslesen der Datenbank eingegeben werden oder unsinnige Eingaben zum Absturz der App führen. Insbesondere beim Anlegen der Nutzer:Innen besteht ein Sicherheitsrisiko, da keine Mindestanzahl an Ziffern/Buchstaben/Sonderzeichen beim Benutzernamen und Passwort festgelegt wurde. Eingaben wie ein “.” können nach wie vor als Benutzername und Passwort verwendet werden. Außerdem sind bei der Registrierung Eingaben mit 3 (oder mehr) Nachkommastellen möglich. Dies wird in der Home Activity auch angezeigt. Beispielsweise der Wert: "20,999 € ". 
Des Weiteren kann man bei der Eingabe der Einnahmen/Ausgaben einen "." eingeben. Der Punkt wird nicht in den Buchungen eingetragen, aber die App kann abstürzen. 

#### No. 2: (Github Ticket: #92) 
Wenn man bei der Registrierung ein Double eingibt, wird das bei der Balance zu einem int aufgerundet. Auf dem Homescreen wird dann nicht der Richtige Wert angezeigt, sondern ein Ganzzahliger. Der Fehler tritt nicht immer auf, scheint ein Rundungsfehler zu sein. Beispiel: 1234.56 geht, 91196.54 geht nicht. Die Tests haben ergeben, dass es wahrscheinlich nur mit einem Wert < 10.000 funktioniert.

## Credits

Referenzen und Tutorials, die zur Erstellung des Projekts geholfen und die wir verwendet haben, wurden im Quellcode festgehalten.
