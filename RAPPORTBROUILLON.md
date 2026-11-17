# IFT3913 — Tâche 3

**Équipe :** Océane Molly-Fleur Ndong et Cyreanne Candy Andrianefa

## 1. Notre workflow Semgrep

Pour cette tâche, on devait faire en sorte que GitHub Actions échoue lorsqu'on introduit une nouvelle vulnérabilité dans Tika.
On a donc ajouté un workflow qui lance Semgrep sur notre code et sur une version de référence du projet. Ensuite, un petit script Python compare les deux rapports pour voir si de nouvelles alertes sont apparues.
On a utilisé `p/security-audit` et `p/java` pour les règles Semgrep.
Ce qu'on voulait surtout éviter, c'était que le workflow échoue à cause des vulnérabilités qui existaient déjà dans Tika. C'est pour ça qu'on a préféré comparer les résultats plutôt que de simplement faire échouer le build dès que Semgrep trouve quelque chose.

### Nos premiers tests

On a commencé par lancer le workflow sans ajouter de vulnérabilité. Il passait correctement.
Ensuite, on a ajouté temporairement un socket non chiffré pour voir si GitHub Actions allait réagir. Semgrep a trouvé une nouvelle alerte et le workflow a échoué. On a ensuite retiré ce premier code de test.
Ça nous a permis de vérifier que notre comparaison fonctionnait dans les deux situations.

## 2. Les vulnérabilités qu'on a ajoutées

### 1. Socket non chiffré — `InsecureNetworkClient.java`

<!-- CAPTURE GITHUB ACTIONS — VULNÉRABILITÉ 1 : remplacer le chemin ci-dessous par celui de votre capture dans le dépôt. -->
![Détection Semgrep du socket non chiffré](captures/semgrep-socket.png)

Pour notre première vulnérabilité, on a créé une classe dans `tika-core` qui ouvre une connexion avec `new Socket(host, port)`.
On a choisi cet exemple parce qu'on avait remarqué que Semgrep signalait déjà ce type de problème dans `NetworkParser.java`. On savait donc qu'on pouvait le reproduire et vérifier sa détection.
Le problème est que la connexion n'utilise pas TLS, donc les données échangées peuvent être interceptées.

**Résultat :** Semgrep a détecté notre nouvelle vulnérabilité et le workflow a échoué comme prévu.

### 2. Injection SQL — `InsecureSQLQuery.java`

<!-- CAPTURE GITHUB ACTIONS — VULNÉRABILITÉ 2 : remplacer le chemin ci-dessous par celui de votre capture dans le dépôt. -->
![Détection Semgrep de l'injection SQL](captures/semgrep-injection-sql.png)

Pour celle-ci, on a un peu plus galéré.
Au départ, on avait essayé de créer une injection de commande avec `ProcessBuilder`. Le code était vulnérable, mais Semgrep ne signalait rien avec nos règles. On a même essayé d'ajouter `p/java`, sans obtenir la détection qu'on cherchait.
Plutôt que de continuer à modifier les règles, on a finalement choisi une injection SQL.
On a créé une méthode qui construit une requête SQL en ajoutant directement une entrée utilisateur dans la chaîne. Sans requête préparée, cette entrée pourrait modifier la requête exécutée.

**Résultat :** cette fois, Semgrep a détecté la vulnérabilité avec la règle `formatted-sql-string`.

### 3.
### 4.
### 5.
### 6.
### 7.
