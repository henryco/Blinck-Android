package net.henryco.blinck.modules.login.broker;

import android.support.annotation.Nullable;


/**
 * Created by HenryCo on 20/08/17.
 */

public final class BrokerResult<RESULT, EXCEPTION extends Exception> {

	RESULT result = null;
	EXCEPTION exception = null;
	boolean success = false;

	public BrokerResult() {}
	public BrokerResult(RESULT result, EXCEPTION exception, boolean success) {
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


	public void setResult(RESULT result) {
		this.result = result;
	}

	public void setException(EXCEPTION exception) {
		this.exception = exception;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
