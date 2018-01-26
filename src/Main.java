import java.util.Scanner;

public class Main {
	/*
	 * Digital Disarray 1-16-2018
	 * 1st project of 2018! :)
	 * 
	 * The purpose of this project is to help backup git repos.
	 */
	public static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		String input = null; // Declare the input variable.
		System.out.println(Strings.title); // Print the software title.
		while(true) {
			System.out.print(Strings.menu + "\n" + Strings.prompt); // Prompt user.
			input = in.next(); // Get input and assign to string 'input'.
			Program.input(input); // Send user input to be dealt with.
		}
	}
}
