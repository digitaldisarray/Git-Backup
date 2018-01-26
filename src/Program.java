import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Program {
	private static String temp; // String for storing temporary information.

	// TODO: Fix. Everything. (messy code)
	
	/* Prepare to cringe at this messy code. Sorry in advance.
	 * Prepare to cringe at this messy code. Sorry in advance.
	 * Prepare to cringe at this messy code. Sorry in advance.
	 * Prepare to cringe at this messy code. Sorry in advance.
	 */ 

	public static void input(String input) {
		// Check if input is valid
		if (input.length() > 1 || input.isEmpty()) {
			System.out.println(Strings.error + "Input is invalid.");
		} else {
			int inputInt = Integer.parseInt(input);
			switch (inputInt) {
			case 1:
				help();
				break;
			case 2:
				input = getInput("Enter readme link:");
				System.out.println(readToRepo(input));
				break;
			case 3:
				input = getInput("Enter repo link:");
				System.out.println(repoToRead(input));
				break;
			case 4:
				downloadReadme();
				break;
			case 5:
				exit();
				break;
			}
		}

	}

	// Download a readme file
	private static void downloadReadme() {
		temp = getInput("Enter url: ");
		if(temp.startsWith("https://github.com/")) {
			temp = repoToRead(temp);
		}

		URL url;
		
		
		try {
			url = new URL(temp);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;

			temp = getInput("Enter new file name: ");
			if (!temp.endsWith(".txt")) {
				temp += ".txt";
			}
			
			File file = new File(temp);

			if (!file.exists()) {
				file.createNewFile();
			} else {
				System.out.println(Strings.error + "File " + temp + " already exists.");
			}

			// Write the file.
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			while ((inputLine = br.readLine()) != null) {
				bw.write(inputLine + "\n");
			}
			
			bw.close();
			br.close();

		} catch (MalformedURLException e) {
			System.err.println(Strings.error + "MalformedURLException.");
			// e.printStackTrace();
		} catch (IOException e) {
			System.err.println(Strings.error + "IOException while trying to download readme.");
			// e.printStackTrace();
		}
	}

	// Convert readme links to repo links.
	private static String readToRepo(String link) {
		String[] raw = link.split("/");
		link = "https://github.com/" + raw[3] + "/" + raw[4];
		return link;
	}

	// Convert repo links to readme links
	private static String repoToRead(String link) {
		String[] raw = link.split("/");
		link = "https://raw.githubusercontent.com/" + raw[3] + "/" + raw[4] + "/master/README.md";
		return link;
	}

	// Display help information.
	private static void help() {
		System.out.println("Command 1: Display help.\n" + "Command 2: Convert a readme.md link to a repo link.\n"
				+ "Command 3: Convert a repo link to a readme link.\n"
				+ "Command 4: Download a readme from a repo or readme url.");
	}

	// Prompt user to exit the program.
	private static void exit() {
		temp = getInput("Are you sure you want to exit? (y/n) ");
		if (temp.equals("y") || temp.equals("yes")) {
			// We do print instead of println to avoid a ghost line at the bottom.
			System.out.print(Strings.ok + "Exiting...");
			System.exit(0);
		} else {
			return;
		}
	}

	// Display a message and get the users input.
	private static String getInput(String message) {
		System.out.print(message);
		String input = Main.in.next();
		return input;
	}
}
