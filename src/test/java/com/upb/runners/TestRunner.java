package com.upb.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.upb.steps,com.upb.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, " +
                "html:target/cucumber-report.html, " +
                "json:target/cucumber.json, " +
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
)
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
public class TestRunner {
}
