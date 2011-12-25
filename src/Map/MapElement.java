/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

/**
 *
 * @author ishan
 */
public class MapElement {

    private int xLocation;
    private int yLocation;

    public MapElement(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }
}
