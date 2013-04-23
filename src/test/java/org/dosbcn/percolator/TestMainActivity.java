package org.dosbcn.percolator;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Test cases for the {@link MainActivity}.<br/>
 * Note: for the tests which require initialization we use a
 * {@link MockMainActivity} which resolves issues with the DB and other
 * components which are normally initialized. The tests, however, do not depend
 * on any of the mocked components and focus on Android managed aspects of the
 * {@link MainActivity}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {

    private static final String EXPECTED_TITLE = "Percolator";

    @Test
    public void testAppName() {
        Resources cardResources = new MainActivity().getResources();
        String appName = cardResources.getString(R.string.app_name);
        assertThat(appName, equalTo(EXPECTED_TITLE));
    }

    @Test
    public void testFindTitleField() {
        MainActivity mainActivity = new MockMainActivity();
        EditText titleField = mainActivity.findTitleField();
        assertNotNull(titleField);
    }

    @Test
    public void testFindDescriptionField() {
        MainActivity mainActivity = new MockMainActivity();
        EditText descField = mainActivity.findDescriptionField();
        assertNotNull(descField);
    }

    @Test
    public void testFindSaveButton() {
        MainActivity mainActivity = new MockMainActivity();
        Button saveButton = mainActivity.findSaveButton();
        assertNotNull(saveButton);
    }

}
