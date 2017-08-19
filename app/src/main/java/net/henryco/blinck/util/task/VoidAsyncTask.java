package net.henryco.blinck.util.task;

import android.os.AsyncTask;

/**
 * Created by HenryCo on 07/05/17.
 */

public class VoidAsyncTask extends AsyncTask<Void, Void, Void> {

	private Runnable doInBackGround;

	public VoidAsyncTask() {}
	public VoidAsyncTask(Runnable doInBackGround) {
		setBackgroundTask(doInBackGround);
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (doInBackGround != null) doInBackGround.run();
		return null;
	}

	public VoidAsyncTask setBackgroundTask(Runnable doInBackGround) {
		this.doInBackGround = doInBackGround;
		return this;
	}
}
