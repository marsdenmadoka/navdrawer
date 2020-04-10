package marsmadoka98.gmail;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.ActionProvider;
//import android.app.FragmentTransaction;
//import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.ShareActionProvider;

import java.util.Objects;

/**we’ll change the layout of MainActivity.java so that it uses a DrawerLayout
It will contain a frame layout for displaying fragments, and a list view to
display a list of options.
 The list view will contain options for Home, Pizzas, Pasta, and Stores
so that the user can easily navigate to the major hubs of the app */
public class MainActivity extends AppCompatActivity {
    private String[] titles;     //We’ll use these in other methodslater on, so add them as privateclass variables.
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition =0;
    private ShareActionProvider shareActionProvider;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        } //This describes the OnItemClickListener.

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);//getting the title array from string.xml
        drawerList = (ListView) findViewById(R.id.drawer);//get the list view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//Get a reference to theDrawerLayout.
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles)); //dis[lay the title array values from string.xml to the list view called drawer
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");//If the activity has been destroyed and re-created, set the value ofcurrentPosition from the activity’sprevious state and use it to set the action bar title.
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0); }
        /**When the drawer is closed,display the Share action in the action bar.When the drawer is open, hide the Share action.
         //Using an ActionBarDrawerToggle */
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            //Called when a drawer is closed
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();//tells android to recreate menu items
            }

            //  Modifying action bar items at runtime
            public void onDrawerOpened(View drawerView) { //called when darawer is open
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle); //Set the ActionBarDrawerToggle as the DrawerLayout’s drawer listener

         if(getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable the Up icon so it can be used by the ActionBarDrawerToggle.
        getSupportActionBar().setHomeButtonEnabled(true);
    }

        //make the action bar title reflect the fragment that’s displayed when the user clicks on the back button
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getSupportFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment"); //This adds a tag of “visible_fragment” to the fragment as it’s added to the back stack.
                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }
                        if (fragment instanceof PizzaMaterialFragment) { //PizzaFragment
                            currentPosition = 1;
                        }
                        if (fragment instanceof PastaFragment) {
                            currentPosition = 2;
                        }
                        if (fragment instanceof StoresFragment) {
                            currentPosition = 3;
                        }
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);

                    }
                }
    );
}

    private void selectItem(int position) {
// update the main content by replacing fragment
        currentPosition = position;
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaMaterialFragment();    //PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();}

        //display the  fragments using a transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment,"visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        //Set the action bar title
        setActionBarTitle(position); //Call the setActionTitle()method, passing it the position of the item that was clicked on.
        //Close drawer
        drawerLayout.closeDrawer(drawerList);
    }
    //Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) { //The onPrepareOptionsMenu() method gets called whenever invalidateOptionsMenu() gets called.
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen); //Set the Share action’s visibility to false if the drawer is open,set it to true if it isn’t.
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) { //Sync the state of the ActionBarDrawerToggle with the state of the drawer.
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) { //Pass any configuration changes to the ActionBarDrawerToggle.
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);//Save the state of currentPosition if the activity’s going to be destroyed.
        outState.putInt("position", currentPosition);
    }

    private void setActionBarTitle(int position) { //Call the setActionTitle() method, passing it the position of the item that was clicked on
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);//If the user clicks on the “Home” option, use the app name for the title.
        } else {// Otherwise, get the String from the titles array for the position that was clicked and use that
            title = titles[position];
        }
        if(getSupportActionBar() != null) {
        getSupportActionBar().setTitle(title);//Display the title in the action bar.
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       MenuItem shareItem = menu.findItem(R.id.action_share);
       shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
       setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }
    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Let the ActionBarDrawerToggle handle being clicked
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //Code to handle the rest of the action items
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);// If the Create Order action is clicked, start OrderActivity.
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}//close the class