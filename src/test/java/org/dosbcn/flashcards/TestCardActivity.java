package org.dosbcn.flashcards;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import android.content.res.Resources;

public class TestCardActivity {

	private static final String EXPECTED_TITLE = "Flash Cards";

	@Test
	public void testSave() {
		Resources cardResources = new CardActivity().getResources();
		String appName = cardResources.getString(R.string.app_name);
		assertThat(appName, equalTo(EXPECTED_TITLE));
	}
}
