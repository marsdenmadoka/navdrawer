package marsmadoka98.gmail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaMaterialFragment extends Fragment {
//create ths after creating the recycle viewer adapter that is the CaptionedImagesAdapter since those are the steps needed
    public PizzaMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pizzaRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_pizza_material, container, false);//Use the layout fragment_pizza_material.

        //Add the pizza names to an array of Strings, and the pizza images to an array of ints.
        String[] pizzaNames = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName(); }


        int[] pizzaImages = new int[Pizza.pizzas.length];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId(); }

        //Pass the arrays to the adapter. we  created earlier
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        pizzaRecycler.setAdapter(adapter);
        //specify how the views in the recycler view should be arranged.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //displaying the card view in a linearView so will use LinearLayoutManager.
        pizzaRecycler.setLayoutManager(layoutManager);

        //This implements the Listener onClick() method. It starts PizzaDetailActivity, passing it the ID of the pizza the user chose.
        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
                getActivity().startActivity(intent);
            }

        });

        return pizzaRecycler;
    }
   // now change MainActivity so that it’s displayed when the user clicks on the Pizzas option in the navigation drawer.



    /** One of the ways in which a recycler view is more flexible than a
     list view is when it comes to arranging its views. A list view displays
     its views in a single vertical list, but a recycler view gives you more
     options. You can choose to display views in a linear list, a grid, or a
     staggered grid.
     You specify how to arrange the views using a layout manager */


}
