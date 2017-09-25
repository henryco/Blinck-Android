package net.henryco.blinck.util.reflect;

import java.lang.annotation.*;

/**
 * Created by HenryCo on 25/09/17.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoFind {

	int value();
}
