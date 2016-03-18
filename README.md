# GTI745-Labo4
Laboratoire 4 pour GTI745 (2016)


Instruments (https://fr.wikipedia.org/wiki/General_MIDI)
Sûr : Piano (Chun), Guitare classique, Violon,
Pas sûr : Clarinette, Flute, Percussion

2 points
1 - Click
  - menu radial (Chun) avec les instruments et les patrons (Vincent)
  - Fenêtre pour définir les patrons (Vincent) (plus tard....)

2 points  
2 - déroulement (Vincent)
  - connecteurs (sens et nombre de fois et vitesse) et le point de départ (le son du composant sera produit un fois atteint sa position) (Chun)
    - 5 connecteurs max
    - connecteur représente le patron joué en ce moment (Vincent)
  - jouer en boucle (Vincent)
  - parent-dossier

1 point (Chun)
3 - contrôle (play et pause et la vitesse globale)
  - aller à une position particulière

1 point (Vincent)
4 - effet visuel pour représenter le chemin

1 point
5 - vue linéaire (Vincent)

Optionnel
6 - enregistrer la scène

Config pour le lab 3
=================================================================================================================
Une vue d'ensemble des fichiers:

.
|-- doNotCompile
|   |-- MultitouchFramework-MOUSE.java        # à utiliser sans écran multitactile, sur Windows 7 à 10
|   `-- MultitouchFramework-JWINPOINTER.java  # à utiliser avec un écran multitactile, sur Windows 10
`-- src
    |-- AlignedRectangle2D.java
    |-- CLASSES.html             # documentation à lire
    |-- Constant.java
    |-- GraphicsWrapper.java
    |-- MultitouchFramework.java # copier par dessus ce fichier une des versions dans doNotCompile
    |-- Point2D.java
    |-- Point2DUtil.java
    |-- SimpleWhiteboard.java    # modifiez surtout (ou même seulement) ce fichier
    `-- Vector2D.java


Étapes pour le setup du projet avec Eclipse :

1- Lancez Eclipse (si vous avez des problèmes au labo multimédia, utilisez la version dans le dossier C:\eclipse-log720)

2- Importez le projet GTI745_Labo03 : File/Import/General/Existing Projects into Workspace puis sélectionnez le dossier du projet

3- Assurez-vous que la version du JRE est la bonne : ce labo fonctionne uniquement avec le JDK 8 x64
	Sur les machines du labo multimédia :
		Allez dans Windows/Preferences/Java/Installed JREs et ajoutez "Standard VM" avec comme chemin C:\Oracle\java\jdk8_65_x64
		L'environnement devrait se mettre à jour
			Sinon, allez dans Project/Properties/Java Build Path/Libraries et éditez le JRE référencé pour utiliser le bon JRE
	Sur une autre machine :
		Obtenez et installez la plus récente version du JDK 8 x64 sur http://www.oracle.com/technetwork/java/javase/downloads/index.html (le JRE seul peut suffir)
		Allez dans Windows/Preferences/Java/Installed JREs et ajoutez "Standard VM" avec le chemin du JDK récemment installé
		Allez dans Project/Properties/Java Build Path/Libraries et éditez le JRE référencé pour utiliser le bon JRE

4- Ouvrez MultitouchFramework.java et lancez le logiciel


Si vous désirez bâtir le projet de toute pièce, tenez compte de ce qui suit :
- L'environnement Java devrait être 1.8 x64
- Il y a 3 JAR à référencer : JWinPointer.jar, gluegen-rt.jar et jogl.jar
- Ces deux derniers JAR ont besoin de DLL et il faudra peut-être préciser l'emplacement de ces librairies


Fonctionnement avec/sans écran tactile :
- Sans écran tactile :
	- Remplacez MultitouchFramework.java par MultitouchFramework-MOUSE.java
	- Vous pouvez simuler un doigt en appuyant sur Ctrl et en faisant un clic-gauche
	- Refaites cette opération sur un marqueur de doigt pour pouvoir le déplacer
	- Simulez le retrait d'un doigt en appuyant sur Ctrl et en faisant un clic-droit sur un marqueur de doigt
- Avec écran tactile :
	- Remplacez MultitouchFramework.java par MultitouchFramework-JWINPOINTER.java
	- Utilisez vos doigts pour les contrôles


Références :
- JWinPointer.jar : http://www.michaelmcguffin.com/code/JWinPointer/
- SimpleWhiteboard original : http://profs.etsmtl.ca/mmcguffin/code/#SimpleWhiteboard
- =================================================================================================================
