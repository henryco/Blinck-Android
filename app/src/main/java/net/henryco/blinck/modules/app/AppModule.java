package net.henryco.blinck.modules.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 20/08/17.
 */
@Module
public class AppModule {

	private final Application application;

	public AppModule(Application application) {
		this.application = application;
	}


	@Provides @Singleton
	public Application providesApplication() {
		return application;
	}


	@Provides @Singleton
	public SharedPreferences providesSharedPreferences(Application application) {
		return PreferenceManager.getDefaultSharedPreferences(application);
	}

}