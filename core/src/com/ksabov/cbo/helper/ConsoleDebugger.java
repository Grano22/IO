package com.ksabov.cbo.helper;

public class ConsoleDebugger {
    public static void clear() {
        try {
            final String os = System.getProperty("os.name");

            String classPath = System.getProperty("java.class.path");

            if (os.contains("Windows") && !classPath.contains("idea_rt.jar")) {
                //Runtime.getRuntime().exec("cls");
                //Runtime.getRuntime().exec("Clear-Host");
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                return;
            }


            Runtime.getRuntime().exec("clear");
        } catch (final Exception e) {
            //System.out.println(e.getMessage());
        }
    }
}
