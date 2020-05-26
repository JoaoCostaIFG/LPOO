# LPOO_G73 - Skane Dweller

In this game, the player controls a giant snake known as **Skane**. The player's
objective is, putting it simply, to wreak havoc. Be careful with armed enemies,
though. They can easily ruin your day.

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
- **Stronger military** - there are soldiers wielding ranged weaponry.
- **Damaging/Killing enemies** - all enemies can be killed;
- **Eating** - the more (dead) people you eat, the more health you have and the
  bigger you become.
- **Suffering damage** - enemies can damage the **Skane** with their weapons,
  making it shrink.
- **Fog of war** - when the **Skane** is buried, it's ability to see is limited.
  The **Skane** needs to get closer to enemies of walls to sense them.
- **Death** - if a **Skane** is too small to withstand incoming damage, it dies.
- **Enemy spawning** - enemies spawn over time during the game.
- **Map importing** - it's easy to create and import your own custom maps.

### Abandoned ideas

- **Corpse spawning** - dead enemies should leave their corpse on the ground.
- **Enemy alertness state** - indicators for whether and enemy is aware of
  your presence.
- **Perk/upgrade like rewards** - killing stronger enemies (e.g.: tanks) should
  yield special extra rewards, like movement speed upgrades.
- **Jumping** - the **Skane** will not be able to jump to attack helicopters or
  get over small walls after unburying itself.

## Design

