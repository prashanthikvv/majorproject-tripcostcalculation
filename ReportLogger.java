package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import tripCostReport.MyListener;

public class ReportLogger {

    private static String time() {

        return new SimpleDateFormat("hh:mm:ss a").format(new Date());
    }

    public static void info(String message) {

        MyListener.test.info(time() + " : " + message);
    }

    public static void pass(String message) {

        MyListener.test.pass(time() + " : " + message);
    }

    public static void fail(String message) {

        MyListener.test.fail(time() + " : " + message);
    }
}