package net.henryco.blinck.just;

import net.henryco.sqlightning.BuildConfig;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

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


	@Test
	public void ageCalculateTest() {

		LocalDate birthdate = new LocalDate(1995, 6, 26);
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(birthdate, now);

		Date birth = birthdate.toDate();
		System.out.println("b: "+birth);
		long time = birth.getTime();
		System.out.println("t: "+time);

		Date newOne = new Date(time);
		LocalDate newBirth = new LocalDate(newOne);
		Years newAge = Years.yearsBetween(newBirth, now);

		System.out.println("one: "+age);
		System.out.println("two: "+newAge);
		System.out.println(newAge.getYears());
	}

}