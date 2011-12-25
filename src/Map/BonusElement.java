/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

/**
 *
 * @author ishan
 */
public class BonusElement extends MapElement {

    //time that the element should disaapear at
    private long endTime;
    private int amount;
    private boolean available;
    private boolean taken;

    public BonusElement(int lifeTime, int amount, int x, int y) {
        super(x, y);
        this.endTime = lifeTime + System.currentTimeMillis();
        this.amount = amount;
        taken = false;


    }

    /**
     * returns the value of the item
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * returns the time left for the element to live
     * @return
     */
    public long timeLeft() {
        return endTime - System.currentTimeMillis();

    }

    /**
     * check whether the element is still living
     */
    public boolean isAvailable() {

        if (timeLeft() > 0 && !taken) {
            available = true;
        } else {
            available = false;
        }

        return available;

    }

    /**
     * Mark as unavailable .
     *
     * Useful when a player gets the element.
     */
    public void markAsUnavailable() {
        taken = true;

    }
}
