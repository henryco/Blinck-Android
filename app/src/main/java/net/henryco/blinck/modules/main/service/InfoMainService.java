package net.henryco.blinck.modules.main.service;

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


//	public final UserProfileForm getAndSaveProfile(Long id, Serializable authentication) {
//
//		UserProfileForm form = infoService.getUserProfile(authentication.toString()).execute().body();
//		form.setId(id);
//		boolean saved = profileFormRepository.saveRecord(form);
//		if (!saved) throw new RuntimeException("Cannot cache user bio entity");
//
//		return form;
//	}


}