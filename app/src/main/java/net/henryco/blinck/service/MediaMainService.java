package net.henryco.blinck.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import net.henryco.blinck.service.http.ProfileMediaHttpService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.FileUtils;
import net.henryco.blinck.util.form.user.UserMediaForm;
import net.henryco.blinck.util.function.BlinckConsumer;
import net.henryco.blinck.util.retro.RetroCallback;
import net.henryco.blinck.util.task.VoidAsyncTask;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by HenryCo on 28/09/17.
 */

public class MediaMainService {


	private final ProfileMediaHttpService httpService;


	public MediaMainService(ProfileMediaHttpService httpService) {
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
					new RetroCallback<>((call, response) ->
							Helper.consumerResponse(response, responseBody ->
									Helper.consumeBitmap(responseBody.byteStream(), bitmapConsumer)))
			);
		});
	}


	public void loadProfileAvatarFromCache(Context context,
	                                       Authorization authorization,
	                                       BlinckConsumer<Bitmap> bitmapConsumer) {

		Bitmap image = Helper.loadBitmapFromFile(context, authorization.getUid().toString());
		if (image != null) bitmapConsumer.consume(image);
	}


	public void loadAndCacheProfileAvatarImage(Context context,
	                                           Authorization authorization,
	                                           BlinckConsumer<Bitmap> bitmapConsumer) {

		loadProfileAvatarFromCache(context, authorization, bitmapConsumer);
//		Bitmap image = Helper.loadBitmapFromFile(context, authorization.getUid().toString());
//		if (image != null) bitmapConsumer.consume(image);

		loadProfileAvatarImage(authorization, bitmap -> {

			bitmapConsumer.consume(bitmap);
			Helper.saveBitmapToFile(context, bitmap, authorization.getUid().toString());
		});
	}

}



final class Helper {

	private static final String IMAGE_DIR = "images";


	static void consumerResponse(Response<ResponseBody> response,
	                                     BlinckConsumer<ResponseBody> consumer) {

		if (response.isSuccessful()) {
			ResponseBody responseBody = response.body();
			if (responseBody != null)
				new VoidAsyncTask(() -> consumer.consume(responseBody)).execute();
		}
	}


	static void consumeBitmap(InputStream byteStream,
	                                  BlinckConsumer<Bitmap> bitmapConsumer) {

		try (InputStream inputStream = byteStream) {
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			bitmapConsumer.consume(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	static Bitmap loadBitmapFromFile(Context context, String fileName) {

		File root = FileUtils.getCachePath(context, IMAGE_DIR);
		return BitmapFactory.decodeFile(new File(root, fileName).getPath());
	}


	static void saveBitmapToFile(Context context, Bitmap bitmap, String fileName) {

		File root = FileUtils.getCachePath(context, IMAGE_DIR);
		File file = new File(root, fileName);
		boolean saved = FileUtils.writeFileToDisk(getStream(bitmap), bitmap.getByteCount(), file);

		Log.d("Media service", "Avatar saved: "+saved);
	}


	private static InputStream getStream(Bitmap bitmap) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);

		return new ByteArrayInputStream(bos.toByteArray());
	}

}
