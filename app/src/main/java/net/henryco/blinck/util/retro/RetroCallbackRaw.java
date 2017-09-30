package net.henryco.blinck.util.retro;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import net.henryco.blinck.util.function.BlinckBiConsumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by HenryCo on 01/10/17.
 */
@NoArgsConstructor
public final class RetroCallbackRaw<T> implements Callback<ResponseBody> {


	private BlinckBiConsumer<Call<ResponseBody>, T> onResponseConsumer;
	private BlinckBiConsumer<Call<ResponseBody>, Throwable> onFailureConsumer;
	private Class<?> type;


	public RetroCallbackRaw(Class<T> type, BlinckBiConsumer<Call<ResponseBody>, T> consumer) {
		onResponse(type, consumer);
	}

	public RetroCallbackRaw(Class<T> type,
	                        BlinckBiConsumer<Call<ResponseBody>, T> onResponse,
	                        BlinckBiConsumer<Call<ResponseBody>, Throwable> onFailure) {
		onResponse(type, onResponse);
		onFailure(onFailure);
	}


	public RetroCallbackRaw onResponse(Class<T> type, BlinckBiConsumer<Call<ResponseBody>, T> consumer) {

		this.onResponseConsumer = consumer;
		this.type = type;
		return this;
	}

	public RetroCallbackRaw onFailure(BlinckBiConsumer<Call<ResponseBody>, Throwable> consumer) {

		this.onFailureConsumer = consumer;
		return this;
	}


	@Override
	public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

		final ResponseBody body = response.body();
		if (body != null && onResponseConsumer != null) try {

			onResponseConsumer.consume(call, (T) new Gson().fromJson(body.string(), type));
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("RetroCallbackRaw", "Response processing error: "+response.toString());
		}
	}

	@Override
	public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
		if (onFailureConsumer != null) onFailureConsumer.consume(call, t);
	}
}