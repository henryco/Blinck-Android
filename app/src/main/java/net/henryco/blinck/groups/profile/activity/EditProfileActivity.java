package net.henryco.blinck.groups.profile.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import lombok.val;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.R;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.service.ProfileUpdateService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.form.user.UserNameForm;
import net.henryco.blinck.util.form.user.UserProfileForm;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;
import net.henryco.blinck.util.task.RefreshableTimer;
import org.joda.time.LocalDate;

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


	@AutoFind(R.id.first_name)
	private EditText firstName;

	@AutoFind(R.id.second_name)
	private EditText secondName;

	@AutoFind(R.id.last_name)
	private EditText lastName;

	@AutoFind(R.id.about)
	private EditText about;

	@AutoFind(R.id.birthday)
	private EditText birthday;

	@AutoFind(R.id.nickname)
	private EditText nick;

	private String nickname;

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
	}



	private void initActionBar() {

		final ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		}
	}


	private void initImages(Authorization auth) {

		final ImageView[] images = {image1, image2, image3};

		mediaMainService.loadProfileAvatarFromCache(this, auth,
				bitmap -> runOnUiThread(
						() -> imageAvatar.setImageBitmap(bitmap))
		);

		imageAvatar.setOnClickListener(v -> {
			// TODO: 01/10/17 upload avatar
		});

		for (int i = 0; i < images.length; i++) {

			val image = images[i];
			val ref = i;

			if (image != null) {

				image.setOnClickListener(v -> {
					// TODO: 01/10/17 upload image
				});
			}
		}
	}



	private void initProfile(Authorization auth) {

		UserProfileForm form = profileMainService.getProfileFromCache(auth.getUid());
		Log.d("Profile", form.toString());

		firstName.setText(form.getUserName().getFirstName());
		secondName.setText(ProfileHelper.safetyString(form.getUserName().getSecondName()));
		lastName.setText(ProfileHelper.safetyString(form.getUserName().getLastName()));
		about.setText(ProfileHelper.safetyString(form.getAbout()));

		initBirthdayField(form.getBirthday());
		initGenderField(form.getGender());
		initUsernameField(form.getUserName().getNickname());
	}



	private void initBirthdayField(Long date) {

		birthday.setText(ProfileHelper.createBirthday(date));

		DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {

			LocalDate newBirthday = new LocalDate(year, month + 1, dayOfMonth);
			birthday.setText(ProfileHelper.createBirthday(newBirthday.toDate().getTime()));
		};

		val local = new LocalDate(date);
		birthday.setOnClickListener(v ->
				new DatePickerDialog(this, listener,
						local.getYear(),
						local.getMonthOfYear() - 1,
						local.getDayOfMonth()
				).show()
		);
	}



	private void initGenderField(String gender) {

		if (gender.equalsIgnoreCase(ProfileHelper.GENDER_MALE))
			((RadioButton) findViewById(R.id.radio_male)).setChecked(true);

		else if (gender.equalsIgnoreCase(ProfileHelper.GENDER_FEMALE))
			((RadioButton) findViewById(R.id.radio_female)).setChecked(true);
	}



	private void initUsernameField(String username) {

		nickname = ProfileHelper.safetyString(username);
		nick.setText(nickname);
		nick.addTextChangedListener(new TextWatcher() {

			private final Authorization authorization =
					Authorization.get(EditProfileActivity.this);

			private final RefreshableTimer timer
					= new RefreshableTimer(1500, () -> {

				final String s = nick.getText().toString();
				profileUpdateService.updateNickname(authorization, s, updated ->
						runOnUiThread(() -> {
							if (!updated && !s.equals(nickname))
								nick.setTextColor(Color.RED);
							else nick.setTextColor(Color.GREEN);
						})
				);
			});

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				timer.refresh();
			}
		});
	}




	@Override
	public void onBackPressed() {

		updateUserProfile();
		new Handler().postDelayed(()
				-> runOnUiThread(() -> {
			Toast.makeText(this, "Updating might take few seconds", Toast.LENGTH_LONG).show();
			setResult(RESULT_OK);
			finish();
		}), 400);
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



	private void updateUserProfile() {

		val auth = Authorization.get(this);
		val form = new UserProfileForm();
		val nameForm = new UserNameForm();

		nameForm.setFirstName(firstName.getText().toString());
		nameForm.setSecondName(secondName.getText().toString());
		nameForm.setLastName(lastName.getText().toString());
		nameForm.setNickname(nick.getText().toString());

		form.setAbout(about.getText().toString());
		form.setBirthday(ProfileHelper.getBirthday(birthday.getText().toString()));
		form.setUserName(nameForm);

		if (((RadioButton) findViewById(R.id.radio_female)).isChecked())
			form.setGender(ProfileHelper.GENDER_FEMALE);
		else form.setGender(ProfileHelper.GENDER_MALE);

		profileUpdateService.updateProfile(auth, form, status
				-> Log.d("Profile Edit", "Status OK")
		);
	}
}