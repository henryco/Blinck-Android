package net.henryco.blinck.modules.main;

import dagger.Component;
import net.henryco.blinck.modules.app.AppModule;
import net.henryco.blinck.modules.main.activity.MainPageActivity;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, MainModule.class})
public interface MainComponent {

	void inject(MainPageActivity activity);
}
