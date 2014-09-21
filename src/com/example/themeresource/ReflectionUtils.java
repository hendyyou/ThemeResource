package com.example.themeresource;
import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionUtils {
    private final static String TAG = ReflectionUtils.class.getSimpleName();

    public static Object callPrivateMethod(Object obj, String methodName, Object... params) {
        try {
            Class[] paramsClass = null;
            if (params != null && params.length > 0) {
                paramsClass = new Class[params.length];
                for (int i = 0; i < paramsClass.length; i++) {
                    paramsClass[i] = params[0].getClass();
                }
            }
            Method method = obj.getClass().getDeclaredMethod(methodName, paramsClass);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object callPrivateMethod(Class whichClass, Object obj, String methodName, Object... params) {
        try {
            Class[] paramsClass = null;
            if (params != null && params.length > 0) {
                paramsClass = new Class[params.length];
                for (int i = 0; i < paramsClass.length; i++) {
                    paramsClass[i] = params[0].getClass();
                }
            }
            Method method = whichClass.getDeclaredMethod(methodName, paramsClass);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object callPrivateMethod(Class whichClass, Object obj, String methodName, Class[] paramsClass, Object... params) {
        try {
            Method method = whichClass.getDeclaredMethod(methodName, paramsClass);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean modifyFieldValue(Class classType, String filedName, Object object, Object filedValue) {
        try {
            Field field = classType.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(object, filedValue);
            return true;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "NoSUchFieldException", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException", e);
        }
        return false;
    }

    public static Object getPrivateField(Object obj, String fieldName) {
        return getPrivateField(obj, fieldName, obj.getClass());
    }

    public static Object getPrivateField(Object obj, String fieldName, Class clazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean copyAllActivityFields(Activity src, Activity target) {
        if (src == null || target == null) {
            return false;
        }

        Class<?> clsType = Activity.class;
        while (clsType != null) {
            Field[] fields = clsType.getDeclaredFields();
            for (Field fid : fields) {
                try {
                    fid.setAccessible(true);
                    Object ref = fid.get(src);
                    fid.set(target, ref);
                } catch (Exception e) {
                     e.printStackTrace();
                }
            }
            clsType = clsType.getSuperclass();
        }

        return true;
    }

    public static boolean copyAllInstFields(Instrumentation src, Instrumentation target) {
        if (src == null || target == null) {
            return false;
        }

        Class<?> clsType = Instrumentation.class;
        while (clsType != null) {
            Field[] fields = clsType.getDeclaredFields();
            for (Field fid : fields) {
                try {
                    fid.setAccessible(true);
                    Object ref = fid.get(src);
                    fid.set(target, ref);
                } catch (Exception e) {
                     e.printStackTrace();
                }
            }
            clsType = clsType.getSuperclass();
        }

        return true;
    }


    public static boolean copyAllFields(Class<?> clsBase, Object src, Object target) {
        if (src == null || target == null) {
            return false;
        }

        Class<?> clsType = clsBase;
        while (clsType != null) {
            Field[] fields = clsType.getDeclaredFields();
            for (Field fid : fields) {
                try {
                    fid.setAccessible(true);
                    Object ref = fid.get(src);
                    fid.set(target, ref);
                } catch (Exception e) {
                    // Debug.logD("setField error: " + fid.getName() + " @ " + clsType.getSimpleName());
                    // e.printStackTrace();
                }
            }
            clsType = clsType.getSuperclass();
        }

        return true;
    }

    public static Method findMethod(Class<?> cls, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            Method m = cls.getMethod(name, parameterTypes);
            if (m != null) {
                return m;
            }
        } catch (Exception e) {
            // ignore this error & pass down
        }

        Class<?> clsType = cls;
        while (clsType != null) {
            try {
                Method m = clsType.getDeclaredMethod(name, parameterTypes);
                m.setAccessible(true);
                return m;
            } catch (NoSuchMethodException e) {
            }
            clsType = clsType.getSuperclass();
        }
        throw new NoSuchMethodException();
    }

    public static Method findMethodNoThrow(Class<?> cls, String name, Class<?>... parameterTypes) {
        Method m = null;
        try {
            m = findMethod(cls, name, parameterTypes);
            m.setAccessible(true);
        } catch (NoSuchMethodException e) {
        }
        return m;
    }

    public static Field findField(Class<?> cls, String name) throws NoSuchFieldException {
        try {
            Field m = cls.getField(name);
            if (m != null) {
                return m;
            }
        } catch (Exception e) {
            // ignore this error & pass down
        }

        Class<?> clsType = cls;
        while (clsType != null) {
            try {
                Field m = clsType.getDeclaredField(name);
                m.setAccessible(true);
                return m;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            clsType = clsType.getSuperclass();
        }
        throw new NoSuchFieldException();
    }

    public static Field findFieldNoThrow(Class<?> cls, String name) {
        Field f = null;
        try {
            f = findField(cls, name);
        } catch (NoSuchFieldException e) {
        }
        return f;
    }

    public static Class<?> loadClassNoThrow(ClassLoader loader, String name) {
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
