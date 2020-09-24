package task10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Пользовательский ClassLoader, загружающий SomeClass в режиме Runtime.
 */

public class CustomClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ("SomeClass".equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if("SomeClass".equals(name)){
            try{
                byte[] bytes = Files.readAllBytes(Paths.get("src/task10/SomeClass.class"));
                return defineClass("task10." + name, bytes,0, bytes.length);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}
