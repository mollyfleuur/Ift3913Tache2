# Brouillon — Rapport Tâche 2

## Informations générales

### Environnement
- Java 17
- Maven Wrapper
- ChatUniTest 2.1.1
- Ollama
- CodeQwen 7B

## Sélection des classes à étudier

### Module sélectionné

Dans le cadre de cette tâche, nous avons choisi de travailler sur le module `tika-core` du projet Apache Tika.
Avant de sélectionner les classes à analyser, nous avons vérifié que le module pouvait être compilé et que ses tests existants pouvaient être exécutés correctement.
La commande suivante a été utilisée depuis la racine du projet :

```bash
./mvnw -pl tika-core -am test
 ```

L'option `-am (also make)` permet également de construire les modules dont tika-core dépend. Cette option était nécessaire puisque l'exécution de tika-core seul ne permettait pas de résoudre la dépendance locale `tika-annotation-processor`.
Le build s'est terminé avec `BUILD SUCCESS`.

### Analyse de la couverture existante

L'énoncé demande de sélectionner entre une et trois classes possédant déjà des tests, mais dont le code n'est pas couvert à 100 %. Nous avons donc commencé par analyser la couverture des tests existants avant d'ajouter de nouveaux tests.
Nous avons utilisé JaCoCo avec la commande :
`./mvnw -pl tika-core -am test jacoco:report`

![Couverture initiale des classes sélectionnées](docs/images/jacocoCouvertureInitiale.png)

Pour l'ensemble du module tika-core, le rapport initial indiquait notamment une couverture de 46 % des instructions et de 48 % des branches.
Nous avons ensuite examiné les différents packages et nous nous sommes intéressées au package :
`org.apache.tika.utils`

Celui-ci présentait plusieurs classes avec une couverture inférieure à 100 %, ce qui en faisait un bon point de départ pour rechercher des classes candidates.
Vérification de l'existence de tests
Une couverture inférieure à 100 % n'était pas suffisante pour sélectionner une classe. Nous devions également nous assurer que les classes possédaient déjà des tests.
Nous avons donc recherché les fichiers de test présents dans :
`tika-core/src/test/java/org/apache/tika/utils`

Les fichiers suivants ont notamment été trouvés :
-`CharsetUtilsTest.java`
- `ConcurrentUtilsTest.java`
-` RegexUtilsTest.java`
- `ServiceLoaderUtilsTest.java`
- `XMLReaderUtilsTest.java`
Nous avons ensuite croisé ces résultats avec ceux du rapport JaCoCo.

|**Classe**|**Test dédié existant** |**Couverture des instructions**|**Couverture des branches**|
|----------|------------------------|-------------------------------|---------------------------|
|CharsetUtils|Oui|83%|78%|
|ConcurrentUtils|Oui|80%|50%|
|RegexUtils|Oui|91%|100%|
|ServiceLoaderUtils|Oui|100%|100%|
|XMLReaderUtils|Oui|42%|33%|

ServiceLoaderUtils a été écartée puisqu'elle était déjà couverte à 100 % et ne répondait donc pas au critère de sélection.

### Prise en compte de la complexité des classes

Nous avons également pris en compte la taille des classes afin de sélectionner un ensemble suffisamment substantiel pour l'expérimentation, tout en restant raisonnable pour l'analyse détaillée des tests et des mutants.
Nous avons obtenu les tailles suivantes :

|**Classe**|**Nombre de lignes**|
|----------|--------------------|
|CharsetUtils|177|
|ConcurrentUtils|49|
|RegexUtils|57|
|XMLReaderUtils|1201|

XMLReaderUtils présentait une couverture particulièrement faible (42 % des instructions et 33 % des branches), mais comptait environ 1201 lignes. Sa taille était très supérieure à celle des autres candidates et aurait considérablement augmenté la portée de l'expérimentation, notamment lors de l'analyse des mutants et de l'ajout éventuel de tests manuels.
Nous avons donc préféré sélectionner plusieurs classes de taille raisonnable présentant des profils de couverture différents.

### Classes retenues

Les trois classes finalement retenues sont :
1. **CharsetUtils**
2. **ConcurrentUtils**
3. **RegexUtils**

Ces trois classes possèdent donc toutes des tests existants sans atteindre une couverture complète, tout en présentant des niveaux de couverture et des caractéristiques suffisamment différents pour permettre de comparer les résultats de la génération automatique de tests et de l'analyse par mutation.

## CharsetUtils — Molly

### ChatUniTest
...

### Oracles
...

### PIT
...

### Tests manuels
...

## ConcurrentUtils — Cyreanne

### ChatUniTest
...

### Oracles
...

### PIT
...

### Tests manuels
...

## RegexUtils — Cyreanne

### ChatUniTest
...

### Oracles
...

### PIT
...

### Tests manuels
...

## Comparaison et conclusion
...
