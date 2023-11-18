package disarray;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import disarray.util.FileUtil;
import disarray.util.GitHubUtil;

public class Repo extends Thread {
	private String url, name, outputPath;
	
	public Repo(String url) throws MalformedURLException {
		if (GitHubUtil.getLinkType(url) != 0)
			throw new MalformedURLException("URL provded does not appear to be a link to a github repository: " + url);

		this.url = url;
		this.name = url.substring(url.lastIndexOf("/") + 1);
		this.outputPath = "";
	}
	
	public Repo(String url, String outputPath) throws MalformedURLException {
		this(url);
		this.setOutputPath(outputPath);
	}
	
	@Override
	public void run() {
		try {
			try {
				URL mainURL = new URI(this.url + "/archive/refs/heads/main.zip").toURL();
				FileUtil.download(mainURL, this.outputPath + this.name + "-main.zip");
			} catch (FileNotFoundException e) {
				URL masterURL = new URI(this.url + "/archive/refs/heads/master.zip").toURL();
				FileUtil.download(masterURL, this.outputPath + this.name +"-master.zip");
			}
		} catch (Exception e) {
			System.err.println("Error while downloading file: " + this.url);
			e.printStackTrace();
		}
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
		
		if(!this.outputPath.endsWith("/")) {
			this.outputPath += "/";
		}
	}
}
