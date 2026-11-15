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
