package disarray;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import disarray.util.FileUtil;
import disarray.util.GitHubUtil;

public class GitBackup {

	private Queue<Repo> repos;
	private String outputPath;

	/**
	 * Initialize variables and parse the command line arguments.
	 */
	public GitBackup() {
		this.repos = new LinkedList<>();
		this.outputPath = "";
	}

	/**
	 * Download repositories
	 * 
	 * @param args The command line arguments
	 */
	public void run(String[] args) {
		if (args.length <= 1)
			printUsage();

		// Get all links from the command line arguments
		ArrayList<String> links = processArgs(args);

		// Extract repositories from all of our links
		this.repos = GitHubUtil.getRepos(links);
		
		// Download all repositories
		ArrayList<Repo> batch = new ArrayList<>();
		while(!repos.isEmpty()) {
			
			// Add three repositories to a batch
			for(int i = 0; i < 3; i++) {
				Repo r = repos.remove();
				r.setOutputPath(this.outputPath);
				r.start();
				batch.add(r);
				
				if(repos.isEmpty())
					break;
			}
			
			// Wait for all repos in the batch to finish downloading
			for(Repo r : batch) {
				try {
					r.join();
				} catch (InterruptedException e) {
					System.err.println("A repo was interrupted");
				}
			}
			
			// Clear out the batch
			batch.clear();
		}
		
		System.out.println("Done!");
	}

	/**
	 * Extract all links from an array of command line arguments
	 * 
	 * @param args The command line arguments
	 * @return Returns an ArrayList containing all GitHub repository links
	 */
	private ArrayList<String> processArgs(String[] args) {
		// Get files and/or links from command line arguments
		ArrayList<String> files = new ArrayList<>();
		ArrayList<String> links = new ArrayList<>();
		for (int i = 0; i < args.length - 1; i++) {
			String arg = args[i];
			if (arg.equals("-l") || arg.equals("--link")) {
				links.add(args[i + 1]);
				i++;
			} else if (arg.equals("-f") || arg.equals("--file")) {
				files.add(args[i + 1]);
				i++;
			} else if (arg.equals("-o") || arg.equals("--output-path")) {
				this.outputPath = args[i + 1];
				
				if(this.outputPath.endsWith("/") || this.outputPath.endsWith("\\")) {
					this.outputPath = this.outputPath.substring(0, this.outputPath.length() - 1);
					System.out.println("new out: " + this.outputPath);
				}
				
				i++;
			} else {
				printUsage();
			}
		}

		// Read repository links from each file
		for (String fileName : files) {
			File f = new File(fileName);

			if (!f.exists()) {
				System.err.printf("Error: File (%s) does not exist.\n", f.toString());
				continue;
			}
			
			links.addAll(FileUtil.readAllLines(f));
		}

		return links;
	}

	/**
	 * Prints the command line usage and exits the program with status 1
	 */
	private static void printUsage() {
		System.out.println("Usage: git-backup.jar <options>\n    -l, --link <link>\n    -f, --file <file name>\n    -o, --output-path <folder name>");
		System.exit(1);
	}
}
