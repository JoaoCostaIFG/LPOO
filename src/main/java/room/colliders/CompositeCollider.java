package room.colliders;

import room.Position;

import java.util.ArrayList;
import java.util.List;

public class CompositeCollider implements Collider {
    private List<Collider> colliders;

    public CompositeCollider() { colliders = new ArrayList<>(); };

    public List<Collider> getColliders() {
        return colliders;
    }

    public void addCollider(Collider col) {
        colliders.add(col);
    }

    public void removeCollider(Collider col) {
        colliders.remove(col);
    }

    @Override
    public boolean collidesWith(Collider col) {
        for (Collider c: this.colliders)
            if (c.collidesWith(col))
                return true;
        return false;
    }
}
