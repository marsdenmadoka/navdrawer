package marsmadoka98.gmail;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import android.app.FragmentTransaction;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener { //This describes the OnItemClickListener.
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectItem(position); //Call the selectItem() method.//Code to run when an item in the navigation drawer gets clicked
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);//gettig
        drawerList = (ListView) findViewById(R.id.drawer);//get the list view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//Get a reference to theDrawerLayout.
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles)); //dis[lay the title array values from string.xml to the list view called drawer
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState == null) {
            selectItem(0);
        }
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
            public void onDrawerOpened(View drawerView) { //called when darwe is open
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle); //Set the ActionBarDrawerToggle as the DrawerLayout’s drawer listener

        getActionBar().setDisplayHomeAsUpEnabled(true); //Enable the Up icon so it can be used by the ActionBarDrawerToggle.
        getActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    
    //Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) { //The onPrepareOptionsMenu() method gets called whenever invalidateOptionsMenu() gets called.
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen); //Set the Share action’s visibility to false if the drawer is open,set it to true if it isn’t.
        return super.onPrepareOptionsMenu(menu);
    }

    /**Enable the drawer to open and close */


    private void selectItem(int position) {
// update the main content by replacing fragment
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();};

        //display the  fragments using a transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        //Set the action bar title
        setActionBarTitle(position); //Call the setActionTitle()method, passing it the position of the item that was clicked on.
        //Close drawer
        drawerLayout.closeDrawer(drawerList);
    }



    private void setActionBarTitle(int position) { //Call the setActionTitle() method, passing it the position of the item that was clicked on
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);//If the user clicks on the “Home” option, use the app name for the title.
        } else {// Otherwise, get the String from the titles array for the position that was clicked and use that
            title = titles[position];
        }
        getActionBar().setTitle(title);//Display the title in the action bar.
    }





}//close the class