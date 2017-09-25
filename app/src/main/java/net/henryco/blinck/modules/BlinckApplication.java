package net.henryco.blinck.modules;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;
import net.henryco.blinck.modules.app.AppModule;
import net.henryco.blinck.modules.login.DaggerLoginComponent;
import net.henryco.blinck.modules.login.LoginComponent;
import net.henryco.blinck.modules.login.LoginModule;


public class BlinckApplication extends Application {


	private AppModule appModule;
	private LoginComponent loginComponent;


	@Override
	public void onCreate() {
		super.onCreate();

		AppEventsLogger.activateApp(this);

		initializeDagger2();
	}


	private void initializeDagger2() {
		createAppModule();
		createLoginComponent();
	}

	private void createAppModule() {
		this.appModule =  new AppModule(this);
	}

	private void createLoginComponent() {
		this.loginComponent = DaggerLoginComponent.builder()
				.appModule(appModule)
				.loginModule(new LoginModule())
		.build();
	}





//  --------------------------- GETTERS -------------------------    //

	public LoginComponent getLoginComponent() {
		return this.loginComponent;
	}

}