package net.henryco.blinck.util.adapter;

import java.lang.reflect.Method;

/**
 * Created by HenryCo on 22/05/17.
 */

public final class DataAdapter {

	private DataAdapter() {}


	public static <T> T adapt(final Object adaptedFrom) {

		Adapter adapter = adaptedFrom.getClass().getAnnotation(Adapter.class);
		if (adapter == null) throw new RuntimeException(adaptedFrom.getClass().getSimpleName()
				+ " must be annotated by "+Adapter.class.getSimpleName());

		for (Class<? extends BlinckDataAdapter> adapterClass : adapter.value()) {
			T result = adapt(adaptedFrom, adapterClass);
			if (result != null) return result;
		}

		return null;
	}


	@SuppressWarnings("unchecked")
	public static <T> T adapt(final Object adaptedFrom,
							  final Class<? extends BlinckDataAdapter> adapterClass) {

		final BlinckDataAdapter adapter;
		try {
			adapter = adapterClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		for (Method method : adapterClass.getDeclaredMethods()) {
			Class<?>[] argTypes = method.getParameterTypes();
			if (argTypes.length == 0 || argTypes.length > 1)
				throw new RuntimeException();
			Class<?> argType = argTypes[0];

			if (argType == adaptedFrom.getClass()) try {
				return (T) method.invoke(adapter, adaptedFrom);
			} catch (Exception ignored) {
				return null;
			}
		}

		return null;
	}
}
