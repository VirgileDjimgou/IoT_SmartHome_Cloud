# IoT_SmartHome_Cloud
(FH Aalen) Cloud-IoT Projekt : Entwicklung einer Android App zur Visualisierung von emulierten Sensordaten durch einen cloud Umgebung..

![Alt text](smart_home-1.PNG)

<span id="result_box" class="anchor"></span>Dieser Dokumentation
beschreibt eine Fallstudie zum Erstellen eines Smart Home mit Raspberry
Pi-Gerät und die in einen Cloud Umgebung integriet ist . Wir haben
AndroidThings als Betriebssystem auf dem Raspberry-Gerät und Firebase
eine bekannte Service von Cloud Anbieter Google Cloud Platform (GCP) als
Backend-Dienst zum Speichern der Daten verwendet (der in Echtzeit mit
allen authentifizierten Geräten synchronisiert wird). Der Projekt gibt
eine kurze Beschreibung des aktuellen Trends bei IoT-Geräten (populäre
IoT-Plattformen wie Raspberry Pi, Arduino usw.). Diese Projekt erwähnt
auch eine kurze Einführung in AndroidThings OS (von Google
bereitgestellt), das für IoT-Geräte entwickelt wurde und das Java-Framework sowie Firebase als  Backend as a Service (BAAS) für Anwendungsentwickler unterstützt, um IoT-Anwendungen mit Java oder Kotlin zu entwickeln

-   AndroidThings
    -------------

<span id="result_box1" class="anchor"></span>AndroidThings bringt jetzt
die gesamte Android-Plattform auf Geräte, auf denen Sie Java-basierte
IoT-Anwendungen erstellen können. Es hat das Potenzial, das Spiel für
IoT zu verändern, wie es Android für Geräte getan hat.\
Das Problem, mit dem wir die meisten IoT-Entwickler konfrontiert haben,
ist das Sammeln von Sensordaten, den Transport und das Speichern dieser
Daten im Backend. Und der wahre Wert liegt in der Analyse dieser Daten
für Alerts, Visualisierung usw. Und genau hier sehen wir viele Menschen,
die die Werte von Firebase, Google Cloud Services, besser verstehen.
AndroidThings, entwickelt von Google, fügt all diese Teile zu einer
überzeugenden IoT-Plattform zusammen.\
Eines der interessanten Dinge, die als Stärke von Android Things genannt
werden, ist, dass OS-Updates von Google selbst verteilt werden.

-   **Firebase Backend as a Service von Google Cloud Platform (GCP)**

<span id="result_box2" class="anchor"></span>Firebase bietet eine
schnelle Möglichkeit, sensorische Daten, die auf Geräteebene gesammelt
wurden, dauerhaft zu speichern, und es funktioniert hervorragend mit den
Android-APIs, die von Android Things unterstützt werden. Viele
Programmierer von Mobilgeräten und Geräten, mit denen ich konfrontiert
bin, haben Probleme mit der serverseitigen Programmierung. Firebase kann
wirklich helfen, diese Lücke zu überbrücken und es einfacher zu machen.\


-   **Authentifizierung**

<span id="result_box3" class="anchor"></span>Jede geeignete App hat
irgendeine Form von Sicherheit. Die Firebase-Authentifizierung bietet
diese Form der Sicherheit, indem sie eine OAuth-Plattform bereitstellt,
die gängige OAuth-Anbieter wie Facebook, Google+ und Twitter integriert.

-   <span id="result_box4" class="anchor"></span>**Echtzeitdatenbank**

<span id="result_box6" class="anchor"></span>Dies ist eine
NoSQL-Cloud-Datenbank. Okay, das bedeutet, dass alle Daten in Ihrer
Anwendung online in der Cloud gespeichert werden und ein zusätzlicher
Vorteil ist, dass es in Echtzeit (wie es sich ändert) über alle
verbundenen Clients synchronisiert wird.

-   <span id="result_box7" class="anchor"></span>**Umsetzung: Smart Home
    mit RaspberryPi.**\
    \
    Diese Implementierung erfordert Folgendes:\
    \
    **Software:**\
    \
    1. Java.\
    2.Android Anwendungsentwicklung.\
    \
    **Hardware:**\
    \
    1. HimbeerePi 3\
    2. Ethernet-Kabel\
    Birne 3.LED\
    4,1 KΩ Widerstand\
    5.Female zu männlichen Überbrückungsdrähten\
    6.Bretbrett\
    7.Power Versorgung für Raspberry Pi\
    8. SD-Karte (8 GB oder höher)

<!-- -->

