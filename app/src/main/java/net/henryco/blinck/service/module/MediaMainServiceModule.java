package net.henryco.blinck.service.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.service.database.UserMediaFormRepository;
import net.henryco.blinck.service.http.ProfileMediaHttpService;
import net.henryco.blinck.util.retro.RetroTemplate;
import net.henryco.sqlightning.SQLightning;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by HenryCo on 28/09/17.
 */
@Module
public class MediaMainServiceModule {


	private final Application application;

	public MediaMainServiceModule(Application application) {
		this.application = application;
	}


	@Singleton @Provides
	public UserMediaFormRepository provideRepository() {
		return SQLightning.getRepositoryBuilder(application)
				.create(UserMediaFormRepository.class);
	}


	@Singleton @Provides
	public ProfileMediaHttpService provideHttpService() {

		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(10, TimeUnit.SECONDS)
				.connectTimeout(10, TimeUnit.SECONDS)
		.build();

		return new RetroTemplate()
				.setHttpClient(client)
		.create(ProfileMediaHttpService.class);
	}


	@Singleton @Provides
	public MediaMainService provideMediaService(
			UserMediaFormRepository mediaRepo,
			ProfileMediaHttpService httpService) {
		return new MediaMainService(mediaRepo, httpService);
	}
}
