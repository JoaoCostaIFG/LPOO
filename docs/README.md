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

```md
### Problem
#### Problem in context
The description of the design context and the concrete problem that motivated
the instantiation of the pattern. Someone else other than the original developer
should be able to read and understand all the motivations for the decisions made.
When refering to the implementation before the pattern was applied, don’t forget
to link to the relevant lines of code in the appropriate version.
#### The pattern
Identify the design pattern to be applied, why it was selected and how it is a
good fit considering the existing design context and the problem at hand.
#### Implementation
Show how the pattern roles, operations and associations were mapped to the concrete
design classes. Illustrate it with a UML class diagram, and refer to the
corresponding source code with links to the relevant lines (these should be
relative links. When doing this, always point to the latest version of the code.
#### Consequences
Benefits and liabilities of the design after the pattern instantiation, eventually
comparing these consequences with those of alternative solutions
```

### Structuring the project

#### Problem in context

We wanted to ensure a good base architecture for the code so the development
could be as smooth and as fast as possible. In the beginning of the development,
we tried to structure everything in a way that made sense and looked organized
at first glance, but that quickly proved to be ineffective. Many parts of the
code were starting to violate the Single-responsibility principle and depend
on each other.

At this time, [the _Skane class_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/16340561419573bc865d04db468446f9d8738aa9/src/main/java/Skane.java#L9-L19)
depended intimately on **lanterna** and the way the objects are draw on screen.
This meant that the _Skane class_ needed to be changed when the Skane changed
and when the way the Skane/game is drawn changes, which violates the
Single-responsibility and the Dependency inversion principles. It also meant
we needed to change all the game elements if we changed the way the game
is drawn.

#### The pattern

To solve these problems, we implemented the MVC (Model-View-Controller)
architectural pattern. Its early adoption during the development made the
implementation easy and “straight forward”.

This pattern allowed us to separate the game objects (model), the rules of
the game (controller) and the user input/gui (view), which is perfect for
the type of project (game) we were developing.

#### Implementation

The following picture illustrates how the pattern's roles were mapped to the
game's classes.

![Movel view controller pattern.](/docs/uml/mvc.png)

These classes can be found in the following files:

- [Drawer](/src/main/java/gui/Drawer.java)
- [Controller](/src/main/java/controller/Controller.java)
- [EnemyController](/src/main/java/controller/EnemyController.java)
- [GameController](/src/main/java/controller/GameController.java)
- [GraphicsDrawer](/src/main/java/gui/GraphicsDrawer.java)
- [Gui](/src/main/java/gui/Gui.java)
- [MovableController](/src/main/java/controller/MovableController.java)
- [PlayerController](/src/main/java/controller/PlayerController.java)
- [Room](/src/main/java/room/Room.java)
- [SkaneController](/src/main/java/controller/SkaneController.java)

#### Consequences

Implementing the **MVC** makes the design denser than it really needed to
be in some instances. For example, the _SkaneController class_ has some
methods that could be part of the _Skane class_ if we didn't want to
separate the game behavior from the model. This also makes code navigation
harder.

These negative consequences were counteracted by how easy it became to
develop different parts of the game at the same time (simultaneous development)
and modify single parts without worrying about the effects on others. This
evident code separation also helped with testing.

### Enemy movement

#### Problem in context

In this game, enemies need to be able to move. The problem was: different enemies
might move in different ways. During the early development of enemy movement,
the movement was equal for every type of enemy (only _Civilians_ at the time)
and was being calculated in the _Room class_. This was problematic because
it made creating new movement strategies and selecting the correct strategies
for each enemy type difficult. It also violated the Single-responsibility
principle.

![Old movement technique code for civilians.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/3fc057d898efcb07976134eb3a43a203a047f502/src/main/java/room/Room.java#L268-L349)

#### The pattern

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

#### Problem in context

Since both members of the group use a tilling window manager, we noticed from
the very start of the project that resizing the terminal window caused problems
with **lanterna** when trying to select an appropriate size for the playing
area.  
It is also worth noting that resizing the terminal windows during _gameplay_,
shouldn't change the size of the playing area, since that would benefit users
with larger screens.

[How the game room size was selected at compile time and never updated after that.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/a74d85d6ec5bedb9570d0330f90c673ca46fd327/src/main/java/Game.java#L28)

//TODO falar das solucoes alternativas pensadas

#### The pattern

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

### Collisions

#### Problem in context

The first implementation of movement checking in the game was [copy pasted from
the Hero project](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/0254be3d927a112238efa112398d0486134ab531/src/main/java/Arena.java#L268-L281).
It consisted in, when an object wanted to move to a position,
verifying if the position was already occupied. When the latter was true, we
handled the collision and moved to position afterwards.

Code-wise however, this had a plethera of code smells.
Firstly, the code associated
with handling the collision was kept in the _Room_ class, part of the **Model**,
thus
violating the **MVC** and the **SRP**.

Secondly, the _Room_ class mantained a
different move method for each _MovableElement_ [(Example with the old Monster Class)](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/0254be3d927a112238efa112398d0486134ab531/src/main/java/Arena.java#L203-L211).
Each method would manage each respective collisions and handled them accordingly.
This causes two issues:

- there are tons of repeated code between each definition of the move method - obvious
code smell;
- If one were to add a new _MovableElement_ to the game, the creation of a method
and adding new handlers to the existing move methods would be required, violating
the OPC.

#### The pattern

In order to fix the mentioned problems, the group decided to use the **strategy pattern**.
This was achieved by using set of _CollisionStrategies_ are used by each controller,
which assigns them to a specific _Element_ type.

#### Implementation

TODO Remove links
We started by creating the [_MovableController_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/94375fc9ee8894725b95c7dc4e1e9cf29a710e09/src/main/java/Controller/MovableController.java#L11-L33)
abstract class. All controllers that move their controlled elements inherit it.
Afterwards, we moved all of the collision related methods to each respective controller,
ie: moving the moveSkane to the [_SkaneController_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/94375fc9ee8894725b95c7dc4e1e9cf29a710e09/src/main/java/Controller/SkaneController.java#L20-L27).
The **strategy pattern** was then implemented by designing the [CollisionStrategy](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/94375fc9ee8894725b95c7dc4e1e9cf29a710e09/src/main/java/Controller/collisionStrategy/CollisionStrategy.java#L1-L9)
class and its subclasses.
Lastly, a map that associates element types with a specific collision strategy was
created in each _MovableController_. This allows for handling collisions dynamically.

TODO Meter uml aqui

#### Consequences

This approach allowed us to more easily create new collision behaviours, and,
when in constract with the original solution, is far less bloated and more
open to extension. It also prevents wrong pairing between a _CollidableElement_ and
a _CollisionStrategy_ using generics, ie: restrict _CollisionStrategy_ to one or
more _CollidableElement_ (s).

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
