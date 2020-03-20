package marsmadoka98.gmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PizzaDetailActivity extends AppCompatActivity {
    //PizzaDetailActivity will display the name of the pizza the user selected, along with its image.this is te activity where our recycleViewer objects will sends us to once clicked
    private ShareActionProvider shareActionProvider; //We’ll use this constant to pass the ID of the pizza as extra information in the intent
    public static final String EXTRA_PIZZANO = "pizzaNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);
        //Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }//Enable the Up button
//Display details of the pizza
        int pizzaNo = (Integer) getIntent().getExtras().get(EXTRA_PIZZANO); //Get the pizza the user chosoe from the intent.
        String pizzaName = Pizza.pizzas[pizzaNo].getName();
        TextView textView = (TextView) findViewById(R.id.pizza_text);
        textView.setText(pizzaName);
        int pizzaImage = Pizza.pizzas[pizzaNo].getImageResourceId();
        ImageView imageView = (ImageView) findViewById(R.id.pizza_image);
        imageView.setImageDrawable(getResources().getDrawable(pizzaImage));//use the pizza ID to populate the TextView and ImageView.
        imageView.setContentDescription(pizzaName);
    }

    @Override
    /* Add items in the menu resource file to the action bar. */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Share the name of the pizza
        TextView textView = (TextView) findViewById(R.id.pizza_text);
        CharSequence pizzaName = textView.getText();
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, pizzaName);
        shareActionProvider.setShareIntent(intent); //Set the default text to share in the Share action.
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class); //Start OrderActivity when the user clicks on action_create_order in the action bar.
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}