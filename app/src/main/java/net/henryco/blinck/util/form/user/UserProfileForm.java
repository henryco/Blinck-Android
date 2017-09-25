package net.henryco.blinck.util.form.user;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by HenryCo on 25/09/17.
 */
@Data @NoArgsConstructor
public final class UserProfileForm {

	private @Expose
	UserNameForm userName;

	private @Expose
	String gender;

	private @Expose
	String about;

	private @Expose
	Long birthday;

}
