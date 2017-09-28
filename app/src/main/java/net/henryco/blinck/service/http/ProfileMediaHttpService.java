package net.henryco.blinck.service.http;

import net.henryco.blinck.configuration.BlinckServerAPI;
import net.henryco.blinck.util.form.user.UserMediaForm;
import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by HenryCo on 28/09/17.
 */
@RetroURL(BlinckServerAPI.SERVER_URL)
public interface ProfileMediaHttpService {


	@GET("/protected/user/profile/media")
	Call<UserMediaForm> getUserMediaForm(
			@Header(BlinckServerAPI.HttpHeaders.AUTHORIZATION) String authorization,
			@Query("id") Long userId
	);


}