package net.henryco.blinck.adapter;

import junit.framework.Assert;
import net.henryco.blinck.adapter.dto.ExampleDTO_One;
import net.henryco.blinck.adapter.dto.ExampleDTO_two;
import net.henryco.blinck.adapter.imp.ExampleAdapter;
import net.henryco.blinck.util.adapter.DataAdapter;
import net.henryco.sqlightning.BuildConfig;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by HenryCo on 19/08/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataAdapterTest {

	private ExampleDTO_One sourceOne;
	private ExampleDTO_two sourceTwo;


	@Before
	public void init() {
		sourceOne = new ExampleDTO_One("name1", "text1", 45f, new Integer(5));
		sourceTwo = new ExampleDTO_two("name2", "text2", 42f, new Float(10));
	}


	@Test
	public void adapterIdentityTest1() {

		ExampleDTO_two adapted = DataAdapter.adapt(sourceOne);

		Assert.assertNotNull(adapted);
		Assert.assertEquals(adapted.getName(), sourceOne.getSomeName());
		Assert.assertEquals(adapted.getText(), sourceOne.getSomeText());
		Assert.assertEquals(adapted.getValue(), sourceOne.getSomeValue());
		Assert.assertEquals(adapted.getObject(), sourceOne.getSomeObject());
	}


	@Test
	public void adapterIdentityTest2() {

		ExampleDTO_One adapted = DataAdapter.adapt(sourceTwo, ExampleAdapter.class);

		Assert.assertNotNull(adapted);
		Assert.assertEquals(adapted.getSomeName(), sourceTwo.getName());
		Assert.assertEquals(adapted.getSomeText(), sourceTwo.getText());
		Assert.assertEquals(adapted.getSomeValue(), sourceTwo.getValue());
		Assert.assertEquals(adapted.getSomeObject(), sourceTwo.getObject());
	}


	@Test
	public void noAdapterAnnotationTest1() {

		RuntimeException exception = null;
		try {
			DataAdapter.adapt(sourceTwo);
		} catch (RuntimeException e) {exception = e;}

		Assert.assertNotNull(exception);
	}

}