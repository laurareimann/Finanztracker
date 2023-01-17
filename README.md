<p align="center">
  <img src="app/src/main/res/drawable/logo_earny_bunt.png" width="350" title="Earny_Logo">
</p>

# Finanztracker App - Earny
Software Engineering Projekt, HAW Hamburg, 4. Semester, Jahr: 2022/2023

Dieses Repository beinhaltet eine Android App zum Tracken von Finanzen.
Earny soll den NUtzer*innen dabei helfen, einen Überblick über Ein- und Ausgaben zu behalten, so dass man die eigenen FInanzen besser managen kann.
Die App wurde mit Android Studio erstellt und kann sowohl per Emulator sowie über eine Android Smartphone in Android Studio ausgeführt werden.



## Table of Contents
1. [Installation](#installation)
2. [Tests](#tests)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [FAQs](#faqs)

## Installation:

Anweisungen für die Installation

Die Installationsanleitung enthält mehrere Punkte. Die grundlegendsten sollten alle Abhängigkeiten und Pakete sein, die benötigt werden, um die Funktionsfähigkeit des Programms zu gewährleisten

1. Clone oder download dieses repository.
2. Öffne Android Studio.
3. In Android Studio: select File -> New ->  Project from Version Control -> füge die URL des repository ein und wähle ein Directory/Pfad aus. Dann auf Clone klicken.
4. Das Projekt sollte sich nun öffnen. Kann einige Minuten dauern bis Gradle alles aufgebaut hat und die Projektstruktur angezeigt wird.
5. Um das Gerät per Emulator zu bedienen, unter Device Manager -> Virtual ->  Create Device das gewünschte Gerät erzeugen und die API 33 auswählen.
   Alternativ auch möglich mit richtigem Android Gerät die App zu starten: Device Manager -> Physical ->  Pair using Wi-Fi
6. Auf "Run" App klicken, um die Applikation zu starten.

 Die App sollte nun ein "Willkommen" Screen anzeigen, damit man zur Registrierung oder zum Login gelangt.

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

# Fehlerbehebung
Kein Programm ist perfekt und deshalb sollten Sie Informationen zur Fehlerbehebung bereitstellen. Dies hilft den Anwendern bei der Bewältigung häufiger Herausforderungen bei der Verwendung des Programms.

# Bekannte Bugs
Da Sie sich der Herausforderungen bewusst sind, die die Benutzer bei der Verwendung des Programms erleben werden, ist dies der perfekte Ort, um die bekannten Probleme anzuzeigen. Sie sollten bestätigen, dass Sie aktiv an ihnen arbeiten.

# Danksagungen und Credits

Referenzen und Tutorials, die uns zur Fertigstellung des Projekts geholfen und die wir benutzt haben, haben wir im Code angegeben.



///Notizen (wird noch entfernt)

Sie zeigt, wo sich die wichtigsten Komponenten darin befinden
Grundsätzlich kann die Readme MD das Interesse von jemandem wecken, so dass er Ihren Code überprüfen und verifizieren kann, dass er gültig und aussagekräftig ist.


Eine Dateiliste

Die Liste der enthaltenen Dateien ist gut, obwohl sie von der Größe Ihres Codes abhängt. Das bedeutet, dass Sie den Dateibaum weglassen können. Welchen Weg Sie auch immer wählen, geben Sie zumindest eine Erklärung, wie Sie sich durch den Code bewegen.

Sie können zum Beispiel erklären, wie der Code modularisiert ist. Wurde die MVC-Methode angewandt? War das Routersystem von Bedeutung? Solche Informationen liefern Details über die Dateistruktur.

Informationen zu Lizenzierung und Copyright

Lizenzen sind kritisch und daher sehr empfehlenswert für Leute, die Open-Source-Projekte produzieren. Sie sollten niemals die Lizenz- und Copyright-Informationen weglassen, da sie andere wissen lassen, was sie mit Ihrem Code tun dürfen und was nicht. Sie können es aber auch weglassen, da die Standard-Urheberrechtsgesetze weiterhin gelten. Letztendlich behalten Sie also das Recht an Ihrem Quellcode, so dass es für andere Personen unmöglich oder illegal ist, Ihren Code zu verbreiten, zu vervielfältigen oder davon abzuleiten.

Kontaktinformationen des Programmierers oder Händlers

Die Benutzer Ihres Programms werden mit Sicherheit aus verschiedenen Gründen mit Ihnen oder Ihren Teammitgliedern in Kontakt treten wollen, weshalb Ihre Kontaktinformationen wichtig sind. Sie sollten also den Namen, Links zu sozialen Medien, E-Mail und andere Kontaktmöglichkeiten angeben.

Ein Changelog



