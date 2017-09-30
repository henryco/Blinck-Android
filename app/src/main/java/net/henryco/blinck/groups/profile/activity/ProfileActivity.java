package net.henryco.blinck.groups.profile.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.R;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.TimeUtils;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;
import net.henryco.blinck.util.task.VoidAsyncTask;

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
	}




	private void initActionBar() {

		setSupportActionBar(toolbar);
		ActionBar supportActionBar = getSupportActionBar();

		if (supportActionBar != null)
			supportActionBar.setDisplayHomeAsUpEnabled(true);
		collapsingToolbarLayout.setVisibility(View.INVISIBLE);
		collapsingToolbarLayout.setEnabled(false);

		new VoidAsyncTask(this::initToolBar).execute();
	}



	private void initToolBar() {

		Authorization authorization = Authorization.get(this);

		profileMainService.loadAndCacheProfileFromServer(authorization,
				profile -> runOnUiThread(() -> {

					final String name = profile.getUserName().getFirstName();
					final int age = TimeUtils.calculateAgeFromBirthDate(profile.getBirthday());
					collapsingToolbarLayout.setTitle(Helper.createTitle(name, age));
					collapsingToolbarLayout.setVisibility(View.VISIBLE);
					collapsingToolbarLayout.setEnabled(true);
				})
		);

		mediaMainService.loadAndCacheProfileAvatarImage(this, authorization,
				bitmap -> runOnUiThread(() -> {
					toolbarImage.setImageBitmap(bitmap);
					toolbarImage.setOnClickListener(v -> {
						// TODO: 30/09/17
					});
				})
		);
	}



	private void initFab() {

		fab.setOnClickListener(view ->
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show()
		);
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
}