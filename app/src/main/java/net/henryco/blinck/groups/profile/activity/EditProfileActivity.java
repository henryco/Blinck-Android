package net.henryco.blinck.groups.profile.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.R;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.service.ProfileUpdateService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

import javax.inject.Inject;

public class EditProfileActivity extends AppCompatActivity {


	@Inject ProfileUpdateService profileUpdateService;
	@Inject ProfileMainService profileMainService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		AutoFinder.find(this);

		((BlinckApplication) getApplication()).getProfileComponent().inject(this);

		initActionBar();

		Authorization authorization = Authorization.get(this);
//		UserProfileForm profileForm = profileMainService.getProfileFromCache(authorization.getUid());
//
//		System.out.println(profileForm);

	}



	private void initActionBar() {

		final ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public void onBackPressed() {

		// TODO: 30/09/17 UPLOAD AND SAVE CHANGES
		setResult(RESULT_OK);
		finish();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

}