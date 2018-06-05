package com.example.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class DtoUtil {
	public static boolean hasAnyNonCollectionFieldChanged(Object dto) {
		Class<? extends Object> c = dto.getClass();
		Field[] fields = c.getDeclaredFields();
		return Arrays.stream(fields).filter(f -> Modifier.isPublic(f.getModifiers()))
				.filter(f -> f.getName().matches("^has.+Changed")).filter(f -> f.getType().equals(boolean.class))
				.anyMatch(f -> {
					try {
						return f.getBoolean(dto);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				});
	}
}
