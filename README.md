# AStA-Digital-Forms

## Präsentationen

Zwischenpräsentation: https://docs.google.com/presentation/d/1JXEr9MCTt3LuM759lyKnwTBYi2K5tTDE3HMVEOUADk0/edit?usp=drivesdk

## Docker Compose

Zuerst müssen die `Docker Secrets` generiert werden.
Dazu `./init_secrets.sh` im Terminal ausführen. Unter Windows kann die `Git Bash` (`MINGW64`) verwendet werden.
Dies generiert mithilfe von `openssl rand -base64` zufällige Werte für Passwörter.

### Development

```
docker compose up
```
Startet Alle Dinste bis auf Keycloak.

### Hosting

```
docker compose --profile server up
```
Startet Alle Dinste.

## Details

Weitere Details finden sich in der Wiki unter: https://github.com/MaxTrautwein/AStA-Digital-Forms/wiki
