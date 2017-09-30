package net.henryco.blinck.service.http;

import net.henryco.blinck.configuration.BlinckServerAPI;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.retro.RetroURL;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by HenryCo on 30/09/17.
 */

@RetroURL(BlinckServerAPI.SERVER_URL + "/protected/user/profile/update/")
public interface ProfileUpdateHttpService {


	@POST("bio") Call<ResponseBody> updateProfile(
			@Header(BlinckServerAPI.HttpHeaders.AUTHORIZATION) String authorization,
			@Body UserProfileForm profile
	);


	@POST("nickname") Call<ResponseBody> updateNickname(
			@Header(BlinckServerAPI.HttpHeaders.AUTHORIZATION) String authorization,
			@Body String nickname
	);

}