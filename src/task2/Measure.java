package task2;

import java.util.ArrayList;
import java.util.List;

public class Measure {
    public static int low;
    public static int high;
    private static List<String> timeAsync = new ArrayList<>();

    // Методи доступу
    public static void addTime(String time) {
        timeAsync.add(time);
    }

    public static List<String> getTimeAsync() {
        return new ArrayList<>(timeAsync); // Повертаємо копію для захисту
    }

}
