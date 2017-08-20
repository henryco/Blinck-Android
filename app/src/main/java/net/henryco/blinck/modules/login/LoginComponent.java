package net.henryco.blinck.modules.login;

import dagger.Component;
import net.henryco.blinck.modules.app.AppModule;
import net.henryco.blinck.modules.login.activity.LoginActivity;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 20/08/17.
 */
@Singleton
@Component(modules = {AppModule.class, LoginModule.class})
public interface LoginComponent {

	void inject(LoginActivity loginActivity);
}
