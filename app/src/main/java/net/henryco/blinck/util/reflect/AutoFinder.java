package net.henryco.blinck.util.reflect;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * Created by HenryCo on 25/09/17.
 */

public abstract class AutoFinder {

	public static <T extends Activity> void find(T activity) {

		for (Field field : activity.getClass().getDeclaredFields()) {

			AutoFind annotation = field.getAnnotation(AutoFind.class);
			if (annotation == null) continue;

			try {
				field.setAccessible(true);
				field.set(activity, activity.findViewById(annotation.value()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
