# LPOO_G73 - Skane Dweller

In this game, the player controls a giant snake known as **Skane**. The player's
objective is, putting it simply, to wreak havoc. Be careful with armed enemies,
though.

This project was developed by _João de Jesus Costa_ (_up201806560_@fe.up.pt) and
_João Lucas Silva Martins_ (_up201806436_@fe.up.pt).

## Features

### Implemented features

- **Movement control** - the player can control the movement of the **Skane**.
- **Bury** - the **Skane** is able to bury itself underground to move unnoticed
  and safe from enemies.
- **Breathing** - the **Snake** needs air the breath so it needs to come to the
  surface from time to time.
- **Oxygen level indicator** - the **Skane** progressively changes color, so the
  player knows if it can go underground/how high are the oxygen levels.
- **Civilians** - there are civilians all over the map running from you (because
  they are scared).
- **Military** - there are foot soldiers trying to stop you using their knifes.
- **Eating** - the more (dead) people you eat, the more health you have and the
  bigger you become.
- **Suffering damage** - Enemies can damage the **Skane** with their weapons,
  making it shrink.
- **Death** - If a **Skane** is too small to withstand incoming damage, it dies.

### Planned features

- **Damaging/Killing enemies** - for now, all enemies are invulnerable.
- **Enemy spawning** - enemies should spawn over time during the game.
- **Corpse spawning** - dead enemies should leave their corpse on the ground.
- **Stronger military** - the soldiers should be able to use guns, cars, and maybe
  even helicopters.
- **Enemy alertness state** - indicators for whether and enemy is aware of
  your presence.
- **Perk/upgrade like rewards** - killing stronger enemies (e.g.: tanks) should
  yield special extra rewards, like movement speed upgrades.

### Abandoned ideas

- **Jumping** - the **Skane** will not be able to jump to attack helicopters or
  get over small walls after unburying itself.

## Design

### Enemy movement

#### Problem in Context

In this game, enemies need to be able to move. The problem was: different enemies
might move in different ways. During the early development of enemy movement,
the movement was equal for every type of enemy (only _Civilians_ at the time)
and was being calculated in the _Room class_. This was problematic because
it made creating new movement strategies and selecting the correct strategies
for each enemy type difficult. It also violated the Single-responsibility
principle.

![Old movement technique code for civilians.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/3fc057d898efcb07976134eb3a43a203a047f502/src/main/java/room/Room.java#L268-L349)

#### The Pattern

We fixed this issue using the [Strategy design pattern](https://refactoring.guru/design-patterns/strategy).
This pattern was a good fit for the problem at hand since it allowed us to
choose movement strategies for each enemy in our game at the moment they are
created. With this, we don't have to worry about what type of enemy we're trying
to move, the Model (MVC) doesn't need to know how each of the enemy types
moves and, as an added benefit, we avoid repeating code if two different
enemy types use the same movement strategy.

#### Implementation

The following picture illustrates how the pattern's roles were mapped to the
game's classes.

![Movement strategy pattern](/docs/uml/movement_strategy.png)

These classes can be found in the following files:

- [Element](/src/main/java/room/element/Element.java)
- [Entity](/src/main/java/room/element/Entity.java)
- [MovableElement](/src/main/java/room/element/MovableElement.java)
- [MoveStrategy](/src/main/java/room/element/MoveStrategy.java)
- [MeleeMoveStrat](/src/main/java/controller/strategy/MeleeMoveStrat.java)
- [ScaredMoveStrat](/src/main/java/controller/strategy/ScaredMoveStrat.java)

#### Consequences

We believe the implementation of this design pattern only brought benefits to
our design:

- We can share and reuse movement strategies between different enemy types
  and/or instances.
- We segregate the movement rules from the Model (MVC).
- Creating new movement techniques is faster and simpler.
- It's easy to access all the code related to a movement strategies in order
  to fix, diagnose or just read the code.

### Terminal size

#### Problem in Context

Since both members of the group use a tilling window manager, we noticed from
the very start of the project that resizing the terminal window caused problems
with **lanterna** when trying to select an appropriate size for the playing
area.  
It is also worth noting that resizing the terminal windows during _gameplay_,
shouldn't change the size of the playing area, since that would benefit users
with larger screens.

[How the game room size was selected at compile time and never updated after that.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/a74d85d6ec5bedb9570d0330f90c673ca46fd327/src/main/java/Game.java#L28)

//TODO falar das solucoes alternativas pensadas

#### The Pattern

In order to fix this we used the observer pattern. When the terminal window
resizes, a resize handler is notified. After being notified, it updates its
internal state to reflect the new terminal size.

#### Implementation

The following classes were adapted/developed/used to implement this pattern:

- _TerminalResizeListener_ - An interface from **lanterna** for terminal resize
  event observers.
- _TerminalResizeHandler_ - Our implementation of the previous interface.
- _Terminal_ - **Lanterna's** class. It takes the role of the observable and
  notifies all subscribed _TerminalResizeListener_ each time it resizes.
- _Gui_ - Instantiates both _Terminal_ and _TerminalResizeHandler_. It also
  subscribes a _TerminalResizeHandler_, as a resize listener, to its _Terminal_
  instance. Uses the _TerminalResizeHandler_ to update the screen after each
  resize.

The presented design removes the need for the Gui Class to manage the terminal
directly, thus avoiding violations of Single-responsibility principle. As an added
benefit, it also avoids the constant polling of the terminal's size.

#### Consequences

// TODO  
Be careful if we want to resize the screen/update the room's info.

### Pathfinding for Enemies

Observer pattern here.

### Enemy Movement

Strategy pattern here.

## Known Code Smells and Refactoring Suggestions

### Bloaters

The [_Room class_](/src/main/java/room/Room.java) is Bloater (_Large class_). This
is problematic because finding specific code segments to work on inside the class
can prove cumbersome and the class as a `tendecy' to violate the
Single-responsibility principle.

We could improve the code by dividing the Room class into smaller, more specific,
classes.

There's also the ray-casting helper (private) functions that take 6 arguments,
which are bloaters, but we believe all alternatives are inferior design-wise.

### Dispensables

The ray-casting related code inside the [_Room class_](/src/main/java/room/Room.java)
has comments that seem uneeded and the code for the two private helper functions
(TODO relative link) looks almost duplicated.

This could be fixed by analysing the comments and removing the uneeded ones and joining
the two helper functions into one, adjusting whatever logic might need to be ajusted.

### Couplers

The [_SkaneController class_](/src/main/java/Controller/SkaneController.java) is an
example of a class that uses the data of another class more that its own. In this
case, the data of the [_Skane_ class](/src/main/java/room/element/skane/Skane.java).

We don't think this code smell represents and actual problem in this case.

## Self-Evaluation

fifty fifty [padner](https://westofloathing.gamepedia.com/Pardner).
