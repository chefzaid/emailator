package com.emailator.bulksender.testutils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RegexMatcher extends BaseMatcher<String> {
	private final String regex;

	public RegexMatcher(String regex) {
		this.regex = regex;
	}

	public boolean matches(Object o) {
		return ((String) o).matches(regex);

	}

	public void describeTo(Description description) {
		description.appendText("Matches regex = ");
	}

	public static RegexMatcher matches(String regex) {
		return new RegexMatcher(regex);
	}
}