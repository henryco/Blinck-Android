package net.henryco.blinck.service;

import android.util.Log;
import lombok.val;
import net.henryco.blinck.service.http.ProfileUpdateHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * Created by HenryCo on 30/09/17.
 */

public class ProfileUpdateService {

	private final ProfileUpdateHttpService updateHttpService;

	public ProfileUpdateService(ProfileUpdateHttpService updateHttpService) {
		this.updateHttpService = updateHttpService;
	}



	public final void updateProfile(Authorization authorization,
	                                UserProfileForm profileForm,
	                                BlinckConsumer<Boolean> responseConsumer) {

		val call = updateHttpService.updateProfile(authorization.getToken(), profileForm);
		call.enqueue(new RetroCallback<>((responseBodyCall, response) -> {

			if (response.isSuccessful())
				processResponse(response.body(), responseConsumer);
			else responseConsumer.consume(false);

		}));
	}


	public final void updateNickname(Authorization authorization, String nickname,
	                                 BlinckConsumer<Boolean> responseConsumer) {

		val call = updateHttpService.updateNickname(authorization.getToken(), nickname);
		call.enqueue(new RetroCallback<>((responseBodyCall, response) -> {

			Log.d("Profile update", response.toString());

			Log.d("Profile update", "enqueue");
			if (response.isSuccessful()) {

				Log.d("Profile update", "status OK");
				processResponse(response.body(), responseConsumer);
			}
			else {

				Log.d("Profile update", "status FAIL");
				responseConsumer.consume(false);
			}

		}));
	}


	private static void processResponse(ResponseBody body,
	                                    BlinckConsumer<Boolean> responseConsumer) {

		Log.d("Profile update", "response processing .");

		if (body == null) {

			Log.d("Profile update", "response processing FAIL");

			responseConsumer.consume(false);
			return;
		}

		try {
			Log.d("Profile update", "response processing ..");

			responseConsumer.consume(Boolean.valueOf(body.string()));

			Log.d("Profile update", "response processing OK");

		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Profile update", "response processing FAIL");

			responseConsumer.consume(false);
		}
	}

}