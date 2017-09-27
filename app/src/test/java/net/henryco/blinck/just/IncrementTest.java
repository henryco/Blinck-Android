package net.henryco.blinck.just;

import net.henryco.sqlightning.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by HenryCo on 27/09/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class IncrementTest {


	@Test
	public void justTest() {

		int i = 0;
		if (i ++ < 1) System.out.println("wow1");

		i = 0;
		if (++i < 1) System.out.println("wow2");

		i = 0;
		if (1 > i++)  System.out.println("wow3");

		i = 0;
		if (1 > ++i)  System.out.println("wow4");

	}

}