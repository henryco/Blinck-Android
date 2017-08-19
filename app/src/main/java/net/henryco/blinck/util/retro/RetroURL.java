package net.henryco.blinck.util.retro;

import java.lang.annotation.*;

/**
 * Created by HenryCo on 19/08/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RetroURL {

	String value() default "http://127.0.0.1:8080";
}
