package net.henryco.blinck.service.module;

import dagger.Module;
import dagger.Provides;
import lombok.NoArgsConstructor;
import net.henryco.blinck.service.ProfileUpdateService;
import net.henryco.blinck.service.http.ProfileUpdateHttpService;
import net.henryco.blinck.util.retro.RetroTemplate;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 30/09/17.
 */

@Module @NoArgsConstructor
public class UpdateProfileServiceModule {



	@Singleton @Provides
	public ProfileUpdateHttpService provideHttpUpdateService() {
		return new RetroTemplate().create(ProfileUpdateHttpService.class);
	}



	@Singleton @Provides
	public ProfileUpdateService provideUpdateProfileService(
			ProfileUpdateHttpService httpService) {
		return new ProfileUpdateService(httpService);
	}


}