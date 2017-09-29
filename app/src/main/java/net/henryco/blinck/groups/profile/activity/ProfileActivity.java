package net.henryco.blinck.groups.profile.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import net.henryco.blinck.R;
import net.henryco.blinck.util.reflect.AutoFind;
import net.henryco.blinck.util.reflect.AutoFinder;

public class ProfileActivity extends AppCompatActivity {



	@AutoFind(R.id.toolbar)
	private Toolbar toolbar;

	@AutoFind(R.id.fab)
	private FloatingActionButton fab;

	@AutoFind(R.id.toolbar_layout)
	private CollapsingToolbarLayout collapsingToolbarLayout;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		AutoFinder.find(this);

		setSupportActionBar(toolbar);

		fab.setOnClickListener(view ->
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show()
		);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		collapsingToolbarLayout.setTitle("someTitle");
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
