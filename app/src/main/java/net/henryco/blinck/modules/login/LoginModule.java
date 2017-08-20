package net.henryco.blinck.modules.login;

import dagger.Module;
import dagger.Provides;
import net.henryco.blinck.modules.login.service.BlinckLoginService;
import net.henryco.blinck.util.retro.RetroTemplate;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 20/08/17.
 */
@Module
public class LoginModule {



	@Provides @Singleton
	public BlinckLoginService provideLoginService() {
		return new RetroTemplate().create(BlinckLoginService.class);
	}

}