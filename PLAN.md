# Plan

1. ~~Learn KOIN, set up basic DI.~~
2. ~~Build Ktor client on the server so it can reach ESPN~~
3. ~~Build ESPN interface. Need to do it soon while there are still active games.~~
4. ~~Integrate Oauth so there's some notion of users and roles.~~ EDIT: will use firebase for now
5. Figure out deployment. Get server running live so I can work out kinks early.
6. Build simple database. Want to figure this out so that I can do non-destructive deployment.
7. Build Ktor server
8. Build basic REST endpoints for administration along with basic admin UI to exercise it.

## Next

- Fix season builder so that all phases work correctly - pre, in, post season. Right now I think it
  builds only the current phase.

## Integration Tests

- How to "replay" a season for testing.
- 