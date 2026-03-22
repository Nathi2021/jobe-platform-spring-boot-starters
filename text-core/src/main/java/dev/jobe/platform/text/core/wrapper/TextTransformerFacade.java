package dev.jobe.platform.text.core.wrapper;

import dev.jobe.platform.text.core.annotation.Lower;
import dev.jobe.platform.text.core.annotation.Sentence;
import dev.jobe.platform.text.core.annotation.Title;
import dev.jobe.platform.text.core.annotation.Upper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

public abstract class TextTransformerFacade extends AbstractTextTransformer {

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

    protected Object transformCollection(final Collection<?> collection) {
        List<Object> transformed = new ArrayList<>();
        for (Object o : collection) {
            transformed.add(this.transform(o));
        }
        return transformed;
    }

    protected Object transformMap(final Map<?, ?> map) {
        Map<Object, Object> transformed = new HashMap<>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = this.transform(entry.getKey());
            Object value = this.transform(entry.getValue());
            transformed.put(key, value);
        }
        return transformed;
    }

    protected Object transformArray(final Object array) {
        int len = Array.getLength(array);
        List<Object> transformed = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            transformed.add(this.transformArray(Array.get(array, i)));
        }
        return transformed;
    }

    protected void transformObject(final Object o) throws IllegalAccessException {
        if (o == null || isPrimitiveOrWrapper(o.getClass())) return;
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(o);
            if (value instanceof String s) {
                if (field.isAnnotationPresent(Title.class)) {
                    field.set(o, this.asTitleCase(s));
                } else if (field.isAnnotationPresent(Sentence.class)) {
                    field.set(o, this.asSentenceCase(s));
                } else if (field.isAnnotationPresent(Lower.class)) {
                    field.set(o, this.asLowerCase(s));
                } else if (field.isAnnotationPresent(Upper.class)) {
                    field.set(o, this.asUpperCase(s));
                }
            } else if (value != null && !isPrimitiveOrWrapper(value.getClass())) {
                transformObject(value);
            }
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
