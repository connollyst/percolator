package org.dosbcn.percolator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.dosbcn.flashcards.R;
import org.dosbcn.percolator.CardActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link CardActivity}.<br/>
 * Note: for the tests which require initialization we use a
 * {@link MockCardActivity} which resolves issues with the DB and other
 * components which are normally initialized. The tests, however, do not depend
 * on any of the mocked components and focus on Android managed aspects of the
 * {@link CardActivity}.
 * 
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardActivity {

	private static final String EXPECTED_TITLE = "Flash Cards";

	@Test
	public void testAppName() {
		Resources cardResources = new CardActivity().getResources();
		String appName = cardResources.getString(R.string.app_name);
		assertThat(appName, equalTo(EXPECTED_TITLE));
	}

	@Test
	public void testFindTitleField() {
		CardActivity cardActivity = new MockCardActivity();
		EditText titleField = cardActivity.findTitleField();
		assertNotNull(titleField);
	}

	@Test
	public void testFindDescriptionField() {
		CardActivity cardActivity = new MockCardActivity();
		EditText descField = cardActivity.findDescriptionField();
		assertNotNull(descField);
	}

	@Test
	public void testFindSaveButton() {
		CardActivity cardActivity = new MockCardActivity();
		Button saveButton = cardActivity.findSaveButton();
		assertNotNull(saveButton);
	}

}
