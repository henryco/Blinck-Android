package net.henryco.blinck.modules.login.broker;

import android.support.annotation.Nullable;


/**
 * Created by HenryCo on 20/08/17.
 */

final class BrokerResult<RESULT, EXCEPTION extends Exception> {

	private RESULT result = null;
	private EXCEPTION exception = null;
	private boolean success = false;

	BrokerResult(RESULT result, EXCEPTION exception, boolean success) {
		this.result = result;
		this.exception = exception;
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	@Nullable
	public EXCEPTION getException() {
		return exception;
	}

	public RESULT getResult() {
		return result;
	}


}
