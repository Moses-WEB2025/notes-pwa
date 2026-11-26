package org.junit.internal;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import miui.cloud.CloudPushConstants;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/* JADX INFO: loaded from: classes6.dex */
public class TextListener extends RunListener {
    private final PrintStream writer;

    public TextListener(JUnitSystem jUnitSystem) {
        this(jUnitSystem.out());
    }

    public TextListener(PrintStream printStream) {
        this.writer = printStream;
    }

    @Override // org.junit.runner.notification.RunListener
    public void testRunFinished(Result result) {
        printHeader(result.getRunTime());
        printFailures(result);
        printFooter(result);
    }

    @Override // org.junit.runner.notification.RunListener
    public void testStarted(Description description) {
        this.writer.append('.');
    }

    @Override // org.junit.runner.notification.RunListener
    public void testFailure(Failure failure) {
        this.writer.append('E');
    }

    @Override // org.junit.runner.notification.RunListener
    public void testIgnored(Description description) {
        this.writer.append('I');
    }

    private PrintStream getWriter() {
        return this.writer;
    }

    protected void printHeader(long j) {
        getWriter().println();
        getWriter().println("Time: " + elapsedTimeAsString(j));
    }

    protected void printFailures(Result result) {
        List<Failure> failures = result.getFailures();
        if (failures.size() == 0) {
            return;
        }
        int i = 1;
        if (failures.size() == 1) {
            getWriter().println("There was " + failures.size() + " failure:");
        } else {
            getWriter().println("There were " + failures.size() + " failures:");
        }
        Iterator<Failure> it = failures.iterator();
        while (it.hasNext()) {
            printFailure(it.next(), "" + i);
            i++;
        }
    }

    protected void printFailure(Failure failure, String str) {
        getWriter().println(str + ") " + failure.getTestHeader());
        getWriter().print(failure.getTrace());
    }

    protected void printFooter(Result result) {
        if (result.wasSuccessful()) {
            getWriter().println();
            getWriter().print("OK");
            getWriter().println(" (" + result.getRunCount() + " test" + (result.getRunCount() == 1 ? "" : CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION) + ")");
        } else {
            getWriter().println();
            getWriter().println("FAILURES!!!");
            getWriter().println("Tests run: " + result.getRunCount() + ",  Failures: " + result.getFailureCount());
        }
        getWriter().println();
    }

    protected String elapsedTimeAsString(long j) {
        return NumberFormat.getInstance().format(j / 1000.0d);
    }
}
