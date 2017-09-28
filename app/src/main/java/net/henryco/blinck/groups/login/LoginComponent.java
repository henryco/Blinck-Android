package net.henryco.blinck.groups.login;

import dagger.Component;
import net.henryco.blinck.groups.app.AppModule;
import net.henryco.blinck.groups.login.activity.LoginActivity;

import javax.inject.Singleton;

/**
 * Created by HenryCo on 20/08/17.
 */
@Singleton
@Component(modules = {AppModule.class, LoginModule.class})
public interface LoginComponent {

	void inject(LoginActivity loginActivity);
}
