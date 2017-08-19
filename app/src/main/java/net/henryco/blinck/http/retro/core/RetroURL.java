package net.henryco.blinck.http.retro.core;

import java.lang.annotation.*;

/**
 * Created by HenryCo on 19/08/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RetroURL {

	String value();
}
