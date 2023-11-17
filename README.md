# Galactic Messenger

## Introduction

Galactic Messenger est une application de chat instantané. Elle possède de nombreuses fonctionnalités telles que :
- La création d'un serveur de chat
- La création d'un client
- L'échange textuel rapide entre plusieurs clients connectés au même serveur

> **Comment utiliser Galactic Messenger ?**

Tout d'abord, étant donné que l'application à entièrement été réalisée en Java, il vous faudra installer [Java](https://www.java.com/fr/download/).

Pour savoir si Java est bel et bien installé sur votre machine, ouvrez une terminal de commande et entrez la commande suivante :

`java --version`

Si le terminal vous affiche un message semblable, c'est que Java est correctement installé sur votre ordinateur :

```
java 20.0.2 2023-07-18
Java(TM) SE Runtime Environment (build 20.0.2+9-78)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.2+9-78, mixed mode, sharing)
```

Ensuite, les fichiers exécutables de Galactic Messenger ont besoin d'être *build* ("construit", "généré"). Pour cela, il vous faudra également installer [Gradle](https://gradle.org/install/) sur votre ordinateur.

Pour savoir si Gradle est bel et bien installé sur votre machine, ouvrez un terminal de commande et entrez la commande suivante :

`gradle --version`

Si le terminal vous affiche un message semblable, c'est que Java est correctement installé sur votre ordinateur :

```
------------------------------------------------------------
Gradle 8.4
------------------------------------------------------------
Build time:   2023-10-04 20:52:13 UTC
Revision:     e9251e572c9bd1d01e503a0dfdf43aedaeecdc3f
Kotlin:       1.9.10
Groovy:       3.0.17
Ant:          Apache Ant(TM) version 1.10.13 compiled on January 4 2023
JVM:          20.0.2 (Oracle Corporation 20.0.2+9-78)
OS:           Windows 11 10.0 amd64
```

Une fois cela fait, téléchargez (ou clonez) le fichier .zip de l'application.

Extrayez-le dans un dossier et ouvrez un terminal à la racine du dossier (là où tous les fichiers sont)...

> **Lancement**

Il y a deux manières de lancer l'application :

**1. En utilisant Java**\
Dans le terminal, entrez les commandes suivantes, dans l'ordre :

1. Pour construire le projet et créer les fichiers .jar :\
    `gradle build`
2. Lancer le serveur avec :\
    `java -jar galactic_messenger_server.jar <numéro de port>`
3. Lancer le client avec :\
    `java -jar galactic_messenger_client.jar <adresse ip du serveur> <numéro_de_port>` pour le client.

**2. En utilisant `make`**\
Nous avons intégré un raccourci afin de *build* et de lancer les fichiers en une seule commande. Cette commande à besoin de `make` pour fonctionner. Il est donc recommandé d'installer `make` sur votre machine, en suivant [ce tutoriel](https://stackoverflow.com/a/32127632/17311396).

Une fois `make` installé, il vous suffira d'entrer :
- `make runServer <numéro_de_port>` pour le serveur\
ou
- `make runClient <adresse ip_du_serveur> <numéro_de_port>` pour le client.

> **Et ensuite ?**

Il ne vous reste plus qu'à lancer l'application et profiter du chat instantané ! 🚀 **Bon chat !** 🚀
