package net.henryco.blinck.util.retro;

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


	private Retrofit createRetrofit(Class<?> retroServiceClass) {
		return new Retrofit.Builder()
				.baseUrl(processAnnotationURL(retroServiceClass, baseURL))
				.addConverterFactory(converterFactory == null ? GsonConverterFactory.create() : converterFactory)
		.build();
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
		return createRetrofit(retroServiceClass).create(retroServiceClass);
	}



	public RetroTemplate setConverterFactory(Converter.Factory converterFactory) {
		this.converterFactory = converterFactory;
		return this;
	}

	public RetroTemplate setBaseURL(String baseURL) {
		this.baseURL = baseURL;
		return this;
	}
}
