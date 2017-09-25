package net.henryco.blinck.util.form.login;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by HenryCo on 25/09/17.
 */

@Data @NoArgsConstructor
public final class UserStatusForm {

	private @Expose String principal;
	private @Expose Boolean active;
}