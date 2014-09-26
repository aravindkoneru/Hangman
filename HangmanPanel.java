import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class HangmanPanel extends JPanel {

	private static final int PANEL_WIDTH = 500;
	private static final int PANEL_HEIGHT = 500;
	private String[] hangmanWord;
	private String[] guess;
	private int wrongGuesses = 0;
	private int correctGuesses = 0;
	private int evenSpacing = 0;
	private int xBase = PANEL_WIDTH / 5;
	private int yBase = PANEL_HEIGHT / 10;

	private int[][] lineCords = {
		{xBase + 180, yBase + 100, xBase + 180, yBase + 185},
		{xBase + 180, yBase + 100, xBase + 155, yBase + 165},
		{xBase + 180, yBase + 100, xBase + 205, yBase + 165},
		{xBase + 180, yBase + 185, xBase + 155, yBase + 250},
		{xBase + 180, yBase + 185, xBase + 205, yBase + 250}
	};

	private Ellipse2D.Double head = new Ellipse2D.Double(xBase + 155, yBase + 50, 50, 50);


	public HangmanPanel(String word) {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		hangmanWord = new String[word.length()];
		for(int x =0; x<word.length(); x++){
			hangmanWord[x] = word.substring(x,x+1);
		}
		guess = new String[hangmanWord.length];
		for(int x = 0; x < guess.length; x++){
			guess[x] = "_";
		}
	}

	//uses an algorithem in order to center all of lines regardless of box size and word size
	public int[] calcX(){
		evenSpacing = (PANEL_WIDTH-hangmanWord.length*10)/(hangmanWord.length);
		int centerIndex = (hangmanWord.length/2);
		int[] xCords = new int[hangmanWord.length];

		if(xCords.length%2 == 1){
			for(int x = 0; x < xCords.length; x++){
				xCords[x] = (evenSpacing*x);
			}

			int idealCenter = (PANEL_WIDTH)/2;
			int currentCenter = xCords[centerIndex];
			int shift = idealCenter - currentCenter;

			for(int x = 0; x < xCords.length; x++){
				xCords[x] += shift;
			}
		} else{
			for(int x = 0; x < xCords.length; x++){
				xCords[x] = (evenSpacing*x);
			}

			int idealCenter = (PANEL_WIDTH)/2;
			int currentCenter = xCords[centerIndex];
			int currentCenter2 = xCords[centerIndex-1];
			int shift = idealCenter - (currentCenter + currentCenter2)/2;

			for(int x = 0; x < xCords.length; x++){
				xCords[x] += shift;
			}

		}

		return xCords;
	}

	public void drawSetUp(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		int[] xCords = calcX();
		int yCord = 450;

		for(int x = 0; x < hangmanWord.length; x++){
			if(guess[x] == "_"){
				g2.drawLine(xCords[x]-10, yCord, xCords[x]+10,yCord);
			} else{
				g2.drawString(hangmanWord[x], xCords[x], yCord);
			}
		}

		Color c = new Color(255, 255, 255);

		int xLength = PANEL_WIDTH / 5;
		int yLength = PANEL_HEIGHT / 10;
		g2.drawLine(xLength, yLength, xLength, yLength + 300);

		int bCrossX = PANEL_WIDTH / 5;
		int bCrossY = PANEL_HEIGHT / 10;
		g2.drawLine(bCrossX - 50, bCrossY + 300, bCrossX + 150, bCrossY + 300);

		int tCrossX = PANEL_WIDTH / 5;
		int tCrossY = PANEL_HEIGHT / 10;
		g2.drawLine(tCrossX, tCrossY, tCrossX + 180, tCrossY);

		int ropeX = PANEL_WIDTH / 5;
		int ropeY = PANEL_HEIGHT / 10;
		g2.drawLine(ropeX + 180, ropeY, ropeX + 180, ropeY + 50);
	}

	public void wrongGuess(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		if(wrongGuesses > 0){
			g2.draw(head);
			if(wrongGuesses > 1){
				for(int x = 0; x < wrongGuesses-1; x++){
					int[] cords = lineCords[x];
					g2.drawLine(cords[0], cords[1], cords[2], cords[3]);
				}
			}
		}

		if(wrongGuesses == 6){
			g2.drawString("YOU LOSE!", PANEL_WIDTH/2, PANEL_HEIGHT/2);
		} else if(correctGuesses == hangmanWord.length) {
			g2.drawString("YOU WON!", PANEL_WIDTH/2, PANEL_HEIGHT/2);
		}
	}

	public void play(String letter){
		boolean correct = false;
		for(int x = 0; x < hangmanWord.length; x++){
			if(hangmanWord[x].equals(letter)){
				guess[x] = hangmanWord[x];
				correct = true;
				correctGuesses++;
			}
		}
		if(!correct){
			wrongGuesses++;
		}

		if(wrongGuesses == 6){
			for(int x = 0; x < hangmanWord.length; x++){
				guess[x] = hangmanWord[x];
			}
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawSetUp(g2);
		wrongGuess(g2);
	}

	public int getWrongGuesses(){
		return wrongGuesses;
	}

	public int getRight(){
		return correctGuesses;
	}

}
