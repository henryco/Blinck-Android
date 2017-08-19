package net.henryco.blinck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import net.henryco.blinck.R;

public class FbLoginActivity extends AppCompatActivity {



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

	private LoginButton fbLoginButton;
	private CallbackManager callbackManager;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fb_login);

		fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
		fbLoginButton.setReadPermissions(permissions);
		fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult loginResult) {

				AccessToken accessToken = loginResult.getAccessToken();
				if (!accessToken.isExpired()) {



				} else LoginManager.getInstance().logOut();

			}

			@Override
			public void onCancel() {
				LoginManager.getInstance().logOut();
			}

			@Override
			public void onError(FacebookException error) {
				LoginManager.getInstance().logOut();
			}
		});


	}




}
