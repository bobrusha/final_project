package com.elegion.androidschool.finalproject;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ListsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment mFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mFragment = new ListsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.must_be_replaced, mFragment)
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.left_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(mNavigationView);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        mDrawerLayout.closeDrawer(mNavigationView);
        int oldFragmentId = mFragment.getId();
        switch (id) {
            case R.id.navigation_item_lists:
                mFragment = new ListsFragment();
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(oldFragmentId, mFragment)
                    .commit();
                return true;
            case R.id.navigation_item_products:
                mFragment = new ProductsListFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(oldFragmentId, mFragment)
                        .commit();
                return true;
            case R.id.navigation_item_markets:
                //TODO: start activity with markets
                return true;
        }
        return false;
    }
}
