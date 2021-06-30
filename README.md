# Progetto Ingegneria del Software 2020-2021



![alt text](https://github.com/NicolaDean/ingswAM2021-Diana-Dean-Dominici/blob/master/src/main/resources/images/logo.jpg?raw=true)

Implementazione del gioco da tavolo "Lorenzo il magnifico".\
Il progetto consiste nella realizzazione digitale e multiplayer del gioco.\
L'architettura generale consiste in un applicativo "SERVER" che ospita diverse partite ognuna con multipli "CLIENTS".\
L'approccio utilizzato per realizzare l'app è del tipo MVC (ModelViewControl) in cui appunto i dati, la vista e la logica sono concettualmente separate.

A livello di gameplay invece gli utenti potranno disporre di un client CLI (terminale) oppure di un client GUI (interfaccia grafica).

## Documentazione

### UML

### JavaDoc

### Librerie e Plugins

| Libreria/Plugin  | Descrizione |
| -------------    | ------------- |
| Maven   | Strumento per automatizzare il processo di compilazione e Linking delle dipendenze all'interno di un progetto   |
| Shade   | Plugin Maven che permette di creare un "FatJAR" con tutte le dipendenze incluse                                 |
| GSON    | Libreria Java per parsing di JSON (utilizzata per risorse di gioco e protocollo di rete)                        |
| JavaFX  | Libreria per realizzare interfaccia grafica                                                                     |


## Funzionalità

## Utilizzo

## Server
### Esecuzione:
Per eseguire il server è necessario lanciare il comando 
```
java -jar AM12.jar -server [-port -p <port_number>] [-r] [-res] [-resall] [-leaderfree]
```
#### Parametri:
* ```-port -p```      : per selezionare una porta diversa da quella di default *1234*
* ```-r ```           : Per eseguire un "reload" del server dopo una disconnessione digitare il seguente comando
* ```-res ```         : Cheat per avere risorse infinite nella chest
* ```-resall ```      : Cheat per avere risorse infinite nella chest e nello storage
* ```-leaderfree ```  : Cheat per avere leader attivabili senza prerequisiti

#### (NOTA) PRIMA DI LANCIARE IL SERVER CON  ```-r ``` spostarsi nella stessa cartella del JAR
## Client
```java -jar AM12.jar [-cli] [-gui]```

#### Parametri:
* ```-cli```      : per eseguire il client in modalità terminale
* ```-gui ```     : per eseguire il client in modalità interfaccia grafica

#### (NOTA) LA GUI E' ESEGUIBILE ANCHE CON DOPPIO CLICK

## Come riconnettersi al server dal Client
##### (NOTA 1.) ESEGUIRE IL JAR DALLA STESSA CARTELLA CON IL FILE "reconnectInfo.json" (spostarsici con cd/.......)
##### (NOTA 2.) E' POSSIBILE RICONNETTERSI IN AUTOMATICO SOLO ALL'ULTIMA PARTITA ABBANDONATA 
##### (NOTA 3.) (modificando a mano il json reconectInfo.json anche a partite precedenti)
### CLI
Appena appare il Logo cliccare invio (UNA SOLA VOLTA)\
seguire le istruzioni del menu digitando "-r" o "-reconnect", utilizzerà in automatico le informazioni del json ```"reconnectInfo.json"```
### GUI
Appena appare la schermata Home cliccare il bottone "Try Reconnect" e utilizzerà in automatico le informazioni del json ```"reconnectInfo.json"```
