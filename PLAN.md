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

- Firebase server account seems to work - check if db access works
- Fix season builder so that all phases work correctly - pre, in, post season. Right now I think it
  builds only the current phase.
- Support TBD contests.

Probably caused by TBD game:

```
Exception in thread "main" java.util.NoSuchElementException: Collection contains no element matching the predicate.
        at net.slingspot.picks.server.espn.model.SeasonKt.contestants(Season.kt:87)
        at net.slingspot.picks.server.espn.model.SeasonKt.toSchedule(Season.kt:49)
        at net.slingspot.picks.server.espn.domain.EspnRepository.initialize(EspnRepository.kt:56)
        at net.slingspot.picks.server.espn.domain.EspnRepository$initialize$1.invokeSuspend(EspnRepository.kt)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
        at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:100)
```

## Integration Tests

- How to "replay" a season for testing.
- 