package net.henryco.blinck.util.task;

import android.os.AsyncTask;

/**
 * Created by HenryCo on 07/05/17.
 */

public class VoidAsyncTask extends AsyncTask<Void, Void, Void> {

	public interface DoInBackGround {
		void doInBackground();
	}
	private DoInBackGround doInBackGround;

	public VoidAsyncTask() {}
	public VoidAsyncTask(DoInBackGround doInBackGround) {
		setDoInBackground(doInBackGround);
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (doInBackGround != null) doInBackGround.doInBackground();
		return null;
	}

	public VoidAsyncTask setDoInBackground(DoInBackGround doInBackGround) {
		this.doInBackGround = doInBackGround;
		return this;
	}
}
