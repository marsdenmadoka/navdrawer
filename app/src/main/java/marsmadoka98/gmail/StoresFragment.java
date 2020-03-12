package marsmadoka98.gmail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
//We’ll use a ListFragment called StoresFragment to display the
//list of stores
public class StoresFragment extends ListFragment {
/**
 * note we dont need to create a storefrggment layout resource since it is a list and when we extended
 * the listfragment it automatically generates the list same applies to activity  pastafragment and activity pizzafragment
 *
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.stores));//the array of stores in the string.xml
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
