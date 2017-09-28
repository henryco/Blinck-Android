package net.henryco.blinck.groups.login;

import dagger.Module;
import dagger.Provides;
import net.henryco.blinck.groups.login.service.BlinckLoginService;
import net.henryco.blinck.util.retro.RetroTemplate;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by HenryCo on 20/08/17.
 */
@Module
public class LoginModule {


	@Provides @Singleton
	public OkHttpClient provideHttpLoginClient() {
		return new OkHttpClient.Builder()
				.readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS)
		.build();
	}

	@Provides @Singleton
	public BlinckLoginService provideLoginService(OkHttpClient client) {
		return new RetroTemplate().setHttpClient(client).create(BlinckLoginService.class);
	}

}