package disarray.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import disarray.Repo;


/**
 * This class holds utility functions related to GitHub and repositories
 */
public class GitHubUtil {
	
	/**
	 * Given a list of username link, username + repo name, or username w/ stars tab specified, it will extract all github repo links from that page.
	 * 
	 * @param links A list of links to extract repos from
	 * @return A list of all extracted repository objects from the links. Returns empty list if none are found
	 */
	public static Queue<Repo> getRepos(ArrayList<String> links) {
		Queue<Repo> repos = new LinkedList<>();
		
		for (String link : links) {
			// If it is a page of starred repos
			int type = getLinkType(link);
			switch(type) {
			case 0: // single repo
				try {
					repos.add(new Repo(link));
				} catch (MalformedURLException e) {
					// do nothing
				}
				break;
			case 1: // stars list
				return getStarredRepos(link);
			case 2: // profile
				return getProfileRepos(link);
			default: // no type
				// do nothing
			}
		}
		
		return repos;
	}
	
	/**
	 * Gets all the repos on a given page. Page url must have ended in ?tab=repositories or ?tab=starred
	 * 
	 * @param doc The page to extract repos from
	 * @return Returns a list of all repos found on the given page
	 */
	private static List<Repo> getReposOnPage(Document doc) {
		List<Repo> repos = new ArrayList<>();
		
		// Get all starred repo link elements on the page
		Elements elements = doc.select("div h3 a");
		for(Element e : elements) {
			try {
				// Create new repo from the absolute url of the starred repo (includes https://github.com/)
				repos.add(new Repo(e.attr("abs:href")));
			} catch (MalformedURLException e1) {
				System.err.println("Accidentally found a non repo link while getting repos on page: " + doc.baseUri());
			}
		}
		
		return repos;
	}
	
	/**
	 * Gets all of the starred repositories from the link to a user's starred page
	 * Note: This function will continue to the next page if available
	 * 
	 * @param url The url to a user's starred page (must end in ?tab=starred)
	 * @return Returns a list of all found starred repos
	 */
	private static LinkedList<Repo> getStarredRepos(String url) {
		LinkedList<Repo> starred = new LinkedList<>();
		
		// Load web page
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Jsoup failed to fetch url: " + url);
		}

		// Iterate all pages, looking for repo links
		while(true) {
			starred.addAll(getReposOnPage(doc));
			
			// Try to find next button
			Element nextButton = doc.select("a[href]:containsWholeOwnText(Next)").first();
			if(nextButton == null) 
				break;
			
			try {
				doc = Jsoup.connect(nextButton.attr("href")).get();
			} catch (IOException e) {
				System.err.println("Jsoup failed to go to next page. URL: " + url);
				break;
			}
		}
		
		return starred;
	}
	
	
	private static LinkedList<Repo> getProfileRepos(String url) {
		LinkedList<Repo> starred = new LinkedList<>();
		
		// Load web page
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Jsoup failed to fetch url: " + url);
		}

		// Iterate all pages, looking for repo links
		while(true) {
			starred.addAll(getReposOnPage(doc));
			
			// Try to find next button
			Element nextButton = doc.select("a[href]:containsWholeOwnText(Next)").first();
			if(nextButton == null) 
				break;
			
			try {
				doc = Jsoup.connect(nextButton.attr("href")).get();
			} catch (IOException e) {
				System.err.println("Jsoup failed to go to next page. URL: " + url);
				break;
			}
		}
		
		return starred;
	}
	
	/**
	 * Returns an integer based on a links type
	 * 
	 * @param link The link to test for a type
	 * @return -1 invalid, 0 repo, 1 stars, 2 profile
	 */
	public static int getLinkType(String link) {
		/*
		 * Regex Explanation:
		 * ( 				// First group (optional)
		 * 	   (https|http) // https or http
		 *     :\/\/        // followed by ://
		 * )?
		 * (www.)?          // www. (optional)
		 * github\.com\/    // must have github.com/
		 * ([\w-\.]+)\/     // username/
		 * ([\w-\.]+)\/?    // reponame/ (last / optional)
		 */
		if (link.matches("((https|http):\\/\\/)?(www.)?github\\.com\\/([\\w-\\.]+)\\/([\\w-\\.]+)\\/?"))
			return 0;
		
		if (link.matches("((https|http):\\/\\/)?(www.)?github\\.com\\/([\\w-\\.]+)\\?tab=stars")) 
			return 1;
		
		if (link.matches("((https|http):\\/\\/)?(www.)?github\\.com\\/([\\w-\\.]+)\\?tab=repositories"))
			return 2;
		
		if (link.matches("((https|http):\\/\\/)?(www.)?github\\.com\\/([\\w-\\.]+)"))
			return 2;
		
		return -1;
	}
}
