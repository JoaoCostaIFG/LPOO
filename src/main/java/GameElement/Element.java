package GameElement;

public abstract class Element {
    private Position pos, wanted_pos;
    private String me;

    public Element(int x, int y, String me) {
        this.pos = new Position(x, y);
        this.me = me;
    }

    public String getMe() {
        return this.me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public int getX() {
        return this.pos.getX();
    }

    public void setX(int x) {
        this.pos.setX(x);
    }

    public int getY() {
        return this.pos.getY();
    }

    public void setY(int y) {
        this.pos.setY(y);
    }

    public Position getPos() {
        return this.pos;
    }

    public void setPos(Position position) {
        this.pos = position;
    }
}
