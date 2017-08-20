package net.henryco.blinck.modules.login.service;

import net.henryco.blinck.util.retro.RetroURL;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by HenryCo on 20/08/17.
 */

@RetroURL
public interface BlinckLoginService {

	@GET("some/path") // TODO: 20/08/17
	Call<List<String>> getRequiredFacebookPermissionsList();
}
