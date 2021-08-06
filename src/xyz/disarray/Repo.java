package xyz.disarray;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Repo extends Thread {
	private String url;

	public Repo(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try {
			// Get the byte channel from the url
			ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url + "/archive/refs/heads/master.zip").openStream());
			
			// Get the repo name
			String name = url.substring(url.lastIndexOf("/") + 1);
			System.out.println(name);
			
			// Create output streams for the file
			FileOutputStream fileOutputStream = new FileOutputStream("downloads\\" + name + "-master.zip");
			FileChannel fileChannel = fileOutputStream.getChannel();
			
			// Write the output stream to the disk
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			
			// Clost the streams
			fileChannel.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + url);
			return;
		} catch (IOException e) {
			System.out.println("IOException while downloading: " + url);
			e.printStackTrace();
			return;
		}

		System.out.println("Downloaded: " + url);
	}
}
