package net.henryco.blinck.util.form.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by HenryCo on 25/09/17.
 */
@Data @NoArgsConstructor
@AllArgsConstructor
public final class UserLoginForm {

	private @Expose @SerializedName("facebook_uid") String uid;
	private @Expose @SerializedName("facebook_token") String token;

}
