<p align="center">
  <img src="app/src/main/res/drawable/logo_earny_bunt.png" width="350" title="Earny_Logo">
</p>

# Finanztracker App - Earny
Software Engineering Projekt, HAW Hamburg, 4. Semester

Dieses Repository beinhaltet eine Android App zum Tracken von Finanzen, um einen Überblick über Ein- und Ausgaben zu haben.
Die App wurde mit Android Studio erstellt und kann sowohl per Emulator in Android Studio sowie über eine Anroid Smartphone ausgeführt werden.

## Table of Contents
1. [Installation](#installation)
2. [Tests](#tests)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [FAQs](#faqs)

## Installation:
1. Clone or download this repository.
2. Open Android Studio.
3. In IDEA, select File -> Open and navigate to the critter directory within this repository. Select that directory to open.
4. The project should open in IDEA. In the project structure, navigate to src/main/java/com.udacity.jdnd.course3.critter.
5. Within that directory, click on CritterApplication.java and select Run -> Debug CritterApplication.
6. Open a browser and navigate to the url: http://localhost:8082/test

You should see the message "Critter Starter installed successfully" in your browser.

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







///Notizen (wird noch entfernt)
Teil 1. Was ist eine Readme MD-Datei?
Die Readme MD-Datei, die mit der Dateierweiterung README.MD versehen ist, ist eine übliche MD-Datei, eine Markdown-Datei, die Textanweisungen enthält. Projekte, die mit Repository-Anbietern wie GitHub, Bitbucket und GitLab erstellt werden, verwenden häufig eine Datei mit dem Format Readme MD, die das Readme des Projekts enthält.

GitHub bezieht sich auf ein Online-Versionskontrollsystem, das recht beliebt ist. Die Readme-Datei eines Projekts bezieht sich auf eine Textdatei, die nützliche Daten über ein Programm enthält. Sie wird entweder mit dem Programm installiert oder kommt mit dem Installationsprogramm des Programms. Sie enthält vor allem Anweisungen zur Installation des Programms, zur Verwendung seiner Grundfunktionen und zum Zweck.

readme md

Daher ist eine Readme MD online oder offline ein einfacher und schlichter Text, der grundlegende Informationen zu Dateien und Anleitungen enthält. Sie können sie verwenden, um eine Installationsanleitung zu schreiben. Sie ist auch nützlich, um eine grundlegende Dokumentation innerhalb des Projektverzeichnisses zu erstellen. Wenn Sie sich also mit dem MD-Dateiformat befassen, müssen Sie verstehen, wie eine Markdown-Datei erstellt wird, nämlich durch ein Markdown-Skript, das als schriftliche Readme-MD-Datei bereitgestellt wird.

Eine Readme-MD-Datei ist sehr wichtig und sollte daher in Ihr Projekt oder Programm aufgenommen werden, da sie die folgenden Details enthält:

Sie informiert die Leser darüber, worum es in dem Projekt geht
Sie informiert darüber, wie Sie das Projekt verwenden
Sie zeigt die Bedeutung des Projekts, insbesondere für Sie, den Ersteller
Sie informiert darüber, für wen das Programm erstellt wird
Sie zeigt, wo sich die wichtigsten Komponenten darin befinden
Sie informiert darüber, wie das Projekt/Programm für andere von Nutzen ist
Grundsätzlich kann die Readme MD das Interesse von jemandem wecken, so dass er Ihren Code überprüfen und verifizieren kann, dass er gültig und aussagekräftig ist.

Teil 2. Wie man eine Readme MD-Datei schreibt
Wenn Sie eine Readme MD Datei schreiben, müssen Sie die wichtigsten Details kennen, die enthalten sein sollten, um sie einfach und robust zu halten. Am wichtigsten ist, dass Sie die Readme MD-Datei an einem leicht auffindbaren Ort ablegen, z.B. im obersten Verzeichnis, dem Stammverzeichnis in der Hierarchie des Projektsystems. Die folgenden traditionellen, aber wichtigen Abschnitte helfen Ihnen, wie Sie eine Readme-MD-Datei schreiben.

Name des Programms/Projekts
Projektübersicht
Die Projektübersicht informiert über: Worum es sich bei dem Programm handelt; für wen es geschaffen wurde; warum es für Sie von Bedeutung ist; wo Sie die offizielle Dokumentation finden; das Erstellungsdatum und wie Sie es richtig verwenden.

readme md format

Anweisungen zur Konfiguration

Sie müssen die Konfigurationsanweisungen in Ihre Readme MD-Datei aufnehmen, da Software- und Computerkonfigurationen wichtig sind, um eine optimale Leistung auf Basis verschiedener Betriebssysteme wie iOS, Mac, Windows und Android zu erreichen.

Anweisungen für die Installation

Die Installationsanleitung enthält mehrere Punkte. Die grundlegendsten sollten alle Abhängigkeiten und Pakete sein, die benötigt werden, um die Funktionsfähigkeit des Programms zu gewährleisten

Eine Dateiliste

Die Liste der enthaltenen Dateien ist gut, obwohl sie von der Größe Ihres Codes abhängt. Das bedeutet, dass Sie den Dateibaum weglassen können. Welchen Weg Sie auch immer wählen, geben Sie zumindest eine Erklärung, wie Sie sich durch den Code bewegen.

Sie können zum Beispiel erklären, wie der Code modularisiert ist. Wurde die MVC-Methode angewandt? War das Routersystem von Bedeutung? Solche Informationen liefern Details über die Dateistruktur.

Informationen zu Lizenzierung und Copyright

Lizenzen sind kritisch und daher sehr empfehlenswert für Leute, die Open-Source-Projekte produzieren. Sie sollten niemals die Lizenz- und Copyright-Informationen weglassen, da sie andere wissen lassen, was sie mit Ihrem Code tun dürfen und was nicht. Sie können es aber auch weglassen, da die Standard-Urheberrechtsgesetze weiterhin gelten. Letztendlich behalten Sie also das Recht an Ihrem Quellcode, so dass es für andere Personen unmöglich oder illegal ist, Ihren Code zu verbreiten, zu vervielfältigen oder davon abzuleiten.

Kontaktinformationen des Programmierers oder Händlers

Die Benutzer Ihres Programms werden mit Sicherheit aus verschiedenen Gründen mit Ihnen oder Ihren Teammitgliedern in Kontakt treten wollen, weshalb Ihre Kontaktinformationen wichtig sind. Sie sollten also den Namen, Links zu sozialen Medien, E-Mail und andere Kontaktmöglichkeiten angeben.

Fehlerbehebung

Kein Programm ist perfekt und deshalb sollten Sie Informationen zur Fehlerbehebung bereitstellen. Dies hilft den Anwendern bei der Bewältigung häufiger Herausforderungen bei der Verwendung des Programms.

Bekannte Bugs

Da Sie sich der Herausforderungen bewusst sind, die die Benutzer bei der Verwendung des Programms erleben werden, ist dies der perfekte Ort, um die bekannten Probleme anzuzeigen. Sie sollten bestätigen, dass Sie aktiv an ihnen arbeiten.

Danksagungen und Credits

Das Teilen von Informationen ist vorausschauend. Daher sollte jeder, der Ihnen bei der Erstellung der Software geholfen hat, gewürdigt werden, unabhängig von der Größe des Beitrags. Geben Sie also die mitwirkenden Autoren, den Code, den Sie als Referenz verwendet haben und die Tutorials, die Sie zur Fertigstellung des Projekts benutzt haben, an.

Ein Changelog

Ein Changelog ist vor allem für andere Programmierer nützlich. Es ist eine chronologische Liste der Änderungen, die Sie an der Software vorgenommen haben, insbesondere der bemerkenswerten. Diese können neben anderen Aufzeichnungen von Änderungen auch neue Funktionen, Bugfixes, neu verwendete Frameworks und Verbesserungen beinhalten.


