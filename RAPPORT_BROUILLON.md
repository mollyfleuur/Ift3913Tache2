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

![Couverture initiale des classes sélectionnées](docs/tache2/images/jacocoCouvertureInitiale.png)

Pour l'ensemble du module tika-core, le rapport initial indiquait notamment une couverture de 46 % des instructions et de 48 % des branches.
Nous avons ensuite examiné les différents packages et nous nous sommes intéressées au package :
`org.apache.tika.utils`

Celui-ci présentait plusieurs classes avec une couverture inférieure à 100 %, ce qui en faisait un bon point de départ pour rechercher des classes candidates.
Vérification de l'existence de tests
Une couverture inférieure à 100 % n'était pas suffisante pour sélectionner une classe. Nous devions également nous assurer que les classes possédaient déjà des tests.
Nous avons donc recherché les fichiers de test présents dans :
 `tika-core/src/test/java/org/apache/tika/utils`

Les fichiers suivants ont notamment été trouvés :

- `CharsetUtilsTest.java`
- `ConcurrentUtilsTest.java`
- ` RegexUtilsTest.java`
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

#### Configuration

ChatUniTest a été configuré dans le module `tika-core` afin de générer des tests à l'aide d'un modèle de langage exécuté localement avec Ollama. Le modèle utilisé est `codeqwen:v1.5-chat`.

Avant la génération, la commande `parse` de ChatUniTest a été exécutée afin d'analyser le projet. L'analyse s'est terminée avec succès et a identifié 361 classes et 1617 méthodes.

La génération des tests pour `CharsetUtils` a ensuite été lancée avec ChatUniTest. Afin d'observer plus précisément le comportement de l'outil, une première génération a été effectuée sur la méthode `isSupported`.

#### Génération pour `isSupported`

ChatUniTest a généré un test contenant notamment les trois vérifications suivantes :

```java
assertTrue(CharsetUtils.isSupported("UTF-8"));
assertFalse(CharsetUtils.isSupported("invalid-charset"));
assertFalse(CharsetUtils.isSupported(null));
```

Ces trois cas de test couvrent respectivement :

- un nom de charset valide ( `UTF-8` ) ;
- un nom de charset invalide
- une valeur `null`

Cependant, le test produit par ChatUniTest n'a pas pu être compilé et exécuté directement sans intervention manuelle.

#### Problèmes rencontrés

Le test généré contenait des imports Mockito , notamment :
```java
import org.mockito.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
```
Mockito n'est pas une dépendance du module tika-core et ces imports n'étaient pas utilisés par le test. Ils provoquaient donc des erreurs de compilation.
Le test généré contenait également du code utilisant la réflexion sur des éléments internes de CharsetUtils, notamment getCharsetICU. Cette partie reposait sur une interprétation incorrecte de l'implémentation de la classe et produisait notamment un oracle de la forme :

`assertTrue((Boolean) getCharsetICU.invoke(null, "UTF-8"));`

Cette vérification n'était pas valide et dépendait inutilement de détails internes de l'implémentation plutôt que du comportement public de `CharsetUtils`.
ChatUniTest a tenté de corriger automatiquement le test au cours des rounds suivants. Cependant, plusieurs tentatives ont échoué avec une `SocketTimeoutException: Read timed out` . Un appel direct au modèle `codeqwen:v1.5-chat` avec Ollama répondait correctement, ce qui indique que le modèle local était fonctionnel malgré les délais d'attente rencontrés lors des tentatives de correction de ChatUniTest.

#### Corrections manuelles 
Deux catégories principales de corrections fonctionnelles ont été nécessaires pour rendre ce premier test exploitable :
1. suppression des imports Mockito inutiles et indisponibles dans `tika-core` ;
2. suppression des vérifications incorrectes basées sur la réflexion et sur `getCharsetICU`.
Les trois assertions portant directement sur la méthode publique `CharsetUtils.isSupported` ont été conservées.

Le test corrigé a été ajouté dans :

`tika-core/src/test/java/org/apache/tika/utils/CharsetUtilsChatUniTest.java`

Des adaptations de format ont également été nécessaires pour respecter les règles du projet Apache Tika (en-tête de licence Apache, fins de lignes LF et saut de ligne final). Ces adaptations sont considérées séparément des corrections fonctionnelles du test généré.

#### Validation du test corrigé

Le test corrigé à été exécuté avec Maven :
`../mvnw.cmd "-Dtest=CharsetUtilsChatUniTest" test`

Le résultat obtenu est:
```Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Le test généré pour `isSupported` nécessite donc une intervention manuelle avant de pouvoir être intégré au projet, mais les cas de test pertinents proposés par le modèle ont pu être conservés et exécutés avec succès après correction.



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
