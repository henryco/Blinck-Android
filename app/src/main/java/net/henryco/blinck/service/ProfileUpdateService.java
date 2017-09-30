package net.henryco.blinck.service;

import lombok.val;
import net.henryco.blinck.service.http.ProfileUpdateHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallbackRaw;

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
		call.enqueue(new RetroCallbackRaw<>(Boolean.class,
				(responseBodyCall, response) -> responseConsumer.consume(response))
		);
	}


	public final void updateNickname(Authorization authorization, String nickname,
	                                 BlinckConsumer<Boolean> responseConsumer) {

		val call = updateHttpService.updateNickname(authorization.getToken(), nickname);
		call.enqueue(new RetroCallbackRaw<>(Boolean.class,
				(responseBodyCall, response) -> responseConsumer.consume(response))
		);
	}

}