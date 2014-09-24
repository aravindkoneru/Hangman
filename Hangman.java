import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

	private String word = "";
	private String[][] image = { { "|", "-", "-", "-" },
			{ "|", " ", " ", "|" }, { "|", " ", " ", " " },
			{ "|", " ", " ", " ", " " }, { "|", " ", " ", " ", " " },
			{ "=", "=", "=", "=", "=", "=" } };
	private String[] bodyParts = { "O", "|", "\\", "/", "/", "\\" };
	private int[][] cords = { { 2, 3 }, { 3, 3 }, { 3, 2 }, { 3, 4 }, { 4, 3 },
			{ 4, 4 } };

	private int wrongGuesses = 0;
	private boolean loser = false;

	public Hangman(String FILE_NAME) {
		Random gen = new Random();
		int line = gen.nextInt(58110);
		File file = new File(FILE_NAME);
		try {

			Scanner inFile = new Scanner(file);
			int counter = 0;
			while (inFile.hasNextLine()) {
				if (counter == line) {
					String current = inFile.nextLine();
					word = current;
					break;
				}
				inFile.nextLine();
				counter++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

	}

	// splits the word into an array
	// returns an array that contains the letters of the word
	public String[] splitIntoArray() {
		String[] splitWord = new String[word.length()];
		for (int x = 0; x < word.length(); x++) {
			splitWord[x] = word.substring(x, x + 1);
		}
		return splitWord;
	}

	// prints the hangman image
	public void printImage() {
		for (String[] row : image) {
			System.out.println();
			for (String col : row) {
				System.out.print(col);
			}
		}
	}

	// inputs the userInput and return a
	// boolean to determine if the input is valid
	public boolean isValidChar(String userInput) {
		if (userInput.length() > 1) {
			return false;
		} else if (userInput.matches("[.,!?-]")) {
			return false;
		}
		try {
			int temp = Integer.parseInt(userInput);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}

	// adds the body parts to the image
	public void wrongGuess() {
		String toAdd = bodyParts[wrongGuesses];
		int row = cords[wrongGuesses][0];
		int col = cords[wrongGuesses][1];
		image[row][col] = toAdd;
		wrongGuesses++;
		if (wrongGuesses == 6) {
			loser = true;
		}
	}

	public void playGame() {
		Scanner k = new Scanner(System.in);
		String[] word = splitIntoArray();
		String[] guess = new String[word.length];
		for (int x = 0; x < guess.length; x++) {
			guess[x] = "_";
		}
		boolean guessAdded = false;
		while (!loser) {
			printImage();
			System.out.println();
			for (String x : guess) {
				System.out.print(x + " ");
			}

			System.out.println();
			System.out.print("Enter a character: ");
			String guessChar = k.nextLine();
			System.out.println();
			while (!isValidChar(guessChar)) {
				System.out.println("Invalid input. Try again.");
				System.out.print("Enter a character: ");
				guessChar = k.nextLine();
			}

			for (int x = 0; x < word.length; x++) {
				if (guessChar.toLowerCase().equals(word[x])) {
					guess[x] = word[x];
					guessAdded = true;
				}
			}

			if (!guessAdded) {
				wrongGuess();
			}

			if (!(Arrays.asList(guess).contains("_"))) {
				break;
			}

			guessAdded = false;
		}

		if (loser) {
			printImage();
			System.out.println("\nYou lose");
			System.out.println("The word was:");
			for (String x : word) {
				System.out.print(x);
			}
		} else {
			System.out.println("You win");
		}
	}
	
	public String getWord(){
		return word;
	}

	public static void main(String[] args) {
		Hangman test = new Hangman("dictionary.txt");
		test.playGame();

	}

}