```
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

To solve these problems, we implemented the
[**MVC**](https://en.wikipedia.org/w/index.php?title=Model%E2%80%93view%E2%80%93controller&oldid=952768599)
(Model-View-Controller) architectural pattern. Its early adoption during
the development made the implementation easy and “straight forward”.

This pattern allowed us to separate the game objects (**model**), the rules of
the game (**controller**) and the user input/gui (**view**), which is perfect for
the type of project (game) we were developing.

#### Implementation

The following picture illustrates how the pattern's roles were mapped to the
game's classes.

![Movel view controller pattern.](/docs/uml/mvc.png)

The **model component classes** can be found in the following files:

**Note**: Many classes/interfaces from our implementation have been omitted from
this UML class diagram so it wouldn't be too bloated. Most of them will be
discussed in more detail in other sections of this chapter.

The shown **controller component classes** can be found in the following files:

- [CollisionHandler](/src/main/java/org/g73/skanedweller/controller/CollisionHandler.java)
- [Controller](/src/main/java/org/g73/skanedweller/controller/Controller.java)
- [EnemyController](/src/main/java/org/g73/skanedweller/controller/EnemyController.java)
- [GameController](/src/main/java/org/g73/skanedweller/controller/GameController.java)
- [MovableController](/src/main/java/org/g73/skanedweller/controller/MovableController.java)
- [PlayerController](/src/main/java/org/g73/skanedweller/controller/PlayerController.java)
- [SkaneController](/src/main/java/org/g73/skanedweller/controller/SkaneController.java)

The shown **model component classes** can be found in the following files:

- [Room](/src/main/java/org/g73/skanedweller/model/Room.java)
- [RayCasting](/src/main/java/org/g73/skanedweller/model/RayCasting.java)
- [RayCast](/src/main/java/org/g73/skanedweller/model/RayCast.java)

The shown **view component classes** can be found in the following files:

- [Drawer](/src/main/java/org/g73/skanedweller/view/Drawer.java)
- [Gui](/src/main/java/org/g73/skanedweller/view/Gui.java)
- [RoomDrawer](/src/main/java/org/g73/skanedweller/view/RoomDrawer.java)

- [InputHandler](/src/main/java/org/g73/skanedweller/view/InputHandler.java)
- The _TerminalResizeListener class_ is part of Google's Lanterna package.
- [TerminalResizeHandler](/src/main/java/org/g73/skanedweller/view/TerminalResizeHandler.java)

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

### Structuring the game element inheritance hierarchy

#### Problem in context

Our game was starting to get many game elements with different abilities/properties.
With this, we were having problems structuring their inheritance hierarchy in a way
that prevented code duplication and was easy to work with.  
We were reaching a point where we had to have an abstract class for each combination
of game element interfaces, which was not feasible.

As an example of this problem, we can see some code duplication showing up between
the [_Wall class_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/7d5e43e87dd228bae488cf092935c630ee51923b/src/main/java/room/element/Wall.java#L21-L40)
and the [_Entity abstract class_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/7d5e43e87dd228bae488cf092935c630ee51923b/src/main/java/room/element/Entity.java#L71-L90).

Another problem (related to the previous one) that was coming up, was that
it was becoming increasingly more difficult to choose the correct abstraction
to be passed to some methods. For example, the _Wall class_ had a **collider**,
but it didn't extend the _Entity class_ like all the other game elements
that had **colliders** at the time.

#### The pattern

To help solve these problems, we made use of the
[**Null Object pattern**](https://en.wikipedia.org/w/index.php?title=Null_object_pattern&oldid=940667694)
in conjunction with the strategy of **composition over inheritance**.  
We implemented this design pattern because we needed a way for objects to
declare that they didn't have some functionality when those functions were
called.

#### Implementation

The following picture illustrates how the game elements' hierarchy was
restructured and how the pattern's roles were mapped to the game's classes.

![Restructuring of game element hierarchy and null object pattern](/docs/uml/element.png)

**Note**: the _Element class_ implements all the interfaces in the diagram,
namely _Agressive_, _Collidable_, _Mortal_ and _Movable_, in order to make it
easier to delegate work to the **behaviour classes**. This has been omitted
from the UML class diagram above for clarity's sake.

The **Null objects** can be found in the following files:

- [ImmortalBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/ImmortalBehaviour.java)
- [ImovableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/ImovableBehaviour.java)
- [NotCollidableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/NotCollidableBehaviour.java)
- [PassiveBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/PassiveBehaviour.java)

The interfaces and other behaviors can be found in the following files:

- [Agressive](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/Agressive.java)
- [AgressiveBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/AgressiveBehaviour.java)
- [Collidable](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/Collidable.java)
- [CollidableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/CollidableBehaviour.java)
- [Mortal](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/Mortal.java)
- [MortalBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/MortalBehaviour.java)
- [Movable](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/Movable.java)
- [MovableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/MovableBehaviour.java)

The game elements can be found in the following files:

- [Element](/src/main/java/org/g73/skanedweller/model/element/Element.java) - As
mentioned above, all game objects extend this Abstract class.

- [Civilian](/src/main/java/org/g73/skanedweller/model/element/Civilian.java)
- [Laser](/src/main/java/org/g73/skanedweller/model/element/Laser.java)
- [MeleeGuy](/src/main/java/org/g73/skanedweller/model/element/MeleeGuy.java)
- [RangedGuy](/src/main/java/org/g73/skanedweller/model/element/RangedGuy.java)
- [Scent](/src/main/java/org/g73/skanedweller/model/element/skane/Scent.java)
- [Skane](/src/main/java/org/g73/skanedweller/model/element/skane/Skane.java)
- [SkaneBody](/src/main/java/org/g73/skanedweller/model/element/skane/SkaneBody.java)
- [Wall](/src/main/java/org/g73/skanedweller/model/element/Wall.java)

#### Consequences

With this reorganization of the code and the use of the **Null object** pattern,
we managed to streamline the creation/implementation of new game objects with
different abilities/combination of abilities. I also provided us with the ability
to stop worrying about the specific abilities of each object when working with
the game objects in bulk.

The only downside we could see here was having to create a few more classes, but
in the end, the result was simpler than any of the alternatives we could think of,
namely creating classes for each combination of the abilities.

### Enemy movement

#### Problem in context

In this game, enemies need to be able to move. The problem was: different enemies
might move in different ways. During the early development of enemy movement,
the movement was equal for every type of enemy (only _Civilians_ at the time)
and was being calculated in the _Room class_. This was problematic because
it made creating new movement strategies and selecting the correct strategies
for each enemy type difficult. It also violated the Single-responsibility
principle.

[Old movement technique code for civilians.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/3fc057d898efcb07976134eb3a43a203a047f502/src/main/java/room/Room.java#L268-L349)

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
**Note:** Only game elements that are 'movable', as in, instanciated with a
_MovableBehaviour class_ are shown in the following UML class diagram.

![Movement strategy pattern](/docs/uml/movement_strategy.png)

These classes can be found in the following files:

- [ChaseStrat](/src/main/java/org/g73/skanedweller/controller/movement_strategy/ChaseStrat.java)
- [Civilian](/src/main/java/org/g73/skanedweller/model/element/Civilian.java)
- [Element](/src/main/java/org/g73/skanedweller/model/element/Element.java)
- [ImovableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/ImovableBehaviour.java)
- [MeleeGuy](/src/main/java/org/g73/skanedweller/model/element/MeleeGuy.java)
- [MeleeMoveStrat](/src/main/java/org/g73/skanedweller/controller/movement_strategy/MeleeMoveStrat.java)
- [Movable](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/Movable.java)
- [MovableBehaviour](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/MovableBehaviour.java)
- [MoveStrategy](/src/main/java/org/g73/skanedweller/model/element/element_behaviours/MoveStrategy.java)
- [RangedGuy](/src/main/java/org/g73/skanedweller/model/element/RangedGuy.java)
- [RangedMoveStrat](/src/main/java/org/g73/skanedweller/controller/movement_strategy/RangedMoveStrat.java)
- [ScaredMoveStrat](/src/main/java/org/g73/skanedweller/controller/movement_strategy/ScaredMoveStrat.java)

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

[How the game model size was selected at compile time and never updated after that.](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/a74d85d6ec5bedb9570d0330f90c673ca46fd327/src/main/java/Game.java#L28)

#### The pattern

In order to fix this we used the
[**observer pattern**](https://refactoring.guru/design-patterns/observer).
When the terminal window resizes, a resize handler is notified. After being
notified, it updates its internal state to reflect the new terminal size.

#### Implementation

The following classes were adapted/developed/used to implement this pattern:

![Terminal resize handler](/docs/uml/terminal_size.png)

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

Be careful if we want to resize the screen/update the model's info.

### Collisions

#### Problem in context

The first implementation of movement checking in the game was
[very simple and unexpadable](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/0254be3d927a112238efa112398d0486134ab531/src/main/java/Arena.java#L268-L281).
It queried all the game elements positions before moving and blocked the move
if there was an object with the same target position _in-game_. When this
move was blocked, there could be some collision handling invoked.

Code-wise however, this had a plethora of code smells. Firstly, the code associated
with handling the collision was kept in the _Room_ class, part of the **Model**,
thus violating the **MVC** and the **SRP**. Secondly, the _Room_ class maintained
a different move method for each _MovableElement_.

[Example with the old Monster Class](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/0254be3d927a112238efa112398d0486134ab531/src/main/java/Arena.java#L203-L211).

Each method would manage their respective collisions and handle them accordingly.
This caused two issues:

- there was repeated code between each definition of the move method, which is
  a code smell;
- If one were to add a new _MovableElement_ to the game, the creation of a move
  method and adding new collision handlers to the existing move methods would
  be required, violating the **open-closed principle**.

#### The pattern

In order to fix the problems mentioned above, the group decided to use the
[**strategy pattern**](https://refactoring.guru/design-patterns/strategy).
This was achieved by setting the _CollisionStrategies_ that each controller
uses when it's controlled game element collides with another game element
(based on element types).

#### Implementation

We started by creating the
[_MovableController_](/src/main/java/org/g73/skanedweller/controller/MovableController.java)
abstract class. All controllers that move their controlled elements inherit it.
Afterwards, we moved all the collision related methods to each respective
controller, i.e.: moving the `moveSkane()` method to the
[_SkaneController_](/src/main/java/org/g73/skanedweller/controller/SkaneController.java).
The **strategy pattern** was then implemented by designing the
[_CollisionStrategy_](/src/main/java/org/g73/skanedweller/controller/collision_strategy/CollisionStrategy.java)
abstarct class and its implementations. Lastly, a map that associates each element
types with a specific collision strategy was created for all _MovableController_.
This allows for handling collisions dynamically and each controller can define
what happens when it's controlled game element collides with another game element
based on element types.

![Collision strategy UML](/docs/uml/collision_strategy.png)

The classes on the UML diagram can be found on the following files:

- [Controller](/src/main/java/org/g73/skanedweller/controller/Controller.java)
- [PlayerController](/src/main/java/org/g73/skanedweller/controller/PlayerController.java)
- [SkaneController](/src/main/java/org/g73/skanedweller/controller/SkaneController.java)
- [MovableController](/src/main/java/org/g73/skanedweller/controller/MovableController.java)
- [EnemyController](/src/main/java/org/g73/skanedweller/controller/EnemyController.java)
- [CollisionHandler](/src/main/java/org/g73/skanedweller/controller/CollisionHandler.java)
- [CollisionStrategy](/src/main/java/org/g73/skanedweller/controller/collision_strategy/CollisionStrategy.java)
- [BlockCollision](/src/main/java/org/g73/skanedweller/controller/collision_strategy/BlockCollision.java)
- [NullCollision](/src/main/java/org/g73/skanedweller/controller/collision_strategy/NullCollision.java)
- [SkaneAttackCollision](/src/main/java/org/g73/skanedweller/controller/collision_strategy/SkaneAttackCollision.java)

#### Consequences

This approach allowed us to more easily create new collision behaviours, and,
when in constract with the original solution, is far less bloated and more
open to extension. It also prevents wrong pairing between a _CollidableElement_ and
a _CollisionStrategy_ using generics, ie: restrict _CollisionStrategy_ to one or
more _CollidableElement(s)_.

### Colliders - Composite

#### Problem in context

When designing the game's mechanics we came up with the idea of a multitude of
enemy types, each with their specific characteristic (like helicopters flying, tanks
with lasers, enemies with path-finding, etc...). We soon came to the realization
that checking for collisions between these objects would prove very difficult
if we continued to
[use the _Position_ class to do so](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/43f14c4bdf223b45f7bd5b52415378e98c1f6f5d/src/main/java/room/Room.java#L67-L90).
If we kept going with this approach, a switch statement would be required to
decide the type of verification method to be used when deciding if two objects had
collided. It would end up **bloating** the _Room_ class, making it more difficult
to work with.

#### The pattern

To fix this issue the group decided to create a new _Collider_ class and aggregate
it into the _Elemets_ that implemented the _CollidableElement_ interface. For more
overall flexibility, the
[**composite pattern**](https://refactoring.guru/design-patterns/composite) was
used to allow for more complex _colliders_.

#### Implementation

This solution allows the model to easily check if two objects collide with each
other, as defined in the `getCollidingElems()` method of the
[_Room class_](/src/main/java/org/g73/skanedweller/model/Room.java).

![Composite collider UML class diagram](/docs/uml/colliders_composite.png)

The classes in the UML class diagram above can be found in following files:

- [Collider](/src/main/java/org/g73/skanedweller/model/colliders/Collider.java)
- [CompositeCollider](/src/main/java/org/g73/skanedweller/model/colliders/CompositeCollider.java)
- [RectangleCollider](/src/main/java/org/g73/skanedweller/model/colliders/RectangleCollider.java)

#### Consequences

The composite principle helps us solve the problem of having complex **colliders**
in an object in a robust and elegant way. When compared to its alternative,
it's much simpler and leads to many less code smells.

### Colliders - Observer

#### Problem in context

After implementing the collider classes we quickly came into a problem: when an element
had moved, its [_collider_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/cd2a188f81a710bcbf8c143b8fa93ea991ed82b3/src/main/java/room/colliders/Collider.java#L1-L5)
wouldn't be updated with the new position.

#### The pattern

We decided that the __observer pattern__ was the best solution for this problem.

#### Implementation

The following changes were made to make use of the pattern:

- [_Element_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/90cb4f0319680d38016ad088188d4d1f24277cd7/src/main/java/org/g73/skanedweller/model/element/Element.java#L100-L113) -
  Now implements the [_Observable interface_](/src/main/java/org/g73/skanedweller/observe/Observable.java).
  Every time an element moves, it notifies its collider(s):

- [_Collider_](https://github.com/FEUP-LPOO/lpoo-2020-g73/blob/90cb4f0319680d38016ad088188d4d1f24277cd7/src/main/java/org/g73/skanedweller/model/colliders/Collider.java#L20-L23) -
Now implements the [_Observer interface_](/src/main/java/org/g73/skanedweller/observe/Observer.java).
  When a collider is notified that it has changed to a position, it updates its position accordingly.

# TODO UML HERE
  
#### Consequences

This approach works well for all of the possible changes/additions to the game's
colliders that we could think of.
We couldn't think of other alternative solution to the problem.

## Known Code Smells and Refactoring Suggestions

### Bloaters

The [_Room class_](/src/main/java/org/g73/skanedweller/model/Room.java) is a Bloater
(_Large class_). This is problematic because finding specific code segments
to work on inside the class can prove cumbersome and the class has a
`tendecy' to violate the Single-responsibility principle.

