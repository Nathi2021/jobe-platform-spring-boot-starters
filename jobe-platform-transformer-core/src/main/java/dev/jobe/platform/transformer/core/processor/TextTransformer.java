package dev.jobe.platform.transformer.core.processor;

import dev.jobe.platform.transformer.core.annotation.Lower;
import dev.jobe.platform.transformer.core.annotation.Sentence;
import dev.jobe.platform.transformer.core.annotation.Title;
import dev.jobe.platform.transformer.core.annotation.Upper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

public class TextTransformer extends AbstractTextTransformer {

    public Object transform(final Object obj) {
        if (obj == null) return null;
        try {
            if (obj instanceof Collection<?> c) {
                return transformCollection(c);
            }  else if (obj instanceof Map<?, ?> m) {
                return transformMap(m);
            } else if (obj.getClass().isArray()) {
                return transformArray(obj);
            } else {
                transformObject(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to transform object", e);
        }
        return obj;
    }

    private Object transformCollection(final Collection<?> collection) {
        List<Object> transformed = new ArrayList<>();
        for (Object o : collection) {
            transformed.add(transform(o));
        }
        return transformed;
    }

    private Object transformMap(final Map<?, ?> map) {
        Map<Object, Object> transformed = new HashMap<>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = transform(entry.getKey());
            Object value = transform(entry.getValue());
            transformed.put(key, value);
        }
        return transformed;
    }

    private Object transformArray(final Object array) {
        int len = Array.getLength(array);
        List<Object> transformed = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            transformed.add(transform(Array.get(array, i)));
        }
        return transformed;
    }

//    private void transformObject(final Object obj) throws IllegalAccessException {
//        Field[] fields = obj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object value = field.get(obj);
//
//            if (value instanceof String str) {
//                String transformed;
//                if (field.isAnnotationPresent(Title.class)) {
//                    transformed = asTitle(str);
//                } else if (field.isAnnotationPresent(Sentence.class)) {
//                    transformed = asSentence(str);
//                } else if (field.isAnnotationPresent(Lower.class)) {
//                    transformed = asLower(str);
//                } else if (field.isAnnotationPresent(Upper.class)) {
//                    transformed = asUpper(str);
//                } else  {
//                    transformed = str;
//                }
//                field.set(obj, transformed);
//            }
//        }
//    }

    private void transformObject(final Object o) throws IllegalAccessException {
        if (o == null || isPrimitiveOrWrapper(o.getClass())) return;
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(o);
            if (value instanceof String s) {
                if (field.isAnnotationPresent(Title.class)) {
                    field.set(o, asTitle(s));
                } else if (field.isAnnotationPresent(Sentence.class)) {
                    field.set(o, asSentence(s));
                } else if (field.isAnnotationPresent(Lower.class)) {
                    field.set(o, asLower(s));
                } else if (field.isAnnotationPresent(Upper.class)) {
                    field.set(o, asUpper(s));
                }
            } else if (value != null && !isPrimitiveOrWrapper(value.getClass())) {
                transformObject(value);
            }

//            if (value instanceof String s) {
//                String transformed;
//                if (field.isAnnotationPresent(Title.class)) {
//                    transformed = asTitle(s);
//                } else if (field.isAnnotationPresent(Sentence.class)) {
//                    transformed = asSentence(s);
//                } else if (field.isAnnotationPresent(Lower.class)) {
//                    transformed = asLower(s);
//                } else if (field.isAnnotationPresent(Upper.class)) {
//                    transformed = asUpper(s);
//                } else  {
//                    transformed = s;
//                }
//                field.set(o, transformed);
//            } else if (value != null && !isPrimitiveOrWrapper(value.getClass())) {
//                transformObject(value);
//            }
        }
    }

    private boolean isPrimitiveOrWrapper(final Class<?> cls) {
        return cls.isPrimitive()
            || cls.isEnum()
            || cls == String.class
            || cls == Boolean.class
            || cls == Character.class
            || cls == Byte.class
            || cls == Short.class
            || cls == Integer.class
            || cls == Long.class
            || cls == Float.class
            || cls == Double.class
            || cls == BigInteger.class
            || cls == Void.class;
    }
}
