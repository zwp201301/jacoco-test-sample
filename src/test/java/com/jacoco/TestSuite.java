package com.jacoco;

import com.jacoco.controller.StreamControllerTest;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StreamControllerTest.class,
})
public class TestSuite {

    public static boolean isInTestSuite = false;
    @ClassRule
    public static final ExternalResource resource = new ExternalResource() {

        @Override
        protected void before() {
            isInTestSuite = true;
        }

        @Override
        protected void after() {

        }

    };

}