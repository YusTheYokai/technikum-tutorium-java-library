# Technikum Tutorium Java Bücherei
Aufgabe ist es ein Büchereisystem zu entwickeln.

_Alle Ein-/Ausgaben sind lediglich Beispiele und müssten nicht identisch übernommen werden!_

## Aufgabenstellung
Es sind bereits einige Klassen und Codeteile vorhanden, die von Ihnen erweitert werden sollen.
Die bereits vorhandenen Kommentare können als Anhaltspunkte verwendet werden. Bitte kommentieren Sie eigenständig Ihre eigenen Codezeilen.

### Programmstart
Nach Start des Programms wird man aufgefordert sich anhand Benutzername und Passwort einzuloggen. Dementsprechend soll direkt beim Programmstart ein Admin angelegt werden.

```
Willkommen bei der Bücherei!
Nutzername: admin
Passwort: admin

Willkommen Admin! Was möchtest du tun?
```

### User hinzufügen
Der Admin kann neue Nutzer*innen anlegen. Hierfür wird der Name, Nutzername und ein Passwort benötigt.
Nutzernamen dürfen nicht doppelt vorkommen und müssen mindestens 3 Zeichen (keine Leerzeichen) lang sein. Das Passwort muss mindestens 8 Zeichen lang sein.

Sollte versucht werden eine\*n Nutzer\*inn anzulegen, der bereits existiert, dessen Nutzername zu kurz ist oder dessen Passwort zu kurz ist, soll eine entsprechende Fehlermeldung ausgegeben werden.

```
Willkommen Admin! Was möchtest du tun?
1. Nutzer*in verwalten
2. Buch hinzufügen
3. Suche
9. Ausloggen
0. Beenden

>> 1

Möchtest du eine*n Nutzer*in hinzufügen oder löschen?
1. Hinzufügen
2. Löschen

>> 1

Name: Max Mustermann
Nutzername: max
Passwort: 12345678

Nutzer*in wurde erfolgreich hinzugefügt!
```

### User löschen
Der Admin kann Nutzer\*innen löschen. Hierfür wird der Nutzername benötigt. Sollte der\*die Nutzer*in nicht existieren, soll eine entsprechende Fehlermeldung ausgegeben werden. Sollte der\*die Nutzer\*in Bücher ausgeliehen haben werden diese vom System zurückgegeben. 

```
Möchtest du eine*n Nutzer*in hinzufügen oder löschen?
1. Hinzufügen
2. Löschen

>> 2

Nutzername: max

Nutzer*in wurde erfolgreich gelöscht!
```

### Bücher hinzufügen
Der Admin kann Bücher hinzufügen. Hierfür wird der Titel und Autor\*in des Buches benötigt. Sollte versucht werden ein identisches Buch hinzuzufügen, d.h. Titel und Autor\*in sind gleich, soll eine entsprechende Fehlermeldung ausgegeben werden.

```
Willkommen Admin! Was möchtest du tun?
1. Nutzer*in verwalten
2. Buch hinzufügen
3. Suche
9. Ausloggen
0. Beenden

>> 2

Titel: Der Herr der Ringe
Autor: J.R.R. Tolkien

Buch wurde erfolgreich hinzugefügt!
```

### Suche
Der Admin kann nach Nutzer*innen oder Büchern suchen. Als Suchbegriff kann der Name oder Nutzername von Nutzer\*innen oder der Titel oder Autor\*in von Büchern eingegeben werden. Sollte der\*die Nutzer\*in oder das Buch nicht gefunden werden, soll eine entsprechende Meldung ausgegeben werden.  

Reguläre Nutzer*innen können ebenfalls die Suche verwenden, jedoch dürfen diese nach Büchern suchen!

Als Ausgabe soll das Ergebnis der read-Methode des Readable-Interfaces verwendet werden.

```
Willkommen Admin! Was möchtest du tun?
1. Nutzer*in verwalten
2. Buch hinzufügen
3. Suche
9. Ausloggen
0. Beenden

>> 3

Suchbegriff: Tom

Ergebnisse:
 - Buch: Die Abenteuer des Tom Sawyer - Mark Twain
 - Nutzer*in: Tom Tester "ttester"
```

### Ausloggen
Der Admin kann sich ausloggen und man kann sich dann als beliebige\*r Nutzer\*in einloggen.

```
Willkommen Admin! Was möchtest du tun?
1. Nutzer*in verwalten
2. Buch hinzufügen
3. Suche
9. Ausloggen
0. Beenden

>> 9

Willkommen bei der Bücherei!
Nutzername: max
Passwort: 12345678

Willkommen Maximilian! Was möchtest du tun?
```

### Buch ausleihen
Nutzer\*innen können Bücher ausleihen. Hierfür wird der Titel des Buches benötigt. Sollte es zwei Bücher mit dem gleichen Titel gegeben muss der\*die Nutzer*in nochmal gefragt werden, welches Buch genau gemeint war. Überlegen Sie sich hierfür eine benutzerfreundliche Lösung.  
Ein Buch kann immer nur von einer Nutzer\*in ausgeliehen werden. Sollte versucht werden ein Buch auszuleihen, das bereits ausgeliehen ist, soll eine entsprechende Fehlermeldung ausgegeben werden.

```
Willkommen Maximilian! Was möchtest du tun?
1. Buch ausleihen
2. Buch zurückgeben
3. Suche
9. Ausloggen
0. Beenden

>> 1

Titel: Der Herr der Ringe
Bis: 05.01.2023

Buch wurde erfolgreich ausgeliehen!
```

### Buch zurückgeben
Nutzer\*innen können Bücher zurückgeben. Hierfür werden alle ausgeliehen Bücher aufgelistet. Der\*die Nutzer*in kann dann auswählen, welches Buch zurückgegeben werden soll.  
Sollte ein Buch nach dem Rückgabedatum zurückgegeben werden, soll zusätzlich eine entsprechende Meldung angezeigt werden.

```
Willkommen Maximilian! Was möchtest du tun?
1. Buch ausleihen
2. Buch zurückgeben
3. Suche
9. Ausloggen
0. Beenden

>> 2

1. Der Herr der Ringe - J.R.R. Tolkien

Welches Buch möchtest du zurückgeben?

>> 1

Buch wurde erfolgreich zurückgegeben!
```

### Beenden
Das Programm wird beendet. Gerne kann hier als Zusatzfeature eingebaut werden, dass alle Änderungen gespeichert werden, z.B. CSV-Datei, und diese beim nächsten Programmstart wieder geladen werden.

_stand 22. Dezember 2022_
