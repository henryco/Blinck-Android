package net.henryco.blinck.groups.profile.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.R;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.TimeUtils;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;
import net.henryco.blinck.util.task.VoidAsyncTask;
import org.joda.time.LocalDate;

import javax.inject.Inject;

public class ProfileActivity extends AppCompatActivity {



	@AutoFind(R.id.toolbar)
	private Toolbar toolbar;

	@AutoFind(R.id.fab)
	private FloatingActionButton fab;

	@AutoFind(R.id.toolbar_layout)
	private CollapsingToolbarLayout collapsingToolbarLayout;

	@AutoFind(R.id.toolbar_image)
	private ImageView toolbarImage;


	@Inject ProfileMainService profileMainService;
	@Inject MediaMainService mediaMainService;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		AutoFinder.find(this);

		((BlinckApplication) getApplication()).getProfileComponent().inject(this);

		initActionBar();
		initFab();

		initUserProfile();
	}



	private void initActionBar() {

		setSupportActionBar(toolbar);
		ActionBar supportActionBar = getSupportActionBar();

		if (supportActionBar != null)
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		collapsingToolbarLayout.setVisibility(View.INVISIBLE);
		collapsingToolbarLayout.setEnabled(false);
	}



	private void initFab() {

		fab.setOnClickListener(v -> {
			// TODO: 30/09/17
		});
	}



	private void initUserProfile() {

		Authorization auth = Authorization.get(this);

		new VoidAsyncTask(() -> {

			profileMainService.loadAndCacheProfileFromServer(auth, this::loadProfileData);
			mediaMainService.loadAndCacheProfileAvatarImage(this, auth, this::loadProfileAvatar);
		}).execute();
	}



	private void loadProfileData(UserProfileForm profile) {

		final String name = profile.getUserName().getFirstName();
		final int age = TimeUtils.calculateAgeFromBirthDate(profile.getBirthday());

		final String gender = Helper.createGender(profile.getGender());
		final String birthday = Helper.createBirthday(profile.getBirthday());
		final String nameFull = Helper.createName(
				profile.getUserName().getFirstName(),
				profile.getUserName().getSecondName(),
				profile.getUserName().getLastName()
		);

		final String about = profile.getAbout();
		final String nickname = profile.getUserName().getNickname();


		runOnUiThread(() -> {

			collapsingToolbarLayout.setTitle(Helper.createTitle(name, age));
			collapsingToolbarLayout.setVisibility(View.VISIBLE);
			collapsingToolbarLayout.setEnabled(true);

			((TextView) findViewById(R.id.text_name)).setText(nameFull);
			((TextView) findViewById(R.id.text_gender)).setText(gender);
			((TextView) findViewById(R.id.text_birthday)).setText(birthday);

			if (about != null && !about.isEmpty())
				((TextView) findViewById(R.id.text_about)).setText(about);

			if (nickname!= null && !nickname.isEmpty()) {
				((TextView) findViewById(R.id.text_username)).setText(nickname);

				findViewById(R.id.container_username)
						.setVisibility(View.VISIBLE);
			}

			findViewById(R.id.container_profile)
					.setVisibility(View.VISIBLE);
		});
	}



	private void loadProfileAvatar(Bitmap bitmap) {

		runOnUiThread(() -> {

			toolbarImage.setImageBitmap(bitmap);
			toolbarImage.setOnClickListener(v -> {
				// TODO: 30/09/17
			});
		});
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


	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}


}


final class Helper {

	static String createTitle(String name, int age) {
		return name + ",  " + age;
	}

	static String createName(String first, String second, String last) {

		String s = second == null ? "" : (" " + second);
		String l = last == null ? "" : (" " + last);
		return first + s + l;
	}

	static String createGender(String gender) {

		if (gender == null) return "";
		if (gender.equalsIgnoreCase("male")) return "Male";
		if (gender.equalsIgnoreCase("female")) return "Female";

		return "Unknown";
	}

	static String createBirthday(long birthday) {
		return new LocalDate(birthday).toString().replace("-", "/");
	}
}