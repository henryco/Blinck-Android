package net.henryco.blinck.util.task;

import android.os.AsyncTask;
import net.henryco.blinck.util.function.BlinckFunction;

/**
 * Created by HenryCo on 07/05/17.
 */

public class BlinckAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	private BlinckFunction<Result, Params[]> doInBackGround;

	public BlinckAsyncTask() {}
	public BlinckAsyncTask(BlinckFunction<Result, Params[]> doInBackGround) {
		setBackgroundTask(doInBackGround);
	}

	@SafeVarargs @Override
	protected final Result doInBackground(Params... params) {
		return doInBackGround != null ? doInBackGround.apply(params) : null;
	}

	public BlinckAsyncTask<Params, Progress, Result> setBackgroundTask(BlinckFunction<Result, Params[]> doInBackGround) {
		this.doInBackGround = doInBackGround;
		return this;
	}
}
