package net.henryco.blinck.modules.main;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import net.henryco.blinck.modules.app.userprofile.UserProfileFormRepository;
import net.henryco.blinck.modules.main.service.BlinckProfileInfoService;
import net.henryco.blinck.modules.main.service.InfoMainService;
import net.henryco.blinck.util.retro.RetroTemplate;
import net.henryco.sqlightning.SQLightning;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;


@Module
public class MainModule {


	private final Application application;
	public MainModule(Application application) {
		this.application = application;
	}



	@Singleton @Provides
	public BlinckProfileInfoService provideRestService() {

		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(30, TimeUnit.SECONDS)
		.build();

		return new RetroTemplate()
				.setHttpClient(client)
		.create(BlinckProfileInfoService.class);
	}


	@Singleton @Provides
	public UserProfileFormRepository provideProfileRepository() {

		return SQLightning.getRepositoryBuilder(application)
				.create(UserProfileFormRepository.class);
	}


	@Provides @Singleton
	public InfoMainService provideInfoService(
			BlinckProfileInfoService blinckProfileInfoService,
			UserProfileFormRepository profileFormRepository) {

		return new InfoMainService(
				blinckProfileInfoService,
				profileFormRepository
		);
	}


}