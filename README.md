# Projet de Dessin Vectoriel (projet-al-2025-Draw)

Ce projet est une application de dessin vectoriel développée en Java. Il est composé de trois outils en ligne de commande :
1. `edit` : Éditeur de dessin vectoriel interactif.
2. `merge` : Outil de fusion de deux fichiers de dessin.
3. `v2bmp` : Convertisseur de dessin vectoriel vers une image PNG.

## Compilation et Exécution
L'application utilise Maven pour la gestion des dépendances et la compilation.
```bash
mvn clean compile
```

- **Pour lancer l'éditeur (`edit`) :**
  ```bash
  mvn exec:java -Dexec.mainClass="fr.univrouen.draw.app.EditApp"
  ```
- **Pour fusionner deux fichiers (`merge`) :**
  ```bash
  mvn exec:java -Dexec.mainClass="fr.univrouen.draw.app.MergeApp" -Dexec.args="file1.vec file2.vec out.vec"
  ```
- **Pour convertir en image (`v2bmp`) :**
  ```bash
  mvn exec:java -Dexec.mainClass="fr.univrouen.draw.app.V2BmpApp" -Dexec.args="in.vec out.png"
  ```

## Principes S.O.L.I.D. et Patrons de Conception
Afin de produire un code robuste, maintenable et évolutif, nous avons respecté les principes S.O.L.I.D. ainsi que plusieurs patrons de conception :

### 1. Le Patron Composite (Construction)
**Pourquoi l'avoir utilisé ?**
Le projet manipule des primitives de dessin (Ligne, Rectangle, Cercle, Ellipse) mais aussi des groupes de dessins (qui peuvent eux-mêmes contenir des groupes). Le patron **Composite** permet de traiter de manière uniforme les éléments simples (`Line`, `Circle`...) et les éléments composés (`Group`). 
Ainsi, l'interface `Shape` est implémentée par les primitives et par `Group`. Le code client (`Drawing`, visiteurs) n'a pas besoin de faire la distinction entre une forme isolée et un groupe complexe.

### 2. Le Patron Visiteur (Comportement/Responsabilités)
**Pourquoi l'avoir utilisé ?**
Nous avions besoin d'effectuer plusieurs opérations différentes sur l'arbre de formes : le rendu graphique (Java2D) et la sérialisation en XML. Si nous avions ajouté les méthodes `draw(Graphics2D)` et `toXml()` directement dans l'interface `Shape` et ses implémentations :
- Nous aurions violé le principe **SRP** (Single Responsibility Principle) : une classe comme `Circle` aurait géré à la fois la logique métier, l'affichage et la persistance.
- Nous aurions violé le principe **OCP** (Open/Closed Principle) : l'ajout d'un nouveau format d'export aurait nécessité de modifier toutes les classes de formes.
Le patron **Visiteur** résout cela en extrayant ces opérations dans des classes séparées (`XMLSerializerVisitor`, `GraphicsRendererVisitor`). Pour ajouter un nouveau traitement, il suffit de créer un nouveau visiteur sans toucher aux classes du modèle.

### 3. Le Patron Commande (Comportement)
**Pourquoi l'avoir utilisé ?**
L'éditeur de dessin est géré via un interpréteur de commandes en ligne (`new`, `line`, `grp`, `save`...). Le patron **Commande** (ici simplifié via `CommandInterpreter`) permet de séparer la lecture des entrées utilisateur de la logique d'exécution des modifications sur le modèle.

### Respect des Principes S.O.L.I.D.
- **SRP** : Séparation stricte entre le Modèle (`fr.univrouen.draw.model`), le Rendu (`fr.univrouen.draw.rendering`), et la Persistance (`fr.univrouen.draw.persistence`).
- **OCP** : L'utilisation du patron Visiteur rend le modèle fermé à la modification mais ouvert à l'extension (on peut ajouter de nouveaux visiteurs). L'interface `Shape` permet d'ajouter de nouvelles formes sans modifier les collections qui les stockent.
- **LSP** : Toutes les formes (`Line`, `Group`, etc.) respectent parfaitement le contrat de l'interface `Shape`.
- **ISP** : Les interfaces sont petites et ciblées (`Shape`, `ShapeVisitor`).
- **DIP** : Le code de haut niveau (`Drawing`, `CommandInterpreter`) dépend de l'abstraction `Shape` et non des implémentations concrètes.