-   <span id="result_box8"
    class="anchor"></span>**Software-Implementierung**\
    \
    1.Android Client App - spricht mit Firebase, aktualisiert Wert
    in Firebase.

    2.Android Things App - liest Wert von Firebase und sendet
    Anweisungen an die Led-Lampe (durch Raspberry Pi-Gerät)

**Workflow :**

![](media/image1.png){width="6.925in" height="4.644444444444445in"}

-   <span id="result_box9" class="anchor"></span>Jedes internetfähige
    Android-Handy mit Android-App verbindet sich über die
    OAuth-Authentifizierung mit Firebase.

-   Nach erfolgreicher Authentifizierung wird der Wert von Firebase
    aktualisiert / gelesen.

-   Firebase aktualisiert die Werte auf IoT-Geräten mit AndroidThings OS
    und Android IoT-App.

-   IoT-Gerät steuert alle angeschlossenen elektronischen Geräte.

        

<!-- -->

-   <span id="result_box10" class="anchor"></span>**IoT-Gerät in
    Cloud verbinden.**

<span id="result_box13" class="anchor"></span>1. Laden Sie die
Developer-Vorschau von der Android Things-Website herunter.\
https://developer.android.com/things/preview/download.html\
\
2. Laden Sie Android Thing OS für Raspberry Pi herunter\
\
3. Nach dem Formatieren Ihrer SD-Karte müssen wir das Betriebssystem
installieren.\
\
4. Nach dem Hochfahren stellt RaspberryPi eine Verbindung zu Ihrem
Netzwerk über Ethernet her.\
\
5. Sobald es erfolgreich verbunden ist, sehen Sie die folgende
Nachricht\
verbunden mit &lt;deviceip-adresse&gt;: 5555

6\. LED positive Pin zu 7 (BCM4) und Negativ zu 9 (Ground)\
\
7. Erstellen Sie ein Firebase-Projekt unter
https://firebase.google.com/.

8\. Gehen Sie zum Abschnitt Regeln und ändern Sie die Regeln wie folgt

"rules": { ".read": "true", ".write": "true" }

![Alt text](firebaseread-1.PNG)


}

![](media/image2.png){width="6.925in" height="4.776388888888889in"}

-   <span id="result_box14" class="anchor"></span> Führen Sie nach der
    erfolgreichen Kompilierung Ihr erstes Android Things-Projekt aus,
    das mit Firebase konfiguriert ist.

-   Klicken Sie im Android Studio auf die Schaltfläche "Ausführen" und
    wählen Sie Ihr Gerät aus.

-   Nun wird Ihre Anwendung auf Ihrem Gerät laufen und Sie werden sehen,
    dass die Glühbirne blinkt.

Aktivirien E-Mail, Google, Facebook Login im Bereich Authentifizierung.

![](media/image3.png){width="6.925in" height="1.8138888888888889in"}

<span id="result_box16" class="anchor"></span>Fazit\
\
Das Internet der Dinge in Cloud Umgebung ist einfacher zu implementieren
als der durchschnittliche Mensch denken würde. Wir haben großes
Potenzial im IoT. Dies war nur eine einfache Projekt für IoT. Von
Sicherheitssystemen bis zum Gesundheitswesen; Von Transportunternehmen
bis zur Lagerverwaltung sind die Möglichkeiten in IoT einfach endlos.
Mit dem Aufkommen von mobilen Apps wie IFTTT hat die Kommunikation die
nächste Stufe erreicht. Wir können getrost sagen, dass die Zukunft dem
IoT und Cloud Computing gehört.

<span id="result_box17" class="anchor"></span>**Verweise:**

[***https://developer.android.com/things/index.html***](https://developer.android.com/things/index.html)**\
**[***https://developer.android.com/things/hardware/raspberrypi.html***](https://developer.android.com/things/hardware/raspberrypi.html)**\
**[***http://fritzing.org/ (Electrical layout diagram
tool)***](http://fritzing.org/)


Who made this
--------------

| <a href="https://github.com/VirgileDjimgou"><img src="https://avatars1.githubusercontent.com/u/8148300?s=400&v=4" alt="IoEngine" align="left" height="100" width="100" /></a>
|---
| [VirgileDjimgou](https://github.com/VirgileDjimgou)

Contribute
----------

1. Create an issue to discuss about your idea
2. [Fork it] (https://github.com/VirgileDjimgou/IoT_SmartHome_Cloud)
3. Create your feature branch (`git checkout -b my-new-feature`)
4. Commit your changes (`git commit -am 'Add some feature'`)
5. Push to the branch (`git push origin my-new-feature`)
6. Create a new Pull Request
7. Profit! :white_check_mark:


Bugs and Feedback
-----------------

For bugs, questions and discussions please use the [Github Issues](https://github.com/VirgileDjimgou/IoT_SmartHome_Cloud/issues).
