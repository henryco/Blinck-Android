package net.henryco.blinck.modules;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;
import net.henryco.blinck.modules.app.DataBaseConfiguration;
import net.henryco.blinck.modules.app.AppModule;
import net.henryco.blinck.modules.login.DaggerLoginComponent;
import net.henryco.blinck.modules.login.LoginComponent;
import net.henryco.blinck.modules.login.LoginModule;
import net.henryco.blinck.modules.main.DaggerMainComponent;
import net.henryco.blinck.modules.main.MainComponent;
import net.henryco.blinck.modules.main.MainModule;
import net.henryco.sqlightning.SQLightning;


public class BlinckApplication extends Application {


	private AppModule appModule;
	private LoginComponent loginComponent;
	private MainComponent mainComponent;


	@Override
	public void onCreate() {
		super.onCreate();

		AppEventsLogger.activateApp(this);
		SQLightning.run(this, DataBaseConfiguration.class, DataBaseConfiguration.class);

		initializeDagger2();
	}


	private void initializeDagger2() {
		createAppModule();
		createLoginComponent();
		createMainComponent();
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
		.build();
	}




//  --------------------------- GETTERS -------------------------    //

	public LoginComponent getLoginComponent() {
		return this.loginComponent;
	}

	public MainComponent getMainComponent() {
		return this.mainComponent;
	}
}