package ua.com.alevel.hw2.context;

import org.reflections.Reflections;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Context {
    private Set<Class<?>> cache;
    private Reflections reflections;

    public Context() {
        cache = new HashSet<>();
        reflections = new Reflections("ua.com.alevel.hw2");
    }

    public void createSingletonClasses() {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Singleton.class);
        addToCacheClassesWithEmptyConstructors(classes);
        addToCacheOtherSingletonClasses(classes);
    }

    private void addToCacheClassesWithEmptyConstructors(Set<Class<?>> classes) {
        classes.forEach(clazz -> {
            Arrays.stream(clazz.getDeclaredConstructors())
                    .forEach(constructor -> {
                        if (constructor.isAnnotationPresent(Autowired.class) && constructor.getParameterCount() == 0) {
                            try {
                                constructor.setAccessible(true);
                                Object object = constructor.newInstance();
                                Field field = clazz.getDeclaredField("instance");
                                field.setAccessible(true);
                                field.set(null, object);
                                cache.add(clazz);
                            }
                            catch (InvocationTargetException | InstantiationException
                                    | IllegalAccessException | NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        });
    }

    private void addToCacheOtherSingletonClasses(Set<Class<?>> classes) {
        classes.forEach(clazz -> {
            Arrays.stream(clazz.getDeclaredConstructors())
                    .forEach(constructor -> {
                        if (constructor.isAnnotationPresent(Autowired.class) && constructor.getParameterCount() == 1) {
                            Class<?>[] params = constructor.getParameterTypes();

                            Optional<Class<?>> optional = cache.stream()
                                    .filter(element -> params[0].equals(element))
                                    .findFirst();

                            if (optional.isPresent()) {
                                try {
                                    Field classDB = optional.get().getDeclaredField("instance");
                                    classDB.setAccessible(true);

                                    constructor.setAccessible(true);
                                    Object object = constructor.newInstance(classDB.get(classDB.getType()));

                                    Field field = clazz.getDeclaredField("instance");
                                    field.setAccessible(true);
                                    field.set(null, object);
                                    cache.add(clazz);
                                }
                                catch (InvocationTargetException | InstantiationException
                                        | IllegalAccessException | NoSuchFieldException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        });
    }
}
