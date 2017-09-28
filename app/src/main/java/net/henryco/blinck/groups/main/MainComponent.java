package net.henryco.blinck.groups.main;

import dagger.Component;
import net.henryco.blinck.groups.app.AppModule;
import net.henryco.blinck.groups.main.activity.MainPageActivity;
import net.henryco.blinck.service.module.InfoMainServiceModule;
import net.henryco.blinck.service.module.MediaMainServiceModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
		AppModule.class,
		MainModule.class,
		InfoMainServiceModule.class,
		MediaMainServiceModule.class
}) public interface MainComponent {

	void inject(MainPageActivity activity);
}
