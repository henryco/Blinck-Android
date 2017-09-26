package net.henryco.blinck.modules.main.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import lombok.val;
import net.henryco.blinck.R;
import net.henryco.blinck.modules.BlinckApplication;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

import javax.inject.Inject;

public class MainPageActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;


	@Inject SharedPreferences sharedPreferences;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	@AutoFind(R.id.container)
	private ViewPager mViewPager;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		((BlinckApplication) getApplication()).getMainComponent().inject(this);
		AutoFinder.find(this);


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		val sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(sectionsPagerAdapter);



		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_page, menu);
		return true;
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


}
