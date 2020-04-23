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
- **Oxygen level indicator** - there should be a way to indicate the oxygen level
  of the **Skane** (so the player knows if it can go underground).
- **Stronger military** - the soldiers should be able to use guns, cars, and maybe
  even helicopters.
- **Perk/upgrade like rewards** - killing stronger enemies (e.g.: tanks) should
  yield special extra rewards, like movement speed upgrades.

### Abandoned ideas

- **Jumping** - the **Skane** will not be able to jump to attack helicopters or
  get over small walls after unburying itself.

## Design

### Terminal size

#### Problem in Context

Since both members of the group use a tilling window manager, we noticed from
the very start of the project that resizing the terminal window caused problems
with **lanterna** when trying to select an appropriate size for the playing
area.  
It is also worth noting that resizing the terminal windows during _gameplay_,
shouldn't change the size of the playing area, since that would benefit users
with larger screens.

https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/a74d85d6ec5bedb9570d0330f90c673ca46fd327/src/main/java/Game.java#L28

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

The [_Room class_](src/main/java/room/Room.java) is Bloater (_Large class_). This
is problematic because finding specific code segments to work on inside the class
can prove cumbersome and the class as a `tendecy' to violate the
Single-responsibility principle.

We could improve the code by dividing the Room class into smaller, more specific,
classes.

### Dispensables

The ray-casting related code inside the [_Room class_](src/main/java/room/Room.java)
has comments that seem uneeded and the code for the two private helper functions
(TODO relative link) looks almost duplicated.

This could be fixed by analysing the comments and removing the uneeded ones and joining
the two helper functions into one, adjusting whatever logic might need to be ajusted.

### Couplers

The [_SkaneController class_](src/main/java/Controller/SkaneController.java) is an
example of a class that uses the data of another class more that its own. In this
case, the data of the [_Skane_ class](src/main/java/room/element/skane/Skane.java).

We don't think this code smell represents and actual problem in this case.

## Self-Evaluation

fifty fifty [padner](https://westofloathing.gamepedia.com/Pardner).
