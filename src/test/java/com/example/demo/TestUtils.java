package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
	public static void injectObject(Object target, String fieldName, Object toInject)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		boolean wasPrivate = false;

		Field f = target.getClass().getDeclaredField(fieldName);

		// if the target is private, make it public (accessible)
		if (!f.canAccess(f)) {
			f.setAccessible(true);
			wasPrivate = true;
		}
		// inject it
		f.set(target, toInject);

		// set to private again
		if (wasPrivate) {
			wasPrivate = false;
		}

	}

}
