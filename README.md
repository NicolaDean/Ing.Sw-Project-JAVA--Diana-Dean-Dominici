# Progetto Ingegneria del Software 2020-2021



![alt text](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/blob/master/src/main/resources/images/logo.jpg?raw=true)

Implementazione del gioco da tavolo "Lorenzo il magnifico".\
Il progetto consiste nella realizzazione digitale e multiplayer del gioco.\
L'architettura generale consiste in un applicativo "SERVER" che ospita diverse partite ognuna con multipli "CLIENTS".\
L'approccio utilizzato per realizzare l'app è del tipo MVC (ModelViewControl) in cui appunto i dati, la vista e la logica sono concettualmente separate.

A livello di gameplay invece gli utenti potranno disporre di un client CLI (terminale) oppure di un client GUI (interfaccia grafica).

## Documentazione

### UML

* [Initial](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/tree/master/Deliveries/UML/Initial/UML.svg)
* [Final](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/tree/master/Deliveries/UML/Final)
* [HighLevel Summary](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/blob/master/Deliveries/UML/Final/UMLsummary.png)
* [Network Architecture](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/blob/master/Deliveries/Network/Network%20structure.png)

### JavaDoc

All project is documented with JavaDoc comment style
### Librerie e Plugins

| Libreria/Plugin  | Descrizione |
| -------------    | ------------- |
| Maven   | Tool to organize, simplify and automatize all JAVA app lifeCyle operations   |
| Shade   | Maven plugin to generate FATJar that include all dependency                             |
| GSON    | JSON parsing library (used for loading game resources such as cards,leaders.. and for network protocol)                        |
| JavaFX  | Graphic interface library                                                                    |


## Functionalities

### Base:
* FULL Rules
* GUI
* CLI
### Advanced Functionalities (3FA + Some extra)
* MultiMatch
* Server Persistance
* Resilience to disconnection of clients
* EXTRA:
   * Nickname Random Generator (NRG) (if user doesn't choose a nickname it will automaticly generate a random one)
   * Drag&Drop of Resources/cards inside GUI
   * Persistance of server is optimized to load saved match only if a user try to reconnect to it (doesnt load all saving match at start)
## Compile & Package
To generate jar we use SHADE maven plugin so you can simply type ```maven package``` and the jar will be generated inside ```"SHADE FOLDER"```
## Server
### Excecution:
To execute Server please run the following command
```
java -jar AM12.jar -server [-port -p <port_number>] [-r] [-res] [-resall] [-leaderfree]
```
#### Parameters:
* ```-port -p```      : to select a server port different from default *1234*
* ```-r ```           : to execute reloading/reconnection of server, allow server to properly load back match for user who want to reconnect
* ```-res ```         : Cheat to have infinite resources inside CHEST
* ```-resall ```      : Cheat to have infinite resources inside CHEST and STORAGE
* ```-leaderfree ```  : Cheat to activate leaders with no prerequisites
```diff
- (NOTE) BEFORE LAUNCH SERVER WITH PARAMETER ```-r ``` MOVE INSIDE THE SAME FOLDER AS JAR (move with cd command)
```
## Client
```java -jar AM12.jar [-cli] [-gui]```

#### Parameters:
* ```-cli```      : to excecute game in command line mode (CLI)
* ```-gui ```     : to excecute game in graphic interface mode (GUI)

## HOW reconnect to server after disconnection (of client or server)
```diff
- (NOTE 0.) CLI DOSNT SUPPORT WINDOW CMD please use WLS, Windows 10 Powershell,MAC,Linux
- (NOTE 1.) BEFORE LAUNCH CLIENT MOVE INSIDE THE SAME FOLDER AS JAR (move with cd command)
- (NOTE 2.) IT IS POSSIBLE TO RECONNECT IN AUTOMATIC ONLY TO THE LAST MATCH ABBANDONED (read note3 to reconnect other match) 
- (NOTE 3.) (TO reconnect to other match please modify the save file ```reconnectInfo.json``` by hand)
```
[Sequence Diagram of how it work](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/blob/master/Deliveries/Network/Reconnection.png)
### CLI
* Open the CLI
* (Wait the logo to appear) -> click ENTER
* If you read the message ```press -r o -reconnect to recconnect``` follow the instruction to reconnect
* press ```-r o -reconnect```
* If the match inside ```"reconnectInfo.json"``` exist and is still online/saved you will be reconnected
#### Reconnection failed possible cause
* File ```"reconnectInfo.json"``` doesnt exist
* You launched the JAR from a directory different from one of the JAR
* Match is already finished
* Saving file expiring date is reached
### GUI
* Open the GUI
* In the Home scene click  "Try Reconnect" button
* If the match inside ```"reconnectInfo.json"``` exist and is still online/saved you will be reconnected
