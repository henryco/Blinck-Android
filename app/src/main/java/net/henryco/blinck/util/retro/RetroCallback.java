package net.henryco.blinck.util.retro;

import android.support.annotation.NonNull;
import net.henryco.blinck.util.function.BlinckBiConsumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HenryCo on 20/08/17.
 */

public class RetroCallback<T> implements Callback<T> {

	private BlinckBiConsumer<Call<T>, Response<T>> onResponseConsumer;
	private BlinckBiConsumer<Call<T>, Throwable> onFailureConsumer;


	public RetroCallback(BlinckBiConsumer<Call<T>, Response<T>> onResponseConsumer,
	                     BlinckBiConsumer<Call<T>, Throwable> onFailureConsumer) {
		onResponse(onResponseConsumer);
		onFailure(onFailureConsumer);
	}

	public RetroCallback(BlinckBiConsumer<Call<T>, Response<T>> onResponseConsumer) {
		this(onResponseConsumer, null);
	}

	public RetroCallback() {
		this(null, null);
	}




	@Override
	public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
		if (onResponseConsumer != null) onResponseConsumer.consume(call, response);
	}

	@Override
	public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
		if (onFailureConsumer != null) onFailureConsumer.consume(call, t);
	}



	public RetroCallback<T> onResponse(BlinckBiConsumer<Call<T>, Response<T>> onResponseConsumer) {
		this.onResponseConsumer = onResponseConsumer;
		return this;
	}

	public RetroCallback<T> onFailure(BlinckBiConsumer<Call<T>, Throwable> onFailureConsumer) {
		this.onFailureConsumer = onFailureConsumer;
		return this;
	}
}
