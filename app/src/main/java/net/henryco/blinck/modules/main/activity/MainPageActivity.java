package net.henryco.blinck.modules.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import lombok.val;
import net.henryco.blinck.R;
import net.henryco.blinck.modules.BlinckApplication;
import net.henryco.blinck.modules.login.activity.LoginActivity;
import net.henryco.blinck.modules.main.service.InfoMainService;
import net.henryco.blinck.util.Authorization;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

import javax.inject.Inject;

public class MainPageActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	@Inject SharedPreferences sharedPreferences;
	@Inject InfoMainService infoMainService;


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
		((BlinckApplication) getApplication()).getMainComponent().inject(this);
		AutoFinder.find(this);


		setSupportActionBar(toolbar);

		initDrawer();

		initTabLayout();
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

		Authorization authorization = new Authorization(this);

		infoMainService.loadAndCacheProfileFromServer(authorization.getUid(), authorization.getToken(), profileForm
				-> runOnUiThread(() -> name.setText(profileForm.getUserName().getFirstName())));
	}





	@Override
	public void onBackPressed() {

		if (drawer.isDrawerOpen(GravityCompat.START))
			drawer.closeDrawer(GravityCompat.START);

		else super.onBackPressed();
	}




	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}



	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {

		switch (item.getItemId()) {

			case R.id.nav_profile:

				break;

			case R.id.nav_notifications:

				break;

			case R.id.nav_settings:

				break;

			case R.id.nav_logout:

				LoginManager.getInstance().logOut();
				startActivity(new Intent(this, LoginActivity.class));
				finish();
				break;
		}

		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

}
