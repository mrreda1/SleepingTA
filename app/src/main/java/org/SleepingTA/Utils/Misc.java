package org.SleepingTA.Utils;

import java.util.Map;

final public class Misc {
    public static Map<String, String> icons = Misc.icons();

    private static Map<String, String> icons() {
        return Map.of(
                "greenChair", "/images/chair-green.png",
                "redChair", "/images/chair-red.png",
                "redTa", "/images/ta-red.png",
                "greenTa", "/images/ta-green.png",
                "student", "/images/student.png");
    }

    public static boolean isInteger(String str) {
        return str.matches("^(?!0)\\d+$");
    }
}
