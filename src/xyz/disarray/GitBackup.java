package xyz.disarray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GitBackup {

	public void run() {
		// Load the list of repos
		File list = new File(System.getProperty("user.dir") + "\\list.txt");

		// Make sure list exists, if it doesn't, create it
		fileExists(list);

		// Make sure downloads folder exists
		new File(System.getProperty("user.dir") + "\\downloads\\").mkdir();
		
		System.out.println("list.txt found\nreading list.txt");

		// Read the list and create repo objects from the urls
		List<Repo> repos = new ArrayList<>();
		for (String url : readAllLines(list.getAbsolutePath())) {
			repos.add(new Repo(url)); // Add the repo
			repos.get(repos.size() - 1).start(); // Start the repo download
		}

		// Wait for repos to complete
		boolean allDone = false;
		while (!allDone) {
			allDone = true;
			for (Repo r : repos) {
				if (r.isAlive()) {
					allDone = false;
				}
			}
		}
		
		System.out.println("Done!");
	}

	private static List<String> readAllLines(String path) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	
	/**
	 * Makes sure a file exists. If it does not exist, it creates it.
	 * @param file the file to make sure exists
	 * @return Returns true if the file exists, false if it needed to be created
	 */
	private boolean fileExists(File file) {
		if (!file.exists()) {
			System.out.println(file.getName() + " not found, creating...");

			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
}
