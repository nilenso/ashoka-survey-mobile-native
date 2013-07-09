package com.infinitisuite.surveymobile.util.net;

import com.infinitisuite.surveymobile.exceptions.SurveyWebURIException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(JUnit4.class)
public class SurveyWebURITest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throws_exception_if_path_is_invalid() {
        thrown.expect(SurveyWebURIException.class);
        new SurveyWebURI("@#$%^&*()invalid_path");
    }

    @Test
    public void generates_absolute_url() throws Exception {
        String absoluteUrl = new SurveyWebURI("foo/bar").getAbsoluteUrl();
        assertThat(absoluteUrl).isEqualTo("http://staging.thesurveys.org/foo/bar");
    }

    @Test
    public void generates_absolute_url_when_path_begins_with_a_slash() throws Exception {
        String absoluteUrl = new SurveyWebURI("/foo/bar").getAbsoluteUrl();
        assertThat(absoluteUrl).isEqualTo("http://staging.thesurveys.org/foo/bar");
    }
}
