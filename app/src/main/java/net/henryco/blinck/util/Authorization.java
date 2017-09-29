package net.henryco.blinck.util;

import android.content.Context;
import android.content.SharedPreferences;
import lombok.ToString;
import lombok.val;
import net.henryco.blinck.R;

/**
 * Created by HenryCo on 27/09/17.
 */
@ToString
public final class Authorization {

	public static final String HEADER = "Authorization";

	private final Long uid;
	private final String token;

	private Authorization(Context context) {

		val preferences = Helper.getSharedPreferences(context);

		uid = Helper.decode(preferences.getString(context.getString(R.string.preference_app_uid), null));
		token = preferences.getString(context.getString(R.string.preference_app_token), null);
	}

	public Long getUid() {
		return uid;
	}

	public String getToken() {
		return token;
	}

	public boolean exists() {
		return uid != null
				&& token != null
				&& !token.isEmpty();
	}



	public static Authorization get(Context context) {
		return new Authorization(context);
	}

	public static Boolean exists(Context context) {
		return get(context).exists();
	}

	public static Authorization save(Context context, String userId, String app_token) {

		SharedPreferences.Editor editor = Helper.getSharedPreferences(context).edit();
		editor.putString(context.getString(R.string.preference_app_token), app_token);
		editor.putString(context.getString(R.string.preference_app_uid), userId);
		editor.apply();

		return get(context);
	}

	public static Authorization reset(Context context) {
		return save(context, null, null);
	}

}


final class Helper {

	static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(
				context.getString(R.string.preference_file_key),
				Context.MODE_PRIVATE
		);
	}

	static Long decode(String string) {

		try {
			return Long.decode(string);
		} catch (Exception e) {
			return null;
		}
	}

}