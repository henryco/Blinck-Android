package net.henryco.blinck.modules.login.service;


import net.henryco.blinck.modules.BlinckServerAPI;
import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;



/**
 * Created by HenryCo on 20/08/17.
 */

@RetroURL(BlinckServerAPI.SERVER_URL)
public interface BlinckLoginService {


	@GET("/public/facebook/permissions")
	Call<List<String>> getRequiredFacebookPermissionsList();

}
