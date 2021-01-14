package com.maids.cc.store.management.backend.logger;

import java.io.*;
import java.util.Calendar;

public class Logger {
    private static String filePath = "src//main//resources//log.txt";

    public static void log(String tableName, String methodName, String newValue){
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;

        try {
            File file = new File(filePath);
            fileWriter = new FileWriter(file, true);
            printWriter = new PrintWriter(new BufferedWriter(fileWriter));

            printWriter.println("Table name : " + tableName);
            printWriter.println("Method name : " + methodName);
            printWriter.println("Values : " + newValue);
            printWriter.println("DateTime : " + Calendar.getInstance().getTime());
            printWriter.println("------------------------------------");

            printWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.close();
                printWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
