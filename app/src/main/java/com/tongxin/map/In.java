package com.tongxin.map;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
public final class In {
    private Scanner scanner;
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = Locale.PRC;
    public In(String s) {
        try {
            File file = new File(s);
            if (file.exists()) {
                scanner = new Scanner(file, CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + s);
        }
    }
    public int readInt() {
        return scanner.nextInt();
    }
    public double readDouble() {
        return scanner.nextDouble();
    }
}
