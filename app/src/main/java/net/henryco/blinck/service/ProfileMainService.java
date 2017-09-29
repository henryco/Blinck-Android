package net.henryco.blinck.service;

import net.henryco.blinck.service.database.UserProfileFormRepository;
import net.henryco.blinck.service.http.ProfileInfoHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;


/**
 * Created by HenryCo on 27/09/17.
 */

public class ProfileMainService {


	private final ProfileInfoHttpService infoService;
	private final UserProfileFormRepository profileFormRepository;


	public ProfileMainService(ProfileInfoHttpService infoService,
	                          UserProfileFormRepository profileFormRepository) {
		this.infoService = infoService;
		this.profileFormRepository = profileFormRepository;
	}


	public final void logout(Authorization authorization) {
		infoService.logOutUser(authorization.getToken()).enqueue(new RetroCallback<>());
	}


	public final UserProfileForm getProfileFromCache(Long id) {
		return profileFormRepository.getRecordById(id);
	}


	public final boolean cacheProfile(UserProfileForm form) {

		if (form == null) return false;
		if (profileFormRepository.isRecordExist(form.getId()))
			return profileFormRepository.updateRecord(form);
		else return profileFormRepository.saveRecord(form);
	}


	public final void loadProfileFromServer(Authorization authorization,
	                                        BlinckConsumer<UserProfileForm> profileConsumer) {

		infoService.getUserProfile(authorization.getToken(), authorization.getUid())
				.enqueue(new RetroCallback<>((call, response) -> profileConsumer.consume(response.body())));
	}


	public final void loadAndCacheProfileFromServer(Authorization authorization,
	                                               BlinckConsumer<UserProfileForm> profileConsumer) {

		final Long id = authorization.getUid();
		final UserProfileForm cached = getProfileFromCache(id);
		if (cached != null) profileConsumer.consume(cached);

		infoService.getUserProfile(authorization.getToken(), id).enqueue(

				new RetroCallback<>((call, response) -> {

					UserProfileForm body = response.body();
					if (body != null) {
						body.setId(id);
						body.getUserName().setId(id);
						cacheProfile(body);
						profileConsumer.consume(body);
					}
				})
		);
	}

}