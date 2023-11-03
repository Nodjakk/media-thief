package works.akus.mediathief.stealer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class ManagerProcessor {

    public List<PlatformBase> process() {
        List<PlatformBase> objects = new ArrayList<>();

        Reflections reflections = new Reflections("works.akus");
        Set<Class<? extends PlatformBase>> classes = reflections.getSubTypesOf(PlatformBase.class);

        for (Class<? extends PlatformBase> clazz : classes) {
            if (clazz.isAnnotationPresent(PlatformInitializer.class)) {
                PlatformInitializer annotation = clazz.getAnnotation(PlatformInitializer.class);
                String name = annotation.name();
                String[] values = annotation.urls();

                try {
                    Constructor<? extends PlatformBase> constructor = clazz.getDeclaredConstructor();
                    PlatformBase instance = constructor.newInstance();

                    objects.add(instance);

                    System.out.println("Added object to manager: " + instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return objects;
    }

}
