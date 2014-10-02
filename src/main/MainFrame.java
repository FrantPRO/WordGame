/*
 * Class MainFrame
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author <strong >S.N. Frantsuzov, 2014</strong>
 * @version 1.0
 */
public class MainFrame extends JFrame implements InterfaceGW {

    private final JLabel msgTop;
    private final JLabel msgScore;
    private final JLabel msgMiddle;
    private final JLabel msgBotton;
    private final JLabel msgSteps;
    private final JTextField textField;
    private final JButton btnRun;
    private final JButton btnGiveUp;
    private final WordGame main;
    private int status;
    private char lastLetter;
    private String word;

    /**
     *
     */
    public MainFrame() {
        super("Word game");
        setSize(510, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        Listener listener = new Listener(this);

        main = new WordGame();

        msgTop = new JLabel("Новая игра");
        msgTop.setBounds(10, 10, 380, 25);
        add(msgTop);

        msgScore = new JLabel("Счет 0 : 0");
        msgScore.setBounds(400, 10, 90, 25);
        add(msgScore);

        msgMiddle = new JLabel("Введите количество ходов");
        msgMiddle.setBounds(10, 45, 220, 25);
        add(msgMiddle);

        msgBotton = new JLabel();
        msgBotton.setForeground(Color.red);
        msgBotton.setFont(new Font(null, Font.ITALIC, 16));
        msgBotton.setBounds(10, 120, 485, 25);
        add(msgBotton);

        msgSteps = new JLabel();
        msgSteps.setBounds(358, 30, 130, 25);
        add(msgSteps);

        textField = new JTextField();
        textField.setBounds(10, 80, 260, 25);
        textField.addKeyListener(listener);
        add(textField);

        btnRun = new JButton("Ок");
        btnRun.setBounds(280, 80, 120, 25);
        btnRun.addActionListener(listener);
        btnRun.setActionCommand("0");
        add(btnRun);

        btnGiveUp = new JButton("Отмена");
        btnGiveUp.setBounds(410, 80, 85, 25);
        btnGiveUp.addActionListener(listener);
        btnGiveUp.setActionCommand("1");
        add(btnGiveUp);

        setVisible(true);
    }

    /**
     * Method for application start
     *
     * @param args
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }

    /**
     *
     */
    private void setMsgScore() {
        this.msgScore.setText("Счет " + getPointsA() + " : " + getPointsB());
    }

    /**
     *
     * @param count
     */
    private void setMsgCountDown(int count) {
        msgSteps.setText("Осталось ходов: " + (getSteps() - count));
    }

    /**
     *
     */
    private void resetMsgCountDown() {
        msgSteps.setText("");
    }

    /**
     *
     * @return current word
     */
    private String getWord() {
        return textField.getText();
    }

    /**
     * Set message on top frame
     * @param text
     */
    private void setMsgTop(String text) {
        this.msgTop.setText(text);
    }

    /**
     *
     * @param text
     */
    private void setMsgMiddle(String text) {
        this.msgMiddle.setText(text);
    }

    /**
     *
     * @param text
     */
    private void setMsgBotton(String text) {
        this.msgBotton.setText(text);
    }

    /**
     *
     */
    private void resetTextField() {
        this.textField.setText("");
    }

    /**
     *
     */
    private void enterSteps() {
        setMsgTop("Новая игра");
        setMsgMiddle("Введите четное количество ходов");
        setMsgBotton("");
        btnRun.setText("Ок");
        btnGiveUp.setText("Отмена");
        try {
            int steps = Integer.parseInt(word);
            if (steps < 0 || (steps % 2) != 0 || steps == 0) {
                setMsgBotton("Число не четное! Введите четное число больше нуля!");
                resetTextField();
                textField.grabFocus();
                return;
            }
            setSteps(steps);
            setMsgCountDown(getCount());
            setMsgBotton("Установлено количество ходов " + steps);
            resetTextField();
            setMsgMiddle("Введите слово");
            btnRun.setText("Ход игрока 1");
            btnGiveUp.setText("Сдаюсь");
            status = 1;
        } catch (NumberFormatException a) {
            setMsgBotton("Вы ввели не цифру!");
            resetTextField();
        }
    }

    /**
     *
     */
    private void play() {

        if (wordCorrect()) {
            setMsgBotton("Слово должно состоять только из букв и не менее 2-х букв");
            return;
        }
        if (getCount() != 0) {
            if (lastLetter != word.toLowerCase().charAt(0)) {
                setMsgBotton("Ошибка! Введите слово на букву: " + Character.toUpperCase(lastLetter));
                resetTextField();
                return;
            }
        }
        if (addWordToList(word)) {
            setWord(word);
            lastLetter = getLastLetter();
            setMsgTop("Противник ввел слово: " + word.toUpperCase());
            setMsgMiddle("Введите слово на букву: " + Character.toUpperCase(lastLetter));
            setMsgBotton("Слово принято");
            setCount();
            setMsgCountDown(getCount());
            resetCountA();
            if (getSwitcher() == 0) {
                setPointsA();
                setMsgScore();
                switchPlayer();
                btnRun.setText("Ход игрока 2");
            } else {
                setPointsB();
                setMsgScore();
                switchPlayer();
                btnRun.setText("Ход игрока 1");
            }

            resetTextField();
        } else {
            setMsgBotton("Слово \"" + word.toUpperCase() + "\" уже было, введите другое");
            resetTextField();
        }
    }

    /**
     *
     */
    private void getWinner() {
        textField.setEnabled(false);
        setMsgTop("Игра закончена!");
        setMsgMiddle("");
        if (getPointsA() > getPointsB()) {
            setMsgBotton("Победил игрок 1");
        } else if (getPointsA() == getPointsB()) {
            setMsgBotton("Победила дружба!");
        } else {
            setMsgBotton("Победил игрок 2");
        }
        resetTextField();
        resetList();
        resetCount();
        resetCountA();
        resetPointsA();
        resetPointsB();
        resetSwitch();
        status = 2;
        btnRun.setText("Новая игра");
        btnGiveUp.setText("Выход");
    }

    /**
     *
     */
    @Override
    public void setPointsA() {
        main.setPointsA();
    }

    /**
     *
     */
    @Override
    public void setPointsB() {
        main.setPointsB();
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getPointsA() {
        return main.getPointsA();
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getPointsB() {
        return main.getPointsB();
    }

    /**
     *
     * @param number {@link int}
     */
    @Override
    public void setSteps(int number) {
        main.setSteps(number);
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getSteps() {
        return main.getSteps();
    }

    /**
     *
     */
    @Override
    public void setCount() {
        main.setCount();
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getCount() {
        return main.getCount();
    }

    /**
     *
     */
    @Override
    public void setCountA() {
        main.setCountA();
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getCountA() {
        return main.getCountA();
    }

    /**
     *
     * @param word {@link String}
     */
    @Override
    public void setWord(String word) {
        main.setWord(word);
    }

    /**
     *
     * @param word {@link String}
     * @return {@link boolean}
     */
    @Override
    public boolean addWordToList(String word) {
        return main.addWordToList(word);
    }

    /**
     *
     * @return {@link char}
     */
    @Override
    public char getLastLetter() {
        return main.getLastLetter();
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getSwitcher() {
        return main.getSwitcher();
    }

    /**
     *
     */
    @Override
    public void switchPlayer() {
        main.switchPlayer();
    }

    /**
     *
     */
    @Override
    public void resetList() {
        main.resetList();
    }

    /**
     *
     */
    @Override
    public void resetCount() {
        main.resetCount();
    }

    /**
     *
     */
    @Override
    public void resetCountA() {
        main.resetCountA();
    }

    /**
     *
     */
    @Override
    public void resetPointsA() {
        main.resetPointsA();
    }

    /**
     *
     */
    @Override
    public void resetPointsB() {
        main.resetPointsB();
    }

    /**
     *
     */
    @Override
    public void resetSwitch() {
        main.resetSwitch();
    }

    /**
     *
     * @return {@link boolean}
     */
    private boolean wordCorrect() {
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(getWord());
        while (m.find()) {
            return true;
        }
        return false;
    }

    private class Listener extends KeyAdapter implements ActionListener {

        public Listener(MainFrame frame) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
                btnRun.doClick();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            word = getWord();
            textField.setEnabled(true);
            textField.grabFocus();
            switch (e.getActionCommand()) {
                case "0":
                    switch (status) {
                        case 0:
                            enterSteps();
                            textField.grabFocus();
                            break;
                        case 1:
                            if (getCount() < getSteps() - 1) {
                                if (!word.equals("")) {
                                    play();
                                    textField.grabFocus();
                                } else {
                                    setMsgBotton("Вы не ввели слово");
                                    textField.grabFocus();
                                }
                                return;
                            }
                            setPointsB();
                            setMsgScore();
                            setMsgCountDown(getCount() + 1);
                            getWinner();
                            break;
                        case 2:
                            btnRun.setText("Ок");
                            btnGiveUp.setText("Отмена");
                            setMsgTop("Новая игра");
                            setMsgMiddle("Введите количество ходов");
                            setMsgBotton("");
                            msgScore.setText("Счет 0 : 0");
                            resetMsgCountDown();
                            status = 0;
                            break;
                    }
                    break;
                case "1":
                    switch (status) {
                        case 0:
                            dispose();
                            break;
                        case 1:
                            setCountA();
                            setCount();
                            switchPlayer();
                            btnRun.setText("Ход игрока " + (getSwitcher() + 1));
                            setMsgCountDown(getCount());
                            setMsgBotton("Перевод хода");
                            if (getCountA() == 2) {
                                getWinner();
                            }
                            textField.grabFocus();
                            break;
                        case 2:
                            dispose();
                            break;
                    }
            }
        }
    }
}
