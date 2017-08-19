package net.henryco.blinck.util.task;

import android.os.AsyncTask;

/**
 * Created by HenryCo on 07/05/17.
 */

public class BlinckAsyncTask<T, U, Z> extends AsyncTask<T, U, Z> {

	public interface DoInBackGround<Q, B> {
		Q doInBackground(B... params);
	}
	private DoInBackGround<Z, T> doInBackGround;

	public BlinckAsyncTask() {}
	public BlinckAsyncTask(DoInBackGround<Z, T> doInBackGround) {
		setDoInBackground(doInBackGround);
	}

	@SafeVarargs @Override
	protected final Z doInBackground(T... params) {
		return doInBackGround != null ? doInBackGround.doInBackground(params) : null;
	}

	public BlinckAsyncTask<T, U, Z> setDoInBackground(DoInBackGround<Z, T> doInBackGround) {
		this.doInBackGround = doInBackGround;
		return this;
	}
}
