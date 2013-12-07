package com.dosbcn.percolator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;

/**
 * Test cases for the {@link com.dosbcn.percolator.MainActivity}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {

	private static final String EXPECTED_TITLE = "Percolator";

	@Test
	public void testAppName() {
		MainActivity mainActivity = RobolectricHelper.createMainActivity();
		Resources cardResources = mainActivity.getResources();
		String appName = cardResources.getString(R.string.app_name);
		assertThat(appName, equalTo(EXPECTED_TITLE));
	}

	@Test
	public void testFindTitleField() {
		MainActivity mainActivity = RobolectricHelper.createMainActivity();
		EditText titleField = mainActivity.findTitleField();
		assertNotNull(titleField);
	}

	@Test
	public void testFindDescriptionField() {
		MainActivity mainActivity = RobolectricHelper.createMainActivity();
		EditText descField = mainActivity.findDescriptionField();
		assertNotNull(descField);
	}

	@Test
	public void testFindSaveButton() {
		MainActivity mainActivity = RobolectricHelper.createMainActivity();
		Button saveButton = mainActivity.findSaveButton();
		assertNotNull(saveButton);
	}

	@Test
	public void testWelcomeMessageShownOnFirstLaunch() {
		fail("TODO implement test");
	}

	@Test
	public void testWelcomeMessageNotShownOnSecondLaunch() {
		fail("TODO implement test");
	}

	@Test
	public void testExampleShownAfterWelcomeMessage() {
		fail("TODO implement test");
	}

	@Test
	public void testTitleFieldFocusedOnLaunch() {
		fail("TODO implement test");
	}

	@Test
	public void testSoftKeyboardShownOnLaunch() {
		fail("TODO implement test");
	}

	@Test
	public void testSaveButtonDisabledByDefault() {
		fail("TODO implement test");
	}

	@Test
	public void testSaveButtonEnabledAfterTitleFieldInput() {
		fail("TODO implement test");
	}

	@Test
	public void testSaveButtonDisabledAfterDescriptionFieldInput() {
		fail("TODO implement test");
	}

	@Test
	public void testListMenuItemSelected() {
		fail("TODO implement test");
	}

	@Test
	public void testStateSavedOnPause() {
		fail("TODO implement test");
	}

	@Test
	public void testStateRestoredOnResume() {
		fail("TODO implement test");
	}

}
