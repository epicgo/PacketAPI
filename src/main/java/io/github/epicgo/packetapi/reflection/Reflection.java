package io.github.epicgo.packetapi.reflection;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class that simplifies reflection in Bukkit plugins
 *
 * @author Epicgo
 * @version 1.0
 */
public class Reflection {

    /**
     * The build version the server is running
     *
     * @return the version of build R- of the server
     */
    public static String getBuildVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    /**
     * Returns a specified class from its full name
     *
     * @param clazz the class name
     * @return the looked up class
     */
    @SneakyThrows
    public static Class<?> getClass(final String clazz) {
        return Class.forName(clazz);
    }

    /**
     * Returns a class in the net.minecraft.server.VERSION.* package
     *
     * @param clazz the name of the class, excluding the package
     * @return the looked up nms class
     */
    public static Class<?> getNMSClass(final String clazz) {
        return getClass("net.minecraft.server." + getBuildVersion() + "." + clazz);
    }

    /**
     * Returns a class in the org.bukkit.craftbukkit.VERSION.* package
     *
     * @param clazz the name of the class, excluding the package
     * @return the looked up obc class
     */
    public static Class<?> getOBCClass(final String clazz) {
        return getClass("org.bukkit.craftbukkit." + getBuildVersion() + "." + clazz);
    }

    /**
     * Returns a field object for a specific field name.
     *
     * @param target    the target class to start with.
     * @param fieldName the field name.
     * @return the object of the field.
     * @throws NoSuchFieldException   If the field is not found.
     * @throws IllegalAccessException If the field is not accessed.
     */
    public static Object getField(final Object target, final String fieldName) {
        try {
            final Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);

            return field.get(target);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to find " + fieldName + " field");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the field " + fieldName);
        }
    }

    /**
     * Sets an object field to the field with a specific field name.
     *
     * @param target      the target class to start with.
     * @param fieldName   the field name.
     * @param objectValue the object set the field.
     * @throws NoSuchFieldException   If the field is not found.
     * @throws IllegalAccessException If the field is not accessed.
     */
    public static void setField(final Object target, final String fieldName, final Object objectValue) {
        try {
            final Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);

            field.set(target, objectValue);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to find " + fieldName + " field");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the field " + fieldName);
        }
    }

    /**
     * Returns a declared field object for a specific field name.
     *
     * @param target    the target class to start with.
     * @param fieldName the field name.
     * @return the object of the field.
     * @throws NoSuchFieldException   If the field is not found.
     * @throws IllegalAccessException If the field is not accessed.
     */
    public static Object getDeclaredField(final Object target, final String fieldName) {
        try {
            final Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(target);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to find " + fieldName + " field");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the field " + fieldName);
        }
    }

    /**
     * Sets an object field to the declared field with a specific field name.
     *
     * @param target      the target class to start with.
     * @param fieldName   the field name.
     * @param objectValue the object set the declared field.
     * @throws NoSuchFieldException   If the field is not found.
     * @throws IllegalAccessException If the field is not accessed.
     */
    public static void setDeclaredField(final Object target, final String fieldName, final Object objectValue) {
        try {
            final Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            field.set(target, objectValue);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to find " + fieldName + " field");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the field " + fieldName);
        }
    }

    /**
     * Returns a specified method of the given name from its complete parameters.
     *
     * @param target     the target class to start with.
     * @param methodName the method name.
     * @param params     the expected parameters.
     * @return An object that invokes this specific method.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    public static Object getMethod(final Object target, final String methodName, Object... params) {
        final List<Method> methodList = Arrays.stream(target.getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(m -> m.getParameters().length == params.length)
                .collect(Collectors.toList());

        try {
            return methodList.get(0).invoke(target, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the method as the target invocation");
        }
    }

    /**
     * Returns a specified method of the given name from its complete parameters.
     *
     * @param target     the target class to start with.
     * @param methodName the method name.
     * @param returnType the expected return type.
     * @param params     the expected parameters.
     * @return An object that invokes this specific method.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    public static Object getMethod(final Object target, final String methodName, final Class<?> returnType, Object... params) {
        final List<Method> methodList = Arrays.stream(target.getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(m -> m.getParameters().length == params.length)
                .filter(method -> method.getReturnType().equals(returnType))
                .collect(Collectors.toList());

        try {
            return methodList.get(0).invoke(target, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the method as the target invocation");
        }
    }

    /**
     * Returns a specified declared method of the given name from its complete parameters.
     *
     * @param target     the target class to start with.
     * @param methodName the method name.
     * @param params     the expected parameters.
     * @return An object that invokes this specific declared method.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    public static Object getDeclaredMethod(final Object target, final String methodName, Object... params) {
        final List<Method> methodList = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(m -> m.getParameters().length == params.length)
                .collect(Collectors.toList());

        try {
            return methodList.get(0).invoke(target, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the method as the target invocation");
        }
    }

    /**
     * Returns a specified declared method of the given name from its complete parameters.
     *
     * @param target     the target class to start with.
     * @param methodName the method name.
     * @param returnType the expected return type.
     * @param params     the expected parameters.
     * @return An object that invokes this specific declared method.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    public static Object getDeclaredMethod(final Object target, final String methodName, final Class<?> returnType, Object... params) {
        final List<Method> methodList = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getReturnType().equals(returnType))
                .filter(m -> m.getParameters().length == params.length)
                .collect(Collectors.toList());

        try {
            return methodList.get(0).invoke(target, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or method");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the method as the target invocation");
        }
    }

    /**
     * Returns a specified constructor from its complete parameters.
     *
     * @param target the target class to start with.
     * @param params the expected parameters
     * @return An object that invokes this constructor.
     * @throws InstantiationException If an instance cannot be initialized.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    @SneakyThrows
    public static Object getConstructor(final Class<?> target, final Object... params) {
        final List<Constructor<?>> constructorList = Arrays.stream(target.getConstructors())
                .filter(constructor -> constructor.getParameters().length == params.length)
                .collect(Collectors.toList());

        try {
            return constructorList.get(0).newInstance(params);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to create an instance of a class using new instance");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or constructor");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the constructor as the target invocation");
        }
    }

    /**
     * Returns a specified declared constructor from its complete parameters.
     *
     * @param target the target class to start with.
     * @param params the expected parameters
     * @return An object that invokes this declared constructor.
     * @throws InstantiationException If an instance cannot be initialized.
     * @throws IllegalAccessException If a costructor cannot be set or obtained or invoked.
     * @throws InstantiationException If this invocation cannot be found.
     */
    public static Object getDeclaredConstructor(final Class<?> target, final Object... params) {
        final List<Constructor<?>> constructorList = Arrays.stream(target.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameters().length == params.length)
                .collect(Collectors.toList());

        try {
            return constructorList.get(0).newInstance(params);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to create an instance of a class using new instance");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create an instance in a form other a set or get or constructor");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create the constructor as the target invocation");
        }
    }
}