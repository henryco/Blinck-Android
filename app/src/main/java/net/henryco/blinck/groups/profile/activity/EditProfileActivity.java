package net.henryco.blinck.groups.profile.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import lombok.val;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.R;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.service.ProfileUpdateService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

import javax.inject.Inject;

public class EditProfileActivity extends AppCompatActivity {


	@AutoFind(R.id.image_avatar)
	private ImageView imageAvatar;

	@AutoFind(R.id.image_1)
	private ImageView image1;

	@AutoFind(R.id.image_2)
	private ImageView image2;

	@AutoFind(R.id.image_3)
	private ImageView image3;



	@Inject ProfileUpdateService profileUpdateService;
	@Inject ProfileMainService profileMainService;

	@Inject MediaMainService mediaMainService;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		AutoFinder.find(this);

		((BlinckApplication) getApplication()).getProfileComponent().inject(this);

		val authorization = Authorization.get(this);

		initActionBar();
		initImages(authorization);
		initProfile(authorization);

//		UserProfileForm profileForm = profileMainService.getProfileFromCache(authorization.getUid());
	}



	private void initActionBar() {

		final ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		}
	}


	private void initImages(Authorization auth) {

		mediaMainService.loadProfileAvatarFromCache(this, auth,
				bitmap -> runOnUiThread(
						() -> imageAvatar.setImageBitmap(bitmap))
		);

		// TODO: 01/10/17
	}


	private void initProfile(Authorization auth) {

		val profileForm = profileMainService.getProfileFromCache(auth.getUid());
		Log.d("Profile", profileForm.toString());

		// TODO: 01/10/17
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