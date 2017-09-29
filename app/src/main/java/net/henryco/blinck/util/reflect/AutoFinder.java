package net.henryco.blinck.util.reflect;

import android.app.Activity;
import android.view.ViewGroup;

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


	public static <T extends Activity> void inflate(T activity, int root, int content, boolean attachToRoot) {

		ViewGroup rootGroup = (ViewGroup) activity.findViewById(root);
		rootGroup.addView(activity.getLayoutInflater().inflate(content, rootGroup, attachToRoot));
	}

	public static <T extends Activity> void inflate(T activity, int root, int content) {
		inflate(activity, root, content, false);
	}


	public static <T extends Activity> void inflateAndFind(T activity, int root, int content, boolean attachToRoot) {
		inflate(activity, root, content, attachToRoot);
		find(activity);
	}

	public static <T extends Activity> void inflateAndFind(T activity, int root, int content) {
		inflateAndFind(activity, root, content, false);
	}

}
