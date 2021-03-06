package net.henryco.blinck.groups.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import net.henryco.blinck.R;
import net.henryco.blinck.BlinckApplication;
import net.henryco.blinck.groups.login.activity.LoginActivity;
import net.henryco.blinck.groups.profile.activity.ProfileActivity;
import net.henryco.blinck.service.ProfileMainService;
import net.henryco.blinck.service.MediaMainService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

import javax.inject.Inject;

public class MainPageActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	private static final int CODE_ACTIVITY_PROFILE = 1;

	@Inject SharedPreferences sharedPreferences;
	@Inject ProfileMainService infoMainService;
	@Inject MediaMainService mediaMainService;


	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	@AutoFind(R.id.container)
	private ViewPager mViewPager;

	@AutoFind(R.id.drawer_layout)
	private AdvanceDrawerLayout drawer;

	@AutoFind(R.id.toolbar)
	private Toolbar toolbar;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawer);
		AutoFinder.inflateAndFind(this, R.id.dynamic_content, R.layout.activity_main_page);

		((BlinckApplication) getApplication()).getMainComponent().inject(this);

		setSupportActionBar(toolbar);

		initDrawer();

		initTabLayout();
	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CODE_ACTIVITY_PROFILE) {
			if (resultCode == RESULT_OK) initDrawer();
		}
	}



	private void initTabLayout() {

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		SectionsPagerAdapter sectionsPagerAdapter =
				new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(sectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
	}



	private void initDrawer() {

		drawer.useCustomBehavior(GravityCompat.START);
		drawer.setViewScale(GravityCompat.START, 0.5f);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		initDrawerHeader();
	}




	private void initDrawerHeader() {

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_text);
		ImageView image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);

		Authorization authorization = Authorization.get(this);

		infoMainService.loadAndCacheProfileFromServer(authorization, profileForm
				-> runOnUiThread(() -> name.setText(profileForm.getUserName().getFirstName())));

		mediaMainService.loadAndCacheProfileAvatarImage(this, authorization,
				consumer -> runOnUiThread(() -> image.setImageBitmap(consumer)));
	}





	@Override
	public void onBackPressed() {

		if (drawer.isDrawerOpen(GravityCompat.START))
			drawer.closeDrawer(GravityCompat.START);
		if (drawer.isDrawerOpen(GravityCompat.END))
			drawer.closeDrawer(GravityCompat.END);
		else super.onBackPressed();
	}




	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		//noinspection SimplifiableIfStatement
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
		}

		return super.onOptionsItemSelected(item);
	}



	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {

		switch (item.getItemId()) {


			case R.id.nav_profile:

				drawer.closeDrawer(GravityCompat.START);
				delayedStart(() -> startActivityForResult(
						new Intent(this, ProfileActivity.class),
						CODE_ACTIVITY_PROFILE
				));

				return false;


			case R.id.nav_notifications:

				drawer.closeDrawer(GravityCompat.START);
				delayedStart(() -> drawer.openDrawer(GravityCompat.END));

				return false;


			case R.id.nav_settings:

				break;


			case R.id.nav_logout:

				infoMainService.logout(Authorization.get(this));
				LoginManager.getInstance().logOut();
				Authorization.reset(this);

				delayedStart(() -> {
					startActivity(new Intent(this, LoginActivity.class));
					finish();
				});

				break;
		}

		drawer.closeDrawer(GravityCompat.START);
		drawer.closeDrawer(GravityCompat.END);

		return true;
	}


	private void delayedStart(Runnable runnable) {
		new Handler().postDelayed(() -> runOnUiThread(runnable), 400);
	}

}