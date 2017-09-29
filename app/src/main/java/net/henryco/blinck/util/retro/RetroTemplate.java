package net.henryco.blinck.util.retro;

import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HenryCo on 19/08/17.
 */

public final class RetroTemplate {

	public String testBaseUrlValue;

	private Converter.Factory converterFactory = null;
	private String baseURL = null;
	private OkHttpClient client = null;


	private Converter.Factory createConverterFactory() {

		if (converterFactory != null)
			return converterFactory;

		return GsonConverterFactory.create(
				new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation()
				.create()
		);
	}


	private Retrofit createRetrofit(Class<?> retroServiceClass) {

		Retrofit.Builder builder = new Retrofit.Builder();
		builder.baseUrl(processAnnotationURL(retroServiceClass, baseURL));
		builder.addConverterFactory(createConverterFactory());

		if (client != null) builder.client(client);

		return builder.build();
	}


	private String processAnnotationURL(Class<?> retroServiceClass, String baseURL) {

		if (baseURL != null) {
			testBaseUrlValue = baseURL;
			return baseURL;
		}

		RetroURL retroServiceClassAnnotation = retroServiceClass.getAnnotation(RetroURL.class);
		if (retroServiceClassAnnotation == null) throw new RuntimeException(retroServiceClass.getSimpleName() + " " +
				"must be annotated by "+RetroURL.class.getSimpleName());
		testBaseUrlValue = retroServiceClassAnnotation.value();
		return retroServiceClassAnnotation.value();
	}




	public <T> T create(Class<T> retroServiceClass) {
		return createRetrofit(retroServiceClass)
				.create(retroServiceClass);
	}



	public RetroTemplate setConverterFactory(Converter.Factory converterFactory) {
		this.converterFactory = converterFactory;
		return this;
	}

	public RetroTemplate setBaseURL(String baseURL) {
		this.baseURL = baseURL;
		return this;
	}

	public RetroTemplate setHttpClient(OkHttpClient client) {
		this.client = client;
		return this;
	}
}
