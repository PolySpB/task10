package task10;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Программа с консоли построчно считывает код метода doWork().
 * Подменяем реализацию метода doWork() в классе SomeClass, перекомпилируем.
 * При помощи пользовательского CustomClassLoader загружаем обновленную реализацию SomeClass.
 */

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String someClassCode = CustomReader.readFromClass();
        String methodCode = CustomReader.readFromConsole();
        someClassCode = someClassCode.substring(0, someClassCode.indexOf("doWork()") + 10) + methodCode + "}\n}\n";

        Worker worker = new SomeClass();
        System.out.println("Before:");
        worker.doWork();

        // Перезаписываем оригинальный класс на новый, с подмененным методом
        File root = new File("src");
        File sourceFile = new File(root, "/task10/SomeClass.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), someClassCode.getBytes(StandardCharsets.UTF_8));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Вызываем собстенный загрузчик CustomClassLoader
        ClassLoader customClassLoader = new CustomClassLoader();
        Class<?> loadedClass = customClassLoader.loadClass("SomeClass");
        worker = (Worker) loadedClass.newInstance();
        System.out.println("After:");
        worker.doWork();
    }
}
