package net.henryco.blinck.util;

import android.content.Context;
import android.util.Log;
import okhttp3.ResponseBody;

import java.io.*;


/**
 * Created by HenryCo on 07/05/17.
 */

public final class FileUtils {



	public static boolean writeResponseBodyToDisk(ResponseBody body, File file) {
		return writeFileToDisk(body.byteStream(), body.contentLength(), file);
	}


	public static boolean writeFileToDisk(InputStream byteStream, long fileSize, File file) {

		try {

			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {

				byte[] fileReader = new byte[4096];

				long fileSizeDownloaded = 0;

				inputStream = byteStream;
				outputStream = new FileOutputStream(file);

				while (true) {

					int read = inputStream.read(fileReader);
					if (read == -1) break;

					outputStream.write(fileReader, 0, read);
					fileSizeDownloaded += read;

					Log.d("Media saved", "file saved: " + fileSizeDownloaded + " of " + fileSize);

				}

				outputStream.flush();
				return true;

			} catch (IOException e) {
				e.printStackTrace();
				return false;

			} finally {

				if (inputStream != null)
					inputStream.close();

				if (outputStream != null)
					outputStream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public static File getCachePath(Context context, String ... name) {

		String path = "";
		for (String n: name) {
			path += (File.separator + n);
		}

		File cachePath = new File(context.getCacheDir(), path); // see: res/xml/filepaths.xml

		cachePath.mkdirs();
		return cachePath;
	}

}


