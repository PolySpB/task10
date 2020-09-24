package task10;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Программа с консоли построчно считывает код метода doWork().
 * Подменяем реализацию метода doWork() в классе SomeClass, перекомпилируем.
 * При помощи пользовательского CustomClassLoader загружаем обновленную реализацию SomeClass.
 */

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Path path = Paths.get("src/task10/SomeClass.java");
        String someClassCode = Files.readString(path, StandardCharsets.UTF_8);

        String methodCode = readFromConsole();
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

        System.out.println("After:");
        useCustomClassloader();
    }

    /**
     * Функция вызова пользовательского загрузчика CustomClassLoader
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void useCustomClassloader() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader customClassLoader = new CustomClassLoader();
        Class<?> loadedClass = customClassLoader.loadClass("SomeClass");
        Worker worker = (Worker) loadedClass.newInstance();
        worker.doWork();
    }

    /**
     * Считывание новой реализации метода doWork() с консоли
     * @return строка, содержащая новую реализацию метода doWork()
     */
    private static String readFromConsole() {
        String result = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line = br.readLine();
            while (!line.isEmpty()) {
                result += line;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
