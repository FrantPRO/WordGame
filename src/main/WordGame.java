/*
 * Class WordGame
 */
package main;

import java.util.HashSet;

/**
 *
 * @author <strong >S.N. Frantsuzov, 2014</strong>
 * @version 1.0
 */
public class WordGame implements InterfaceGW {

    /**
     * List of input words
     */
    private HashSet list;
    /**
     * The current word
     */
    private String word;
    /**
     * The counter moves
     */
    private int count;
    /**
     * The counter early retirement
     */
    private int countA;
    /**
     * The switch players
     */
    private int switcher;
    /**
     * Specified number of moves
     */
    private int steps;
    /**
     * The counter points Player 1
     */
    private int pointsA;
    /**
     * The counter points Player 2
     */
    private int pointsB;
    /**
     * THe word without the last letter
     */
    private String cutWord;

    /**
     *
     */
    public WordGame() {
        this.list = new HashSet();
    }

    /**
     *
     * @param word {@link String}
     */
    @Override
    public void setWord(String word) {
        this.word = word.trim();
    }

    /**
     *
     */
    @Override
    public void resetList() {
        this.list = new HashSet();
    }

    /**
     *
     * @param word {@link String}
     * @return {@link boolean}
     */
    @Override
    public boolean addWordToList(String word) {
        word = word.trim().toLowerCase();
        if (list.isEmpty() || list == null) {
            return list.add(word);
        }
        if (!checkWordInList(word)) {
            return list.add(word);
        }
        return false;
    }

    /**
     *
     */
    @Override
    public void setCountA() {
        this.countA++;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getCountA() {
        return this.countA;
    }

    /**
     *
     */
    @Override
    public void resetCountA() {
        this.countA = 0;
    }

    /**
     *
     * @param steps {@link int}
     */
    @Override
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getSteps() {
        return this.steps;
    }

    /**
     *
     */
    @Override
    public void setCount() {
        this.count++;
    }

    /**
     *
     */
    @Override
    public void resetCount() {
        this.count = 0;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getCount() {
        return this.count;
    }

    /**
     * 
     * @param word {@link String}
     * @return {@link boolean}
     */
    private boolean checkWordInList(String word) {
        return list.contains(word);
    }

    /**
     * Method returns is correct last letter. If necessary, returns the penultimate letter.
     * @return {@link char} correct last letter
     */
    @Override
    public char getLastLetter() {
        char lastLetter = this.word.trim().toLowerCase().charAt(word.length() - 1);
        this.cutWord = this.word.trim().toLowerCase().substring(0, word.length() - 1);
        while (!checkLastLetterToCorrect(lastLetter)) {
            lastLetter = getLastLetter(getCutWord());
        }
        return lastLetter;
    }

    /**
     * 
     * @param cutWord {@link String}
     * @return {@link char}
     */
    private char getLastLetter(String cutWord) {
        this.cutWord = cutWord.trim().toLowerCase().substring(0, cutWord.length() - 1);
        return cutWord.trim().toLowerCase().charAt(cutWord.length() - 1);
    }

    /**
     * Check the last letter of the presence of the letters 'ь', 'ъ' и 'ы'
     * @param letter {@link char} - the last letter
     * @return {@link boolean} true if the last letter is not 'ь', 'ъ' и 'ы'
     */
    private boolean checkLastLetterToCorrect(char letter) {
        return !(letter == 'ь' || letter == 'ъ' || letter == 'ы');
    }

    /**
     * 
     * @return {@link String}
     */
    private String getCutWord() {
        return this.cutWord;
    }

    /**
     *
     */
    @Override
    public void setPointsA() {
        this.pointsA++;
    }

    /**
     *
     */
    @Override
    public void resetPointsA() {
        this.pointsA = 0;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getPointsA() {
        return this.pointsA;
    }

    /**
     *
     */
    @Override
    public void setPointsB() {
        this.pointsB++;
    }

    /**
     *
     */
    @Override
    public void resetPointsB() {
        this.pointsB = 0;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getPointsB() {
        return this.pointsB;
    }

    /**
     *
     */
    @Override
    public void switchPlayer() {
        if (getSwitcher() == 1) {
            this.switcher = 0;
        } else {
            this.switcher = 1;
        }
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getSwitcher() {
        return this.switcher;
    }

    /**
     *
     */
    @Override
    public void resetSwitch() {
        this.switcher = 0;
    }
}
