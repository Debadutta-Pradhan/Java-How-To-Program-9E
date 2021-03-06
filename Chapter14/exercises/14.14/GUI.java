/*
 *       Filename:  GUI.java
 *
 *    Description:  Exercise 14.14 - Guess-the-Number Game
 *
 *        Created:  24/01/16 18:17:26
 *       Revision:  none
 *
 *        @Author:  Siidney Watson - siidney.watson.work@gmail.com
 *       @Version:  1.0
 *
 * =====================================================================================
 */
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GUI extends JFrame{
    private JPanel container;
    private JPanel feedbackPane;

    private JTextField inputField;
    private JLabel titleLabel;
    private JLabel feedbackLabel;
    private JButton retryButton;

    private Font f;

    private GuessTheNumberGame gtn = new GuessTheNumberGame();

    // CONSTRUCTOR
    public GUI(){
        super("Guess The Number Game");

        createElements();

        add(container);
    }
    // create gui elements
    private void createElements(){
        container = new JPanel(new BorderLayout(5, 5));
        feedbackPane = new JPanel();

        titleLabel = new JLabel(
                "<html>I have a number between 1 and 1000.<br>Can you guess my number?</html>",
                SwingConstants.CENTER);
        feedbackLabel = new JLabel("");
        retryButton = new JButton("Retry?");

        retryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                reset();
            }
        });

        feedbackPane.add(feedbackLabel);
        feedbackPane.add(retryButton);
        retryButton.setVisible(false);

        f = new Font("Sans-Serif", Font.PLAIN, 30);

        inputField = new JTextField(3);
        inputField.setFont(f);
        inputField.setHorizontalAlignment(JTextField.CENTER);

        TextFieldHandler handler = new TextFieldHandler();
        inputField.addActionListener(handler);

        container.add(titleLabel, BorderLayout.PAGE_START);
        container.add(inputField, BorderLayout.CENTER);
        container.add(feedbackPane, BorderLayout.PAGE_END);
    }
    // private inner class for event handling
    private class TextFieldHandler implements ActionListener{
        // process text field events
        public void actionPerformed(ActionEvent event){
            int guess = 0;
            // user pressed enter
            if(event.getSource() == inputField){
                try{
                    guess = Integer.parseInt(event.getActionCommand());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                feedbackLabel.setText(gtn.guess(guess));

                if(feedbackLabel.getText() == "Correct!"){
                    inputField.setEditable(false);
                    retryButton.setVisible(true);
                }else{
                    inputField.setText("");
                    if(gtn.isCloser())
                        inputField.setBackground(Color.RED);
                    else
                        inputField.setBackground(Color.BLUE);
                }

            }
        }
    }
    // reset everything to intial state
    private void reset(){
        retryButton.setVisible(false);
        feedbackLabel.setText("");
        inputField.setEditable(true);
        inputField.setText("");
        inputField.setBackground(Color.WHITE);

        gtn.reset();
    }
}
