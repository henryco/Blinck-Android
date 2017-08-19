package net.henryco.blinck.retro;

import junit.framework.Assert;
import net.henryco.blinck.util.retro.RetroTemplate;
import net.henryco.blinck.util.retro.RetroURL;
import net.henryco.sqlightning.BuildConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import retrofit2.converter.gson.GsonConverterFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "app/src/main/AndroidManifest.xml")

public class RetroTemplateTest {


	@RetroURL("http://127.0.0.1:8060")
	private interface RetroServiceAnnotated {}

	private interface RetroServiceNotAnnotated {}


	@Test
	public void creationTest1() {

		RetroServiceAnnotated retroService = new RetroTemplate().create(RetroServiceAnnotated.class);
		Assert.assertNotNull(retroService);
	}

	@Test
	public void creationTest2() {

		RuntimeException exception = null;
		try {
			new RetroTemplate().create(RetroServiceNotAnnotated.class);
		} catch (RuntimeException e) {exception = e;}

		Assert.assertNotNull(exception);
	}

	@Test
	public void creationTest3() {
		RetroServiceNotAnnotated service =
				new RetroTemplate().setBaseURL("http://127.0.0.1:8080")
						.create(RetroServiceNotAnnotated.class);

		Assert.assertNotNull(service);
	}


	@Test
	public void creationTest4() {

		RetroTemplate retroTemplate = new RetroTemplate();

		RetroServiceAnnotated one = retroTemplate.create(RetroServiceAnnotated.class);
		RetroServiceAnnotated two = retroTemplate.setConverterFactory(null).create(RetroServiceAnnotated.class);
		RetroServiceAnnotated three = retroTemplate.setConverterFactory(GsonConverterFactory.create()).create(RetroServiceAnnotated.class);

		Assert.assertNotNull(one);
		Assert.assertNotNull(two);
		Assert.assertNotNull(three);

		org.junit.Assert.assertNotEquals(one, two);
		org.junit.Assert.assertNotEquals(one, three);
		org.junit.Assert.assertNotEquals(two, three);

	}


	@Test
	public void urlValueTest1() {

		RetroTemplate retroTemplate = new RetroTemplate();
		retroTemplate.create(RetroServiceAnnotated.class);

		Assert.assertEquals("http://127.0.0.1:8060", retroTemplate.testBaseUrlValue);
	}

	@Test
	public void urlValueTest2() {

		RetroTemplate retroTemplate = new RetroTemplate();
		retroTemplate.setBaseURL("http://127.0.0.1:3333").create(RetroServiceAnnotated.class);

		Assert.assertEquals("http://127.0.0.1:3333", retroTemplate.testBaseUrlValue);
	}

	@Test
	public void urlValueTest3() {
		RetroTemplate retroTemplate = new RetroTemplate();
		retroTemplate.setBaseURL("http://127.0.0.1:3333").create(RetroServiceNotAnnotated.class);

		Assert.assertEquals("http://127.0.0.1:3333", retroTemplate.testBaseUrlValue);
	}
}
