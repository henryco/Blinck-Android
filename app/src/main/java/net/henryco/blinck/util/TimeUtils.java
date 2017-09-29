package net.henryco.blinck.util;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.Date;

/**
 * Created by HenryCo on 29/09/17.
 */
public final class TimeUtils {




	public static Integer calculateAgeFromBirthDate(Date birthDate) {

		final LocalDate birth = new LocalDate(birthDate);
		return Years.yearsBetween(birth, new LocalDate()).getYears();
	}

	public static Integer calculateAgeFromBirthDate(long birthDate) {
		return calculateAgeFromBirthDate(new Date(birthDate));
	}
	
}