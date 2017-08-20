package net.henryco.blinck.modules.login.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import net.henryco.blinck.R;
import net.henryco.blinck.modules.BlinckApplication;
import net.henryco.blinck.modules.login.http.BlinckLoginService;
import net.henryco.blinck.util.retro.RetroCallback;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

	private static final String[] permissions = {
			"user_birthday",
			"user_location",
			"user_likes",
			"user_education_history",
			"user_photos",
			"user_friends",
			"user_about_me",
			"read_custom_friendlists",
			"public_profile"
	};


	@Inject SharedPreferences sharedPreferences;
	@Inject BlinckLoginService loginService;

	CallbackManager callbackManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		((BlinckApplication) getApplication()).getLoginComponent().inject(this);



		AccessToken accessToken = AccessToken.getCurrentAccessToken();

		LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
		callbackManager = CallbackManager.Factory.create();

		loginButton.setReadPermissions(permissions);
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				loginAction(loginResult.getAccessToken());
			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException error) {

			}
		});


		if (accessToken == null || accessToken.isExpired()) {
			LoginManager.getInstance().logOut();
			enableLoginButton(loginButton);
		} else loginAction(accessToken);



	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}


	private void enableLoginButton(LoginButton loginButton) {
		loginButton.setEnabled(true);
		loginButton.setVisibility(View.VISIBLE);
	}

	private void disableLoginButton(LoginButton loginButton) {
		loginButton.setEnabled(false);
		loginButton.setVisibility(View.INVISIBLE);
	}


	private void loginAction(AccessToken accessToken) {
		System.out.println("USER ID: "+accessToken.getUserId());
	}

}