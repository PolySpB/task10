package task10;

import java.io.*;

/**
 * Класс, содержащий все ридеры, используемые в программе
 */
public class CustomReader {
    /**
     * Считывание новой реализации метода doWork() с консоли
     * @return строка, содержащая новую реализацию метода doWork()
     */
    static String readFromConsole() {
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

    /**
     * Считывание кода класса SomeClass
     * @return строка, содержащая код класса SomeClass
     */
    static String readFromClass() {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/task10/SomeClass.java"))) {
            String line = br.readLine();
            while (br.ready()) {
                result += line;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
