package net.henryco.blinck.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.JsonPrimitive;
import net.henryco.blinck.service.database.UserMediaFormRepository;
import net.henryco.blinck.service.http.ProfileMediaHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserMediaForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;
import net.henryco.blinck.util.task.VoidAsyncTask;
import okhttp3.ResponseBody;

import java.io.InputStream;

/**
 * Created by HenryCo on 28/09/17.
 */

public class MediaMainService {


	private final UserMediaFormRepository mediaRepo;
	private final ProfileMediaHttpService httpService;


	public MediaMainService(UserMediaFormRepository mediaRepo,
	                        ProfileMediaHttpService httpService) {
		this.mediaRepo = mediaRepo;
		this.httpService = httpService;
	}




	public void loadProfileMediaInfo(Authorization authorization,
	                                 BlinckConsumer<UserMediaForm> mediaFormConsumer) {

		httpService.getUserMediaForm(authorization.getToken(), authorization.getUid()).enqueue(
				new RetroCallback<>((userMediaFormCall, userMediaFormResponse)
						-> mediaFormConsumer.consume(userMediaFormResponse.body()))
		);
	}


	public void loadProfileAvatarImage(Authorization authorization,
	                                   BlinckConsumer<Bitmap> bitmapConsumer) {

		loadProfileMediaInfo(authorization, mediaForm -> {
			httpService.downloadImageResource(mediaForm.getImages().getAvatar()).enqueue(


					new RetroCallback<>((call, response) -> {

						if (response.isSuccessful()) {

							ResponseBody responseBody = response.body();
							if (responseBody != null) {

								new VoidAsyncTask(() -> {

									try (InputStream inputStream = responseBody.byteStream()) {
										Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
										bitmapConsumer.consume(bitmap);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}).execute();

							}


						}


					})


			);
		});
	}


}
