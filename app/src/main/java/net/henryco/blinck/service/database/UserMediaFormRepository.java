package net.henryco.blinck.service.database;

import net.henryco.blinck.util.form.user.UserMediaForm;
import net.henryco.sqlightning.reflect.annotations.repository.Repository;
import net.henryco.sqlightning.reflect.repository.LightningRepository;

/**
 * Created by HenryCo on 28/09/17.
 */

@Repository("media_form")
public interface UserMediaFormRepository
		extends LightningRepository<Long, UserMediaForm> {


}