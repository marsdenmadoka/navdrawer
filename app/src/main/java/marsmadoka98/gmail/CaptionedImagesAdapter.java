package marsmadoka98.gmail;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    /** create a recycler view that will display a list of our card views.
     the recycler view needs to display a list of cards, each
     containing an image view and a text view. This means that the adapter
     needs to create views for these items, and replace their contents when each
     item in the data set is no longer visible.
     */
    private String[] captions;
    private int[] imageIds;     //We’ll use these variables to hold the pizza data.the captions and image resource IDs of the pizzas.
    private Listener listener;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds){ //Pass data to the adapter in its constructor.
        this.captions = captions;
        this.imageIds = imageIds;
    }

    public static interface Listener {
        public void onClick(int position);
    }

    public void setListener(Listener listener){ //Activity’s and fragments will use this method to register as a listener.
        this.listener = listener;
    }

    /**define the view Holder Each data item in our recycler view is a card view, so we need to make our view holder store card views If you want to display another type
    of data in the recycler view, you define it here.
    Our recycler view needs to display CardViews, so we specify that our ViewHolder containsCardViews */
       //Define the adapter’s ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v) { //Each ViewHolder will display a CardView.
            super(v); //call the ViewHolder super constructor
            cardView = v;
        }
    }

    //Create the ViewHolders
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, //Specify what layout to use for the contents of the ViewHolder.
                        parent, false); //Use our layout for the CardViews.*/
        return new ViewHolder(cv);
    }

    /** The onBindViewHolder() method gets called whenever the
     recycler view needs to display data in a view holder */
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image); //Display the image in the ImageView.
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]); //Display the caption in the TextView..
            //When the CardView is clicked, call the Listener onClick() method.
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }

    //Return the number of items in the data set
    @Override
    public int getItemCount(){
        return captions.length; //The length of the captions array equals the number of data items in the recycler view.
    }
}
