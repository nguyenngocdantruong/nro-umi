package com.girlkun.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.util.regex.Pattern;


public class Logger {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String BLUE_BRIGHT = "\033[0;94m";
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    public static final String CYAN_BRIGHT = "\033[0;96m";
    public static final String WHITE_BRIGHT = "\033[0;97m";

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";

    public static final boolean DEBUG = true;
    public static String getLocation() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // 0 = Thread.getStackTrace
        // 1 = Logger.getLocation
        // 2 = Logger.debug
        // 3 = hàm gọi Logger.debug (cần lấy)
        if (stack.length > 3) {
            StackTraceElement e = stack[3];
            return e.getClassName() + "." + e.getMethodName() + ":" + e.getLineNumber();
        }
        return "UnknownLocation";
    }



    
    // Flag to show info
    public static final boolean SHOW_INFO_BOSS = false;

    
    
    
    
    
    
    private static final Pattern DIACRITICS_PATTERN =
            Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String removeVietnameseAccents(String input) {
        if (input == null) return null;

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = DIACRITICS_PATTERN.matcher(normalized).replaceAll("");
        normalized = normalized.replace('đ', 'd').replace('Đ', 'D');
        return normalized;
    }


    public static void debug(String text){
        if(DEBUG){
            if(!text.contains("\n")){
                text = text.concat("\n");
            }
            success(getLocation() + " -> " +text);
        }
    }
    
    
    public static void debug(String text, boolean flag){
        if(!flag) return;
        success(text);
    }
    
    // ======================
    //  LOG FUNCTIONS (AUTO REMOVE ACCENT)
    // ======================
    public static void log(String text) {
        System.out.println(removeVietnameseAccents(text));
    }

    public static void log(String color, String text){
        System.out.print(color + removeVietnameseAccents(text) + RESET);
    }

    public static void success(String text) {
        System.out.print(GREEN + removeVietnameseAccents(text) + RESET);
    }

    public static void warning(String text) {
        System.out.print(BLUE + removeVietnameseAccents(text) + RESET);
    }

    public static void error(String text) {
        System.out.print(BLUE + removeVietnameseAccents(text) + RESET);
    }


    // ======================
    //  EXCEPTION LOGGER (GIỮ NGUYÊN STACKTRACE)
    // ======================
    public static void logException(Class clazz, Exception ex, String... log) {
        try {
            if(log != null && log.length > 0){
                log(PURPLE, removeVietnameseAccents(log[0]) + "\n");
            }
            StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
            String nameMethod = stackTraceElements[1].getMethodName();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String detail = sw.toString();
            String[] arr = detail.split("\n");

            Logger.warning("Co loi tai class: ");
            Logger.error(clazz.getName());
            Logger.warning(" - tai phuong thuc: ");
            Logger.error(nameMethod + "\n");
            Logger.warning("Chi tiet loi:\n");

            for (String str : arr) {
                Logger.error(str + "\n");
            }

            Logger.log("--------------------------------------------------------\n");

        } catch (Exception e) {}
    }
}
