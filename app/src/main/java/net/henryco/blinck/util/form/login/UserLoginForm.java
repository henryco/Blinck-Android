package net.henryco.blinck.util.form.login;

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

	private @SerializedName("facebook_uid") String uid;
	private @SerializedName("facebook_token") String token;

}
