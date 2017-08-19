package net.henryco.blinck.util.function;

/**
 * Created by HenryCo on 20/08/17.
 */

public interface BlinckTriFunction<RETURN, PARAM1, PARAM2, PARAM3> {
	RETURN apply(PARAM1 param1, PARAM2 param2, PARAM3 param3);
}
