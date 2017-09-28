package net.henryco.blinck.service.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import net.henryco.blinck.service.InfoMainService;
import net.henryco.blinck.service.database.UserProfileFormRepository;
import net.henryco.blinck.service.http.ProfileInfoHttpService;
import net.henryco.blinck.util.retro.RetroTemplate;
import net.henryco.sqlightning.SQLightning;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by HenryCo on 28/09/17.
 */
@Module
public class InfoMainServiceModule {

	private final Application application;

	public InfoMainServiceModule(Application application) {
		this.application = application;
	}

	@Singleton @Provides
	public ProfileInfoHttpService provideRestService() {

		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(30, TimeUnit.SECONDS)
				.build();

		return new RetroTemplate()
				.setHttpClient(client)
				.create(ProfileInfoHttpService.class);
	}


	@Singleton @Provides
	public UserProfileFormRepository provideProfileRepository() {

		return SQLightning.getRepositoryBuilder(application)
				.create(UserProfileFormRepository.class);
	}


	@Provides @Singleton
	public InfoMainService provideInfoService(
			ProfileInfoHttpService blinckProfileInfoService,
			UserProfileFormRepository profileFormRepository) {

		return new InfoMainService(
				blinckProfileInfoService,
				profileFormRepository
		);
	}


}
