package net.henryco.blinck.modules.main.service;

import net.henryco.blinck.modules.BlinckServerAPI;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by HenryCo on 27/09/17.
 */

@RetroURL(BlinckServerAPI.SERVER_URL)
public interface BlinckProfileInfoService {

	@GET("/protected/user/profile/bio")
	Call<UserProfileForm> getUserProfile(
			@Header(BlinckServerAPI.HttpHeaders.AUTHORIZATION) String authorization,
	        @Query("id") Long userId
	);

}