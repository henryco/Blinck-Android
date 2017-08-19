package net.henryco.blinck.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import okhttp3.ResponseBody;

import java.io.*;


/**
 * Created by HenryCo on 07/05/17.
 */

public final class Utils {

	public static final class Flag {

		private volatile boolean flag;
		public Flag(boolean intiValue) {
			setFlag(intiValue);
		}
		public Flag() {
			this(true);
		}
		public synchronized Flag setFlag(boolean flag) {
			this.flag = flag;
			return this;
		}
		public boolean getFlag() {
			return flag;
		}
		public synchronized boolean check() {
			boolean flag = getFlag();
			setFlag(false);
			return flag;
		}
	}


	public static AccessToken getAccessTokenSafety(AppCompatActivity context,
	                                               Class<? extends AppCompatActivity> activityClass) {

		AccessToken token = AccessToken.getCurrentAccessToken();
		if (token == null) {
			try {
				LoginManager.getInstance().logOut();
			} catch (Exception ignored) {}
			Intent loaded = new Intent(context, activityClass);
			context.startActivity(loaded);
			context.finish();
		}
		return token;
	}



	public static String getStringRes(Activity context, int string) {
		return getStringRes(context.getApplicationContext(), string);
	}



	public static String getStringRes(Context context, int string) {
		return context.getResources().getString(string);
	}



	public static boolean writeResponseBodyToDisk(ResponseBody body, File file) {
		try {

			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				byte[] fileReader = new byte[4096];

				long fileSize = body.contentLength();
				long fileSizeDownloaded = 0;

				inputStream = body.byteStream();
				outputStream = new FileOutputStream(file);

				while (true) {
					int read = inputStream.read(fileReader);
					if (read == -1) break;
					outputStream.write(fileReader, 0, read);
					fileSizeDownloaded += read;
					Log.d("Media download", "file download: " + fileSizeDownloaded + " of " + fileSize);
				}
				outputStream.flush();
				return true;
			} catch (IOException e) {
				return false;
			} finally {
				if (inputStream != null) inputStream.close();
				if (outputStream != null) outputStream.close();
			}
		} catch (IOException e) {
			return false;
		}
	}


	public static File getCachePath(Context context) {
		File cachePath = new File(context.getCacheDir(), "images"); // see: res/xml/filepaths.xml
		cachePath.mkdirs();
		return cachePath;
	}

}
