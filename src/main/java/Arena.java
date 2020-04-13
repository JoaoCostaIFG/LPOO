import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    /* Game States
     * 0 - close game
     * 1 - normal running state
     * 2 - player died/lost
     * 3 - player won
     * 4 - restart game
     */
    private int game_state = 1;
    private int height;
    private int width;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int height, int width) {
        this.height = height;
        this.width = width;

        Random random = new Random();
        this.hero = new Hero(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);

        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public int getGameState() {
        return this.game_state;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; ++c) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; ++r) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        Coin c;
        boolean to_add;

        for (int i = 0; i < 5; ++i) {
            to_add = true;
            c = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (c.getPos().equals(hero.getPos())) {
                to_add = false;
            } else {
                for (Coin coin : coins) {
                    // collision
                    if (c.getPos().equals(coin.getPos())) {
                        to_add = false;
                        break;
                    }
                }
            }

            if (to_add) {
                coins.add(c);
            } else {
                --i;
                c = null;
            }
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        Monster m;
        boolean to_add;

        for (int i = 0; i < 5; ++i) {
            to_add = true;
            m = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (m.getPos().equals(hero.getPos())) {
                to_add = false;
            } else {
                for (Monster monster : monsters) {
                    // collision
                    if (m.getPos().equals(monster.getPos())) {
                        to_add = false;
                        break;
                    }
                }
            }

            if (to_add) {
                monsters.add(m);
            } else {
                --i;
                m = null;
            }
        }
        return monsters;
    }

    public void draw(TextGraphics gra) {
        // background
        gra.setBackgroundColor(TextColor.Factory.fromString("#313742"));
        gra.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        // walls
        for (Wall wall : walls)
            wall.draw(gra);

        // coins
        for (Coin coin : coins)
            coin.draw(gra);

        // monsters
        for (Monster monster : monsters)
            monster.draw(gra);

        // hero
        hero.draw(gra);
        gra.putString(new TerminalPosition(width + 1, 1), "HP: " + Integer.toString(hero.getHp()));
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    moveHero(hero.moveLeft());
                    break;
                case 'd':
                case 'D':
                    moveHero(hero.moveRight());
                    break;
                case 'w':
                case 'W':
                    moveHero(hero.moveUp());
                    break;
                case 's':
                case 'S':
                    moveHero(hero.moveDown());
                    break;
                case 'r':
                case 'R':
                    this.game_state = 4;
                    break;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case Escape:
            case EOF:
                this.game_state = 0;
                break;
            default:
                break;
        }
    }

    private void retrieveCoins(Position position) {
        for (int i = 0; i < coins.size(); ++i) {
            if (coins.get(i).getPos().equals(position)) {
                coins.remove(i);
                break;
            }
        }
    }

    private void moveMonsters() {
        Position pos;
        for (Monster monster : monsters) {
            pos = monster.move();
            if (canMonsterMove(pos)) {
                monster.setPos(pos);
            }
        }
    }

    private void verifyMonsterCollisions(Position position) {
        for (Monster monster : monsters) {
            if (position.equals(monster.getPos()) && monster.canMonsterAttack()) {
                hero.setHp(hero.getHp() - 1);   // hero takes damage
                monster.monsterAttack();        // monster will stay immobile for a few turns
            }
        }
    }

    private boolean canMonsterMove(Position position) {
        // wall collision
        for (Wall wall : walls) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // other monster collision
        for (Monster monster : monsters) {
            if (position.equals(monster.getPos()))
                return false;
        }

        // stay inside arena
        return (position.getX() > 0 && position.getX() < (width - 1) &&
                position.getY() > 0 && position.getY() < (height - 1));
    }

    private boolean canHeroMove(Position position) {
        // check if alive
        if (!hero.isAlive())
            return false;

        // wall collision
        for (Wall wall : walls) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // stay inside arena
        return (position.getX() > 0 && position.getX() < (width - 1) &&
                position.getY() > 0 && position.getY() < (height - 1));
    }

    private void checkGame() {
        if (coins.size() == 0)  // win state
            this.game_state = 3;

        if (hero.isAlive())     // nothing happened
            return;
        else {                  // lose state
            hero.setMe("");
            this.game_state = 2;
        }
    }

    private void moveHero(Position position) {
        //if (canHeroMove(position)) {
            hero.setPos(position);
            retrieveCoins(position);

            // only move monsters + check collisions if hero moves
            verifyMonsterCollisions(hero.getPos());
            moveMonsters();
            verifyMonsterCollisions(hero.getPos());

            // check if hero is still alive and picked up coins
            checkGame();
        //}
    }
}
