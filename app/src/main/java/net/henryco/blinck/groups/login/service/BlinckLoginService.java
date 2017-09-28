package net.henryco.blinck.groups.login.service;


import net.henryco.blinck.configuration.BlinckServerAPI;
import net.henryco.blinck.util.form.login.UserLoginForm;
import net.henryco.blinck.util.form.login.UserStatusForm;
import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

@RetroURL(BlinckServerAPI.SERVER_URL)
public interface BlinckLoginService {


	@GET("/public/facebook/permissions")
	Call<List<String>> getRequiredFacebookPermissionsList();

	@POST("/login/user")
	Call<Void> postLoginForm(@Body UserLoginForm loginForm);

	@GET("/session/user")
	Call<UserStatusForm> getUserStatus(@Header(BlinckServerAPI.HttpHeaders.AUTHORIZATION) String authorization);
}
