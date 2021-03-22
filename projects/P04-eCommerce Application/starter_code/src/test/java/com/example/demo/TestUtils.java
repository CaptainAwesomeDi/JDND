package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldname, Object toInject)
    {
        boolean wasPrivate = false;

        try {
            Field field = target.getClass().getDeclaredField(fieldname);

            if(!field.canAccess(target))
            {
                field.setAccessible(true);
                wasPrivate = true;
            }

            field.set(target, toInject);

            if(wasPrivate)
            {
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
