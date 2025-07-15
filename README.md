# AntiAFKMod

The Anti AFK Mod is a lightweight client-side utility designed to prevent Minecraft servers from marking players as AFK (Away From Keyboard). This mod periodically simulates player activity, such as small movements, head rotations, and jumping, based on customizable settings.

## Functionalities

-   A toggleable AFK prevention mode (default key: K)

-   Configurable options for:

    -   Movement

    -   Head rotation

    -   Jumping

    -   Movement distance

    -   Action interval in minutes

-   Full integration with Mod Menu and Cloth Config, allowing in-game configuration through a graphical menu

Perfect for players who want to stay active during long sessions or avoid server timeouts while idle.

## Useful Commands

| Action             | Commande                                       |
| ------------------ | ---------------------------------------------- |
| Clean              | `./gradlew clean`                              |
| Full clean         | `./gradlew clean --refresh-dependencies`       |
| Build              | `./gradlew build`                              |
| Clean & Build      | `./gradlew clean build`                        |
| Full clean & Build | `./gradlew clean build --refresh-dependencies` |

## How to create .jar

Do the `Build` command and find the .jar at `build/libs/antiafkmod-x.x.x.jar`.

## Authors

-   Giabibi
