package net.henryco.blinck.util.adapter;

import java.lang.annotation.*;

/**
 * Created by HenryCo on 19/08/17.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Adapter {
	Class<? extends BlinckDataAdapter>[] value();
}
