package net.henryco.blinck.modules.login.broker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import net.henryco.blinck.util.function.BlinckConsumer;

/**
 * Created by HenryCo on 20/08/17.
 */

public class FacebookLoginBroker {

	private final LoginButton loginButton;
	private final CallbackManager callbackManager;

	private LoginResult loginResult;

	private BlinckConsumer<FacebookException> onError;
	private BlinckConsumer<LoginResult> onSuccess;
	private Runnable onCancel;



	public FacebookLoginBroker(LoginButton loginButton,
	                           CallbackManager callbackManager) {
		this.loginButton = loginButton;
		this.callbackManager = callbackManager;
		disableLoginButton();

		this.loginResult = null;
	}


	public BlinckConsumer<BrokerResultHandler<LoginResult, FacebookException>> activateWithResult() {

		return consumer -> {

			enableLoginButton();
			loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

				@Override
				public void onSuccess(LoginResult result) {
					loginResult = result;
				    disableLoginButton();
					if (onSuccess != null)
						onSuccess.consume(result);
					consumer.process(new BrokerResult<>(result, null, true));
				}

				@Override
				public void onCancel() {
					reset();
					if (onCancel != null)
						onCancel.run();
					consumer.process(new BrokerResult<>(null, null, false));
				}

				@Override
				public void onError(FacebookException error) {
					reset();
					if (onError != null)
						onError.consume(error);
					consumer.process(new BrokerResult<>(null, error, false));
				}

			});

		};
	}

	public void reset() {
		logOut();
		enableLoginButton();
	}

	public void activate() {
		activateWithResult().consume(brokerResult -> {});
	}


	public void logOut() {
		LoginManager.getInstance().logOut();
	}

	public void enableLoginButton() {
		loginButton.setEnabled(true);
		loginButton.setVisibility(View.VISIBLE);
	}

	public void disableLoginButton() {
		loginButton.setEnabled(false);
		loginButton.setVisibility(View.INVISIBLE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)  {
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}



	@Nullable
	public LoginResult getLoginResult() {
		return loginResult;
	}



	public FacebookLoginBroker onSuccess(BlinckConsumer<LoginResult> onSuccess) {
		this.onSuccess = onSuccess;
		return this;
	}

	public FacebookLoginBroker onError(BlinckConsumer<FacebookException> onError) {
		this.onError = onError;
		return this;
	}

	public FacebookLoginBroker onCancel(Runnable onCancel) {
		this.onCancel = onCancel;
		return this;
	}
}
