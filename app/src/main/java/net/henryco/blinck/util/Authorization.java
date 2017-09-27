package net.henryco.blinck.util;

import android.content.Context;
import lombok.val;
import net.henryco.blinck.R;

/**
 * Created by HenryCo on 27/09/17.
 */

public final class Authorization {

	public static final String HEADER = "Authorization";

	private final Long uid;
	private final String token;

	public Authorization(Context context) {

		val preferences = context.getSharedPreferences(
				context.getString(R.string.preference_file_key),
				Context.MODE_PRIVATE
		);

		uid = Long.decode(preferences.getString(context.getString(R.string.preference_app_uid), null));
		token = preferences.getString(context.getString(R.string.preference_app_token), null);
	}


	public Long getUid() {
		return uid;
	}

	public String getToken() {
		return token;
	}

}