/**
 * Interface InterfaceGW
 */
package main;

/**
 *
 * @author <strong >S.N. Frantsuzov, 2014</strong>
 * @version 1.0
 */
public interface InterfaceGW {

    /**
     * Increment the counter by one point to the player A
     */
    public void setPointsA();

    /**
     * Increment the counter by one point to the player B
     */
    public void setPointsB();

    /**
     * 
     * @return the number of points a player A
     */
    public int getPointsA();

    /**
     * 
     * @return the number of points a player B
     */
    public int getPointsB();

    /**
     * Sets the maximum number of steps for the game
     * @param number
     */
    public void setSteps(int number);

    /**
     * 
     * @return the number of steps
     */
    public int getSteps();

    /**
     * Increment the counter by one step
     */
    public void setCount();

    /**
     * 
     * @return the value of the counter steps
     */
    public int getCount();

    /**
     * Increment the counter early exit by one step
     */
    public void setCountA();

    /**
     *
     * @return the value of the counter early exit
     */
    public int getCountA();

    /**
     * Sets the word
     * @param word
     */
    public void setWord(String word);

    /**
     * Adds the word to the list
     * @param word
     * @return true if the word is not in the list, and false otherwise
     */
    public boolean addWordToList(String word);

    /**
     * 
     * @return the last letter of word
     */
    public char getLastLetter();

    /**
     * Returns the value to step of player. '0' for player A and '1' for player B
     * @return number of the current player
     */
    public int getSwitcher();

    /**
     * Toggle switch to another position
     * If the switch is in position 0, then transferred to 1 and vice versa
     */
    public void switchPlayer();

    /**
     * Resets the list
     */
    public void resetList();

    /**
     * Resets the count of steps
     */
    public void resetCount();

    /**
     * Resets the count of exit
     */
    public void resetCountA();

    /**
     * Resets the coint of points player A
     */
    public void resetPointsA();

    /**
     * Resets the coint of points player B
     */
    public void resetPointsB();

    /**
     * Adds the switch to 0
     */
    public void resetSwitch();
}
