package net.henryco.blinck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.login.widget.LoginButton;
import net.henryco.blinck.R;

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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		LoginButton fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
		fbLoginButton.setReadPermissions(permissions);
	}
}
