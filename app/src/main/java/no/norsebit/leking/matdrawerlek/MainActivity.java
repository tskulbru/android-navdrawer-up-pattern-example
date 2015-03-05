package no.norsebit.leking.matdrawerlek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mikepenz.materialdrawer.Drawer;


public class MainActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {

    private Toolbar mToolbar;
    private Drawer.Result mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, new PlaceholderFragment())
                    .commit();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = new Drawer().withActivity(this).withToolbar(mToolbar).build();
        mDrawerLayout = mDrawer.getDrawerLayout();
        mActionBarDrawerToggle = mDrawer.getActionBarDrawerToggle();

        // Add the backstack listener
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        // Handle clicks on the up arrow since this isnt handled by the
        mDrawer.getActionBarDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();

                // Pop the backstack all the way back to the initial fragment. Customize if needed
                fm.popBackStack(fm.getBackStackEntryAt(0).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    /**
     * Called everytime we add or remove something from the backstack
     */
    @Override
    public void onBackStackChanged() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    /**
     * If you need to move backwards inside the app using the back button, and want to override the
     * the default behaviour which could take you outside the app before you've popped the entire stack
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Button btn = (Button) rootView.findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlankFragment f = BlankFragment.newInstance("param1", "param2");
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, f);
                    transaction.addToBackStack("blank");
                    transaction.commit();
                }
            });
            return rootView;
        }
    }
}
