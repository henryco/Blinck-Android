package net.henryco.blinck.modules.app.userprofile;

import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.sqlightning.reflect.annotations.repository.Repository;
import net.henryco.sqlightning.reflect.repository.LightningRepository;

/**
 * Created by HenryCo on 27/09/17.
 */
@Repository("profile_form")
public interface UserProfileFormRepository
		extends LightningRepository<Long, UserProfileForm> {


}