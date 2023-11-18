package disarray.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;

/**
 * This class holds utility functions related to file i/o
 */
public class FileUtil {


	/**
	 * Read all of the lines from a given file (UTF-8)
	 * 
	 * @param file The file to read all of the lines from
	 * @return Returns a collection containing each line from the file
	 */
	public static Collection<String> readAllLines(File file) {
		Collection<String> lines = Collections.emptyList();

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	
	/**
	 * Downloads a file from a URL and saves it with a specified file name
	 * 
	 * @param url The URL to download a file from
	 * @param fileName The file's name
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void download(URL url, String fileName) throws FileNotFoundException, IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
		System.out.println("Downloaded " + url.toString());
	}

}
