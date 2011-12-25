/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

/**
 *
 * @author ishan
 */
public class DestructibleElement extends MapElement {

    protected int health;

    public DestructibleElement(int x, int y) {
        super(x, y);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * checks whether the element should be deleted
     * @return
     */
    public boolean isDestroyed() {
        return health > 0 ? false : true;
    }
}
