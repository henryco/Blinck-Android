package net.henryco.blinck.service;

import net.henryco.blinck.service.database.UserMediaFormRepository;
import net.henryco.blinck.service.http.ProfileMediaHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserMediaForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;

/**
 * Created by HenryCo on 28/09/17.
 */

public class MediaMainService {


	private final UserMediaFormRepository mediaRepo;
	private final ProfileMediaHttpService httpService;


	public MediaMainService(UserMediaFormRepository mediaRepo,
	                        ProfileMediaHttpService httpService) {
		this.mediaRepo = mediaRepo;
		this.httpService = httpService;
	}


	public final void loadProfileMediaInfo(Authorization authorization,
	                                       BlinckConsumer<UserMediaForm> mediaFormConsumer) {

		httpService.getUserMediaForm(authorization.getToken(), authorization.getUid()).enqueue(
				new RetroCallback<>((userMediaFormCall, userMediaFormResponse)
						-> mediaFormConsumer.consume(userMediaFormResponse.body()))
		);
	}

}
