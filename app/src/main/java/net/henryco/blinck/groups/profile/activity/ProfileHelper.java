package net.henryco.blinck.groups.profile.activity;

import org.joda.time.LocalDate;

/**
 * Created by HenryCo on 01/10/17.
 */
final class ProfileHelper {

	static final String GENDER_MALE = "male";
	static final String GENDER_FEMALE = "female";


	static String createTitle(String name, int age) {
		return name + ",  " + age;
	}

	static String createName(String first, String second, String last) {

		String s = second == null ? "" : (" " + second);
		String l = last == null ? "" : (" " + last);
		return first + s + l;
	}

	static String createGender(String gender) {

		if (gender == null) return "";
		if (gender.equalsIgnoreCase(GENDER_MALE)) return "Male";
		if (gender.equalsIgnoreCase(GENDER_FEMALE)) return "Female";

		return "Unknown";
	}

	static String createBirthday(long birthday) {
		return new LocalDate(birthday).toString().replace("-", "/");
	}

	static long getBirthday(String birthday) {
		return LocalDate.parse(birthday.replace("/", "-")).toDate().getTime();
	}


	static String safetyString(String string) {
		return string == null ? "" : string;
	}
}
