import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class HangmanFrame extends JFrame {

	private Hangman man = new Hangman("dictionary.txt");
	private String word = man.getWord();
	private HangmanPanel hangP = new HangmanPanel(word);
	private Graphics g;


	JTextField userInput = null;

	public HangmanFrame() {
		userInput = new JTextField(20);
		getContentPane().add(hangP, BorderLayout.CENTER);
		setListener();
		pack();
	}

	class textListner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = userInput.getText();
			if(!(hangP.getWrongGuesses() == 6) && !(hangP.getRight() == word.length())){
				if(!man.isValidChar(text)){
					userInput.setText("Inavlid Input. Try again.");
				} else{
					hangP.play(text);
					userInput.setText("");
				}
			} else if(hangP.getWrongGuesses() == 6){
				userInput.setText("YOU LOST!");
			} else if(hangP.getRight() == word.length()){
				userInput.setText("YOU WON!");
			}

		}
	}

	public void setListener(){
		userInput.addActionListener(new textListner());
		JPanel controlPanel = new JPanel();
		controlPanel.add(userInput);
		getContentPane().add(controlPanel, BorderLayout.NORTH);
	}

	public static void main(String[] args){
		HangmanFrame test = new HangmanFrame();
	}

}
