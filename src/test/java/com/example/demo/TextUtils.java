package com.example.demo;

import java.lang.reflect.Field;

public class TextUtils {
	public static void injectObject(Object target, String fieldName, Object toInject) throws NoSuchFieldException, SecurityException {
		boolean wasPrivate = false; 
		
		Field f = target.getClass().getDeclaredField(fieldName);
	}

}
