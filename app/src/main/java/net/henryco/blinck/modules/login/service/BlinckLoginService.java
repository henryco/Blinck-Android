package net.henryco.blinck.modules.login.service;


import net.henryco.blinck.modules.BlinckServerAPI;
import net.henryco.blinck.util.form.UserLoginForm;
import net.henryco.blinck.util.form.UserStatusForm;
import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;



/**
 * Created by HenryCo on 20/08/17.
 */

@RetroURL(BlinckServerAPI.SERVER_URL)
public interface BlinckLoginService extends BlinckServerAPI {


	@GET("/public/facebook/permissions")
	Call<List<String>> getRequiredFacebookPermissionsList();

	@POST("/login/user")
	Call<Void> postLoginForm(@Body UserLoginForm loginForm);

	@GET("/session/user")
	Call<UserStatusForm> getUserStatus(@Header(HttpHeaders.AUTHORIZATION) String authorization);
}
