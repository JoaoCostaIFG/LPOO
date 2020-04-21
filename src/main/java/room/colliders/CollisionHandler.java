package room.colliders;

public class CollisionHandler
{
    public static boolean isColliding(RectangleCollider rect1, RectangleCollider rect2) {
        return rect1.collidesWith(rect2);
    }

    public static boolean isColliding(CompositeCollider comp1, RectangleCollider rect2) {
        return comp1.collidesWith(rect2);
    }

    public static boolean isColliding(RectangleCollider rect1, CompositeCollider comp2) {
        return comp2.collidesWith(rect1);
    }
}
