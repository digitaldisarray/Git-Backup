import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Program {
	private static String temp; // String for storing temporary information.

	// TODO: Fix. Everything. (messy code)

	/*
	 * Prepare to cringe at this messy code. Sorry in advance. Prepare to cringe at
	 * this messy code. Sorry in advance. Prepare to cringe at this messy code.
	 * Sorry in advance. Prepare to cringe at this messy code. Sorry in advance.
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
				txtDownloadLinks();
				break;
			case 6:
				exit();
				break;
			}
		}

	}

	// Generate repo download links from a txt
	private static void txtDownloadLinks() {
		temp = getInput("File with links: ");
		if (!temp.endsWith(".txt")) {
			temp += ".txt";
		}

		temp = readLinks(temp);
		String[] links = temp.split("@@&&??");
		
		temp = getInput("Output file: ");
		if (!temp.endsWith(".txt")) {
			temp += ".txt";
		}

		try {
			File file = new File(temp);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			String[] linksConverted = links;
			
			for (int i = 0; i < links.length - 1; i++) {
				System.out.println(i);
				
				linksConverted[i] = linkToDownload(links[i]);
				
				fw.write(linksConverted[i] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// Ignored.
		}

	}

	// Convert a link to a download link.
	private static String linkToDownload(String link) {
		String[] raw = link.split("/");
		return "https://github.com/" + raw[3] + "/" + raw[4] + "/archive/master.zip";
	}

	// Read links from a file
	private static String readLinks(String name) {
		try {
			// Create BufferedReader, StringBuilder, and String line.
			BufferedReader br = new BufferedReader(new FileReader(name));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			// Read the file while there is still content.
			while (line != null) {
				sb.append(line + "@@&&??");
				line = br.readLine();
			}
			temp = sb.toString();
			br.close();
		} catch (IOException e) {
			System.err.println("IOException while reading file.");
			// e.printStackTrace();
		}
		return temp;
	}

	// Download a readme file
	private static void downloadReadme() {
		temp = getInput("Enter url: ");
		if (temp.startsWith("https://github.com/")) {
			temp = repoToRead(temp);
		}

		try {
			URL url = new URL(temp);
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
		System.out.println("1: Display help.\n" + "2: Convert a readme.md link to a repo link.\n"
				+ "3: Convert a repo link to a readme link.\n" + "4: Download a readme from a repo or readme url.\n"
				+ "5: Generate download links from a txt of repos.");
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
