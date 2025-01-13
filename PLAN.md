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

Fix /users db access:

Bad type on operand stack
Exception Details:
Location:
com/google/firestore/v1/RunQueryRequest$Builder.getReadTimeFieldBuilder()Lcom/google/protobuf/SingleFieldBuilderV3; @43: invokespecial
Reason:
Type 'com/google/protobuf/Timestamp' (current frame, stack[3]) is not assignable to 'com/google/protobuf/AbstractMessage'
Current Frame:
bci: @43
flags: { }
locals: { 'com/google/firestore/v1/RunQueryRequest$Builder' }
stack: { 'com/google/firestore/v1/RunQueryRequest$Builder', uninitialized 24, uninitialized 24, 'com/google/protobuf/Timestamp', 'com/google/protobuf/GeneratedMessageV3$BuilderParent', integer }
Bytecode:
0000000: 2ab4 004a c700 322a b400 2310 079f 000a
0000010: 2ab8 018d b500 5a2a bb00 4459 2ab4 005a
0000020: c001 8b2a b601 6c2a b601 70b7 0173 b500
0000030: 4a2a 01b5 005a 2a10 07b5 0023 2ab6 00c0
0000040: 2ab4 004a b0                           
Stackmap Table:
same_frame(@23)
same_frame(@54)


## Integration Tests

- How to "replay" a season for testing.
- 