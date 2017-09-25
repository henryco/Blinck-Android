package net.henryco.blinck.util.form.user;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by HenryCo on 25/09/17.
 */
@Data @NoArgsConstructor
public final class UserNameForm {

	private @Expose
	String firstName;

	private @Expose
	String secondName;

	private @Expose
	String lastName;

	private @Expose
	String nickname;

}