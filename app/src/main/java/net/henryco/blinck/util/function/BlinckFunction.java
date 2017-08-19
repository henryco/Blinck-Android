package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 20/08/17.
 */

public interface BlinckFunction<RETURN, PARAM> {
	RETURN apply(PARAM param);
}
