package net.henryco.blinck.modules.login.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import net.henryco.blinck.R;
import net.henryco.blinck.modules.BlinckApplication;
import net.henryco.blinck.modules.login.broker.FacebookLoginBroker;
import net.henryco.blinck.modules.login.service.BlinckLoginService;

import javax.inject.Inject;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


	@Inject SharedPreferences sharedPreferences;
	@Inject BlinckLoginService loginService;

	private FacebookLoginBroker facebookLoginBroker;


	private static final String[] DEFAULT_PERMISSIONS = {
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




	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		((BlinckApplication) getApplication()).getLoginComponent().inject(this);

		LoginManager.getInstance().logOut(); // TODO: 20/08/17 REMOVE IT
		final AccessToken accessToken = AccessToken.getCurrentAccessToken();
		final LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);

		loginButton.setReadPermissions(getPermissions());

		facebookLoginBroker = new FacebookLoginBroker(loginButton, CallbackManager.Factory.create());
		facebookLoginBroker.onSuccess(loginResult -> loginAction(loginResult.getAccessToken()));
		facebookLoginBroker.start();


		if (accessToken != null && !accessToken.isExpired()) {
			facebookLoginBroker.disableLoginButton();
			loginAction(accessToken);
		}
		else {
			facebookLoginBroker.logOut();
			facebookLoginBroker.enableLoginButton();
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebookLoginBroker.onActivityResult(requestCode, resultCode, data);
	}




	private String[] getPermissions() {
		try {
			List<String> body = loginService.getRequiredFacebookPermissionsList().execute().body();
			return body != null ? body.toArray(new String[0]) : DEFAULT_PERMISSIONS;
		} catch (Exception e) {
			e.printStackTrace();
			return DEFAULT_PERMISSIONS;
		}
	}



	private void loginAction(AccessToken accessToken) {
		System.out.println("USER ID: "+accessToken.getUserId());
	}


}