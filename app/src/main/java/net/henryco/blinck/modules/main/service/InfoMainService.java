package net.henryco.blinck.modules.main.service;

import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;

import java.io.Serializable;

/**
 * Created by HenryCo on 27/09/17.
 */

public class InfoMainService {


	private final BlinckProfileInfoService infoService;
	private final UserProfileFormRepository profileFormRepository;


	public InfoMainService(BlinckProfileInfoService infoService,
	                       UserProfileFormRepository profileFormRepository) {
		this.infoService = infoService;
		this.profileFormRepository = profileFormRepository;
	}




	public final UserProfileForm getProfileFromCache(Long id) {
		return profileFormRepository.getRecordById(id);
	}


	public final boolean cacheProfile(UserProfileForm form) {

		if (profileFormRepository.isRecordExist(form.getId()))
			return profileFormRepository.updateRecord(form);
		else return profileFormRepository.saveRecord(form);
	}


	public final void loadProfileFromServer(Long id, Serializable authentication,
	                                        BlinckConsumer<UserProfileForm> profileConsumer) {

		System.out.println("AUTH: "+authentication);
		infoService.getUserProfile(authentication.toString(), id)
				.enqueue(new RetroCallback<>(
						(call, response) -> profileConsumer.consume(response.body())));
	}


	public final void loadAndCacheProfileFromServer(Long id, Serializable authentication,
	                                               BlinckConsumer<UserProfileForm> profileConsumer) {

		infoService.getUserProfile(authentication.toString(), id)
				.enqueue(new RetroCallback<>((userProfileFormCall, response) -> {

				}));
	}

}