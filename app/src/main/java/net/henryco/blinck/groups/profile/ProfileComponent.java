package net.henryco.blinck.groups.profile;

import dagger.Component;
import net.henryco.blinck.groups.app.AppModule;
import net.henryco.blinck.groups.profile.activity.ProfileActivity;
import net.henryco.blinck.service.module.InfoMainServiceModule;
import net.henryco.blinck.service.module.MediaMainServiceModule;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 29/09/17.
 */
@Singleton
@Component(modules = {
		ProfileModule.class,
		InfoMainServiceModule.class,
		MediaMainServiceModule.class
}) public interface ProfileComponent {

	void inject(ProfileActivity activity);
}
