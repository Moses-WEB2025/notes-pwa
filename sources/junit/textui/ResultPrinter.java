package junit.textui;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.Field;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Enumeration;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import miui.cloud.CloudPushConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes5.dex */
public class ResultPrinter implements TestListener {
    int fColumn = 0;
    PrintStream fWriter;

    @Override // junit.framework.TestListener
    public void endTest(Test test) {
    }

    public ResultPrinter(PrintStream printStream) {
        this.fWriter = printStream;
    }

    synchronized void print(TestResult testResult, long j) {
        printHeader(j);
        printErrors(testResult);
        printFailures(testResult);
        printFooter(testResult);
    }

    void printWaitPrompt() {
        getWriter().println();
        getWriter().println("<RETURN> to continue");
    }

    protected void printHeader(long j) {
        getWriter().println();
        getWriter().println("Time: " + elapsedTimeAsString(j));
    }

    protected void printErrors(TestResult testResult) {
        printDefects(testResult.errors(), testResult.errorCount(), SNSAuthProvider.VALUE_SNS_ERROR);
    }

    protected void printFailures(TestResult testResult) {
        printDefects(testResult.failures(), testResult.failureCount(), "failure");
    }

    protected void printDefects(Enumeration<TestFailure> enumeration, int i, String str) {
        if (i == 0) {
            return;
        }
        if (i == 1) {
            getWriter().println("There was " + i + StringUtils.SPACE + str + ":");
        } else {
            getWriter().println("There were " + i + StringUtils.SPACE + str + "s:");
        }
        int i2 = 1;
        while (enumeration.hasMoreElements()) {
            printDefect(enumeration.nextElement(), i2);
            i2++;
        }
    }

    public void printDefect(TestFailure testFailure, int i) {
        printDefectHeader(testFailure, i);
        printDefectTrace(testFailure);
    }

    protected void printDefectHeader(TestFailure testFailure, int i) {
        getWriter().print(i + ") " + testFailure.failedTest());
    }

    protected void printDefectTrace(TestFailure testFailure) {
        getWriter().print(BaseTestRunner.getFilteredTrace(testFailure.trace()));
    }

    protected void printFooter(TestResult testResult) {
        if (testResult.wasSuccessful()) {
            getWriter().println();
            getWriter().print("OK");
            getWriter().println(" (" + testResult.runCount() + " test" + (testResult.runCount() == 1 ? "" : CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION) + ")");
        } else {
            getWriter().println();
            getWriter().println("FAILURES!!!");
            getWriter().println("Tests run: " + testResult.runCount() + ",  Failures: " + testResult.failureCount() + ",  Errors: " + testResult.errorCount());
        }
        getWriter().println();
    }

    protected String elapsedTimeAsString(long j) {
        return NumberFormat.getInstance().format(j / 1000.0d);
    }

    public PrintStream getWriter() {
        return this.fWriter;
    }

    @Override // junit.framework.TestListener
    public void addError(Test test, Throwable th) {
        getWriter().print(ExifInterface.LONGITUDE_EAST);
    }

    @Override // junit.framework.TestListener
    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        getWriter().print(Field.FLOAT_SIGNATURE_PRIMITIVE);
    }

    @Override // junit.framework.TestListener
    public void startTest(Test test) {
        getWriter().print(".");
        int i = this.fColumn;
        this.fColumn = i + 1;
        if (i >= 40) {
            getWriter().println();
            this.fColumn = 0;
        }
    }
}