We could improve the code by dividing the Room class into smaller, more specific,
classes: _Extract class_ refactor.

There's also the ray-casting helper (private) functions that take 6 arguments,
which are bloaters (_Long parameter list_), but we believe all alternatives are
inferior design-wise.

### Dispensables

The ray-casting related code inside the two private helper functions
`octant03Ray()` and `octant12Ray()`
on the [_Room class_](/src/main/java/org/g73/skanedweller/model/Room.java)
looks almost duplicated (_Duplicate code_).

This could be fixed by analysing the code to find ways to join these
similarities, but we haven't been able to find a way to that.

We have code for composite colliders is not in use at the moment. It was created
because we believed it would be useful for use with the **Skane**, but it ended
up not being needed (_Speculative generality_).

We could fix this problem by “getting rid of” the
[_CompositeCollider_ class](/src/main/java/org/g73/skanedweller/model/colliders/CompositeCollider.java).

### Couplers

The [_SkaneController class_](/src/main/java/org/g73/skanedweller/controller/SkaneController.java)
is an example of a class that uses the data of another class more that its own. In
this case, the data of the
[_Skane_ class](/src/main/java/org/g73/skanedweller/model/element/skane/Skane.java).

We don't think this code smell represents an actual problem in this case.

### Change Preventers

The [_Element hierarchy_](/src/main/java/org/g73/skanedweller/model/element) and
the [_View hierarchy_](/src/main/java/org/g73/skanedweller/view/element_views)
repesent a situation of **Parallel Inheritance Hierarchies**. If we wanted to
add a new element to the game, we would be obliged to create a new _Model_ class
and a new _View_ class for it.

The only way to fix this code smell would imply moving parts of the **View**
into the **Model** (or vice-versa). This would be a violation of the **MVC**
architetural pattern.

## Self-Evaluation

We believe both members of the group were integral for the development of this
project and worked the same amount. With this, we believe self-evaluate with
50% of the final grade, each.

Or, in other words: Fifty-fifty [padner](https://westofloathing.gamepedia.com/Pardner).
