# Features

- the player controls a giant snake called Skane;
- the snake can bury itself underground to move unnoticed and safe from enemy fire;
- there are civilian running from you and soldiers trying to kill you using
  their guns, cars and helicopters;
- the snake needs air the breath so it needs to come to the surface from time
  to time;
- the more you eat, the more health you have and the bigger you become;
- enemy damage makes lose health and, consequently, shrink;
- if you shrink too much, you die;
- some stronger enemies (e.g.: tanks) can yield some extra rewards, like movement
  speed upgrades, upon death;

## Planned Features

## Implemented Features

## Design

### Terminal Resizing

TODO- add lines bellow https://help.github.com/en/github/managing-your-work-on-github/creating-a-permanent-link-to-a-code-snippet

Since both members of the group use a tilling window manager, we
noticed from the very start of the project that resizing the terminal
window caused problems with lanterna.

In order to fix this we used the observer pattern. When the terminal
window resizes, a resize handler is notified and the room's size is
redefined.

The following classes were made/adapted/used to implement this pattern:

- _TerminalResizeListener_ - An interface from lanterna that specifies a
  listener's functionalities.
- _TerminalResizeHandler_ - Our implementation of the previous interface.
- _Terminal_ - Lanterna's class. It takes the role of the observable and
  notifies _TerminalResizeHandler_ each time it resizes.
- Gui - Instantiates both _Terminal_ and _TerminalResizeHandler_. Adds
  _TerminalResizeHandler_ as a resize listener to _Terminal_. Changes
  the room for each time _TerminalResizeHandler_ has resized.

The presented design removes the need for the Gui Class to manage the
terminal directly, thus avoiding the SRP. TODO add siglas no inicio
It also avoids the constant polling of the terminal's size.
There were no other alternative solutions that were proposed.

### Pathfinding for Enemies

Observer pattern here.

### Enemy Movement

Strategy pattern here.

## Known Code Smells and Refactoring Suggestions

## Self-Evaluation
