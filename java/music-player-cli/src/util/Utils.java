package util;

import java.util.Scanner;

public class Utils {
    
    public static void clearscreen() {
        System.out.println("\033[2J");
    }

    public static int generateId() {
        return 0;
    }

    public static String readLine(Scanner in) {
        while (!in.hasNextLine())
            System.exit(0);
        return in.nextLine().trim();
    }

    public static int readInt(String prompt, int min, int max, Runnable onError) {
        while (true) {
            try {
                int value = Integer.parseInt(prompt);
                if (value < min || value > max)
                    throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                onError.run();
            }
        }
    }
}
