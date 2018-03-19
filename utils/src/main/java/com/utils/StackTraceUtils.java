package com.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtils {
    private Throwable e;

    static public String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    static public String getStackTrace2(Throwable e) {
        StackTraceUtils stackTraceUtils = new StackTraceUtils(e);
        return stackTraceUtils.getCompactStackTrace();
    }

    public StackTraceUtils(Throwable e) {
        this.e = e;
    }

    public String getCompactStackTrace() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.println(e);
        StackTraceElement[] trace = e.getStackTrace();
        for (int i=0; i < Math.min(trace.length, 5); i++)
            pw.println("\tat " + trace[i]);

        callPrintCausedStackTrace(pw, trace);

        return sw.toString();
    }

    public void printCausedStackTrace(PrintWriter pw, StackTraceElement[] causedTrace) {
        StackTraceElement[] trace = e.getStackTrace();
        int m = trace.length-1, n = causedTrace.length-1;
        while (m >= 0 && n >=0 && trace[m].equals(causedTrace[n])) {
            m--; n--;
        }
        int framesInCommon = trace.length - 1 - m;

        pw.println("Caused by: " + e);
        for (int i=0; i <= Math.min(m, 4); i++)
            pw.println("\tat " + trace[i]);
        if (framesInCommon != 0)
            pw.println("\t... " + framesInCommon + " more");

        callPrintCausedStackTrace(pw, trace);
    }

    private void callPrintCausedStackTrace(PrintWriter pw, StackTraceElement[] trace) {
        Throwable ourCause = e.getCause();
        if (ourCause != null) {
            StackTraceUtils st = new StackTraceUtils(ourCause);
            st.printCausedStackTrace(pw, trace);
        }
    }
}
