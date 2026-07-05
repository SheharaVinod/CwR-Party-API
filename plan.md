# CwR-Party-API Improvement Plan

Based on integration with CwR-CTF, here are the changes needed to make the Party API more useful for external plugin coordination.

## 1. Add Party Creation Timestamp

```java
// Party.java
private final long createdAt;

public Party(Player owner, Plugin plugin) {
    this.createdAt = System.currentTimeMillis();
}

public long getCreatedAt() {
    return createdAt;
}
```

This allows external plugins to determine join order for fair team assignment (first-created party = first to join a game mode).

## 2. Expose Party List from PartyManager

```java
// PartyManager.java
public Collection<Party> getAllParties() {
    return new HashSet<>(playerPartyMap.values());
}
```

Useful for debugging, admin commands, and external plugin coordination.

## 3. Add PartyJoinEvent Details

```java
// PlayerJoinPartyEvent.java — consider adding:
public Player getOwner() { ... } // the party owner who accepted/accepted the join
public boolean isOwnerJoining() { ... } // true if the new player is the owner
```

Makes it clearer for listeners to know who joined and under what context.

## 4. Add PartyOwnerChangeEvent

When the party owner leaves and ownership transfers (if implemented in the future), fire an event so external plugins can update their state.

## 5. Expose RemovePlayer Without RemovingTypes Abstraction

The `RemovingTypes` abstraction is flexible but makes it harder for external plugins to remove players. Consider adding a convenience method:

```java
// Party.java
public void removePlayer(Player player);
public void kickPlayer(Player player);
```

These would internally create the appropriate `LeftPlayer`/`KickPlayer` instances.

---

## Current API Surface Summary (for reference)

```
PartyManager:
  getInstance()                              -> PartyManager
  isInAParty(Player)                         -> boolean
  getPartyOf(Player)                         -> Party
  getMembersOf(Party)                        -> List<String>
  getAllMembersOf(Party)                     -> List<String>
  getPartyList()                             -> List<String>

Party:
  getOwner()                                 -> Player
  getMembers()                               -> LinkedList<Player>
  getCopyOfAllMembersIncludingTheOwner()     -> List<Player>
  isOwner(Player)                            -> boolean
  disbandParty()                             -> void
  addPlayer(Player)                          -> void
  removePlayer(RemovingTypes)                -> void
  invite(Player, InvitationType)             -> void
  open() / closed()                          -> void
  isOpened()                                 -> boolean
  mergeWith(Party)                           -> void
  promote(Player)                            -> void

Events:
  PlayerCreatePartyEvent, PlayerJoinPartyEvent
  PlayerLeavePartyEvent, PlayerKickPartyEvent
  PlayerPromotePartyEvent, OwnerInviteToPartyEvent
  PartyDisbandEvent, PartyCloseEvent, PartyOpenEvent
  PartyInvitePartyToMergeEvent, OwnerInviteToMergePartyEvent
  PartyJoinInvitationExpireEvent, PartyMergeInvitationExpireEvent
  OwnerOfflineEvent

RemovingTypes (interface):
  LeftPlayer(Player, LinkedList<Player>)     -> implements RemovingTypes
  KickPlayer(Player, LinkedList<Player>)     -> implements RemovingTypes
  RemovingTypes.execute(Party, PartyManager)
```
