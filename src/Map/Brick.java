/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

/**
 *
 * @author ishan
 */
public class Brick extends DestructibleElement {

    public Brick(int x, int y) {
        super(x, y);

        //Starting health of a brick
        health = 0;
    }

    //overrides wrt to the brick health logic
    @Override
    public boolean isDestroyed() {

        return health == 4 ? true : false;

    }
}
