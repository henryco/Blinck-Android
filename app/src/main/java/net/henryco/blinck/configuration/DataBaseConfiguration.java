package net.henryco.blinck.configuration;

import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.sqlightning.reflect.annotations.config.Configuration;
import net.henryco.sqlightning.reflect.annotations.config.DBName;
import net.henryco.sqlightning.reflect.annotations.config.DBVersion;
import net.henryco.sqlightning.reflect.annotations.config.Main;
import net.henryco.sqlightning.reflect.annotations.repository.Table;

/**
 * Created by HenryCo on 27/09/17.
 */
@Configuration
@DBVersion(1) @Main
@DBName("blinck_database.db")
public final class DataBaseConfiguration {

	@Table("profile_form")
	UserProfileForm profileForm;

}