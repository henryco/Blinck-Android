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
import net.henryco.blinck.modules.BlinckServerAPI;
import net.henryco.blinck.modules.login.broker.FacebookLoginBroker;
import net.henryco.blinck.modules.login.form.UserLoginForm;
import net.henryco.blinck.modules.login.service.BlinckLoginService;
import net.henryco.blinck.util.function.BlinckBiConsumer;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;
import net.henryco.blinck.util.retro.RetroCallback;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


	private FacebookLoginBroker facebookLoginBroker;

	@Inject SharedPreferences sharedPreferences;
	@Inject BlinckLoginService loginService;

	@AutoFind(R.id.fb_login_button)
	private LoginButton loginButton;



	private final BlinckBiConsumer<Call<List<String>>, Response<List<String>>>
			onResponse = (listCall, listResponse) -> {
		List<String> list = listResponse.body();
		onGetPermissionsSuccess(list == null
				? permissionsAlert()
				: list.toArray(new String[0])
		);
	};


	private final BlinckBiConsumer<Call<List<String>>, Throwable>
			onFailure = (listCall, throwable) -> {
		throwable.printStackTrace();
		// TODO: 25/09/17
	};


	private static String[] permissionsAlert() {
		return new String[] {
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
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		((BlinckApplication) getApplication()).getLoginComponent().inject(this);
		AutoFinder.find(this);

		loginService.getRequiredFacebookPermissionsList()
				.enqueue(new RetroCallback<>(onResponse, onFailure));
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebookLoginBroker.onActivityResult(requestCode, resultCode, data);
	}


	private void onGetPermissionsSuccess(String ... permissions) {

		LoginManager.getInstance().logOut(); // TODO: 20/08/17 REMOVE IT
		final AccessToken accessToken = AccessToken.getCurrentAccessToken();

		loginButton.setReadPermissions(permissions);

		facebookLoginBroker = new FacebookLoginBroker(loginButton, CallbackManager.Factory.create());
		facebookLoginBroker.onSuccess(loginResult -> loginAction(loginResult.getAccessToken()));
		facebookLoginBroker.activate();

		if (accessToken != null && !accessToken.isExpired()) {
			facebookLoginBroker.disableLoginButton();
			loginAction(accessToken);
		}
		else facebookLoginBroker.reset();
	}


	private void loginAction(AccessToken accessToken) {

		System.out.println("USER ID: "+accessToken.getUserId());

		UserLoginForm form = new UserLoginForm(accessToken.getUserId(), accessToken.getToken());
		loginService.postLoginForm(form).enqueue(new RetroCallback<>((call, response) -> {
			String token = response.headers().get(BlinckServerAPI.Headers.AUTHORIZATION);
			System.out.println("TOKEN: "+token);
		}));
	}



}