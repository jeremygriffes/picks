# Plan

Build the game algorithm to determine:

1. How a game works
2. Conditions for victory

In order to do that, the game algorithm will need to understand the variables that are at play in a
game, such as players, contests, and so forth. You'll need data models for those.

Players and users may be synonymous, or they may be separate entities with associations. For
example, a system admin doesn't need to be a player. Any player, however, must also be a user.

When the notion of users becomes necessary, you'll want to start exploring OAuth integration and
user roles. Ideally that can be deferred until the game mechanics are resolved.

