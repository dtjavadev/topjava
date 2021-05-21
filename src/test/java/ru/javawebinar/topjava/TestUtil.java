package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestUtil {
    private static final Logger log = LoggerFactory.getLogger("result");

    private static final StringBuilder results = new StringBuilder();

    private static String info(Description description, String status, long nanos) {
        return String.format("Test %s %s, spent %d milliseconds",
                description.getMethodName(), status, TimeUnit.NANOSECONDS.toMillis(nanos));
    }

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            String result = info(description, "succeeded", nanos);
            results.append(result).append('\n');
            log.info(result + "\n");
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            String result = info(description, "failed", nanos);
            results.append(result).append('\n');
            log.info(result + "\n");
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            String result = info(description, "skipped", nanos);
            results.append(result).append('\n');
            log.info(result + "\n");
        }
    };

    public static final ExternalResource SUMMARY = new ExternalResource() {

        @Override
        protected void before() throws Throwable {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" + "\n" + results + "\n");
        }
    };
}
