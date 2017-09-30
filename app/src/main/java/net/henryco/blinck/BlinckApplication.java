package net.henryco.blinck;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;
import net.henryco.blinck.configuration.DataBaseConfiguration;
import net.henryco.blinck.groups.app.AppModule;
import net.henryco.blinck.groups.login.DaggerLoginComponent;
import net.henryco.blinck.groups.login.LoginComponent;
import net.henryco.blinck.groups.login.LoginModule;
import net.henryco.blinck.groups.main.DaggerMainComponent;
import net.henryco.blinck.groups.main.MainComponent;
import net.henryco.blinck.groups.main.MainModule;
import net.henryco.blinck.groups.profile.DaggerProfileComponent;
import net.henryco.blinck.groups.profile.ProfileComponent;
import net.henryco.blinck.groups.profile.ProfileModule;
import net.henryco.blinck.service.module.InfoMainServiceModule;
import net.henryco.blinck.service.module.MediaMainServiceModule;
import net.henryco.blinck.service.module.UpdateProfileServiceModule;
import net.henryco.sqlightning.SQLightning;


public class BlinckApplication extends Application {


	private AppModule appModule;
	private LoginComponent loginComponent;
	private MainComponent mainComponent;
	private ProfileComponent profileComponent;


	@Override
	public void onCreate() {
		super.onCreate();

		AppEventsLogger.activateApp(this);
		SQLightning.run(this, DataBaseConfiguration.class);

		initializeDagger2();
	}


	private void initializeDagger2() {
		createAppModule();
		createLoginComponent();
		createMainComponent();
		createProfileComponent();
	}

	private void createAppModule() {
		this.appModule = new AppModule(this);
	}

	private void createLoginComponent() {
		this.loginComponent = DaggerLoginComponent.builder()
				.appModule(appModule)
				.loginModule(new LoginModule())
		.build();
	}

	private void createMainComponent() {
		this.mainComponent = DaggerMainComponent.builder()
				.appModule(appModule)
				.mainModule(new MainModule(this))
				.infoMainServiceModule(new InfoMainServiceModule(this))
				.mediaMainServiceModule(new MediaMainServiceModule(this))
		.build();
	}

	private void createProfileComponent() {
		this.profileComponent = DaggerProfileComponent.builder()
				.profileModule(new ProfileModule())
				.infoMainServiceModule(new InfoMainServiceModule(this))
				.mediaMainServiceModule(new MediaMainServiceModule(this))
				.updateProfileServiceModule(new UpdateProfileServiceModule())
		.build();
	}



//  --------------------------- GETTERS -------------------------    //

	public LoginComponent getLoginComponent() {
		return this.loginComponent;
	}

	public MainComponent getMainComponent() {
		return this.mainComponent;
	}

	public ProfileComponent getProfileComponent() {
		return this.profileComponent;
	}
}