# CubixMC API

L'API publique pour développer des jeux et des fonctionnalités sur le réseau
[CubixMC](https://cubixmc.fr).

Un **module** est un jar qui ne parle qu'à cette API. Le moteur de CubixMC (construit sur
Minestom) le charge au démarrage du serveur. Un module ne voit ni Minestom ni le moteur : il ne
casse donc pas quand Minestom change de version.

> ⚠️ Version `0.x` : l'API évolue encore, chaque version mineure peut casser la compatibilité.

## Dépendance

```kotlin
dependencies {
    compileOnly("fr.cubixmc:cubix-api:0.1.0") // fournie par le serveur, à ne pas embarquer
}
```

Java 25 est requis.

## Un module minimal

`src/main/resources/cubix-module.json` :

```json
{
  "id": "hello",
  "name": "Hello",
  "version": "1.0.0",
  "main": "com.exemple.hello.HelloModule",
  "apiVersion": "0.1",
  "depends": [],
  "softDepends": []
}
```

```java
public final class HelloModule implements CubixModule {

    @Override
    public void onEnable(ModuleContext context) {
        context.events().subscribe(PlayerJoinEvent.class, event ->
                event.player().sendMessage(Component.text("Bonjour " + event.player().name())));
    }
}
```

## Ce qu'offre l'API

- `CubixPlayer` : pseudo, langue, Bedrock, grade (`rank()`, `hasPermission`), et un `Audience`
  d'Adventure pour les messages, titres, sons.
- `player.wallet()` : coins et cristaux. Le solde se lit tout de suite ; un ajout ou un retrait
  est écrit en base hors du thread de jeu et ne passe jamais sous zéro
  (`wallet.take(Currency.COINS, 100).thenAccept(ok -> …)`). Ils restent cosmétiques.
- `Menu` : menus en coffre (1 à 6 lignes), icônes (`Icon.of`, `Icon.head`, `glowing()`),
  bordure et remplissage, boutons avec le type de clic, contenu modifiable une fois ouvert
  (`MenuView.set`). Un menu ouvert depuis un autre reçoit un bouton « Retour » (coin en bas à
  gauche, ou `backSlot`, ou `withoutBack`). `player.openMenu(menu)`.
- `player.sidebar()` : la barre de côté, `show(titre, lignes)` aussi souvent que voulu (seules
  les lignes changées partent au client).
- `player.hotbar()` : objets verrouillés de la barre rapide (ni déplaçables ni jetables), clic
  droit pour leur action.
- `player.play(mode)`, `player.connect(serveur)`, `player.setLanguage(langue)`,
  `player.teleportToSpawn()` ; `server.networkServers()` et `server.networkPlayerCount()`.
- `context.holograms()` : textes flottants propres à chaque joueur (sa langue, ses chiffres),
  `refresh()` pour les mettre à jour, retirés à l'arrêt du module.
- `server.boosts()` : boosts de gains ; `reward(joueur, jeu, base)` paie une partie avec boosts et
  bonus de grade.
- `Messages` et `MessageKey` : textes traduits dans `lang/*.properties`, avec anti-spam.
- `EventBus` : `PlayerJoinEvent`, `PlayerQuitEvent`, `PlayerRankChangeEvent`.
- `Scheduler` : tâches sur le thread de jeu ou en arrière-plan, annulées à l'arrêt du module.

## Règles

- **Identifiant** : minuscules, chiffres et tirets (`rush`, `hub-games`).
- **Dépendances** : `depends` démarre avant vous, sinon votre module ne démarre pas ;
  `softDepends` démarre avant vous seulement si le module est présent.
- **Compatibilité** : `apiVersion` `1.2` tourne sur toute API `1.x` avec `x ≥ 2`. En `0.x`, il faut
  exactement la même version mineure.
- **Cycle de vie** : `onEnable` au démarrage, `onDisable` à l'arrêt. Il n'y a pas de rechargement à chaud.
  Vos abonnements et vos tâches sont annulés automatiquement à l'arrêt.
- **Visible depuis un module** : le JDK, cette API, [Adventure](https://docs.advntr.dev),
  SLF4J, JSpecify, et les modules dont il dépend. Le reste doit être embarqué dans votre jar.

## Licence

[Apache 2.0](LICENSE).
