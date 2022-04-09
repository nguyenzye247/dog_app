package eu.tutorial.dogappdb.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import eu.tutorial.dogappdb.R;
import eu.tutorial.dogappdb.model.DogBreed;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> implements Filterable {

    private ArrayList<DogBreed> dogBreeds;
    private ArrayList<DogBreed> dogBreedFull;
    private float xTouch = 0;
    private float yTouch = 0;
    private float clickThreshold = 5.0f;

    private final ViewBinder viewBinder = new ViewBinder();

    private Context parentContext;

    public DogAdapter(ArrayList<DogBreed> dogBreeds){
        this.dogBreeds = dogBreeds;
    }

    public void setDogBreeds(List<DogBreed> dogs){
        this.dogBreeds = (ArrayList<DogBreed>) dogs;
    }

    @NonNull
    @Override
    public DogAdapter.DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
        this.dogBreedFull = new ArrayList<>(dogBreeds);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.DogViewHolder holder, int position) {
        DogBreed dog = this.dogBreeds.get(position);
        viewBinder.bind(holder.swipeLayout, String.valueOf(dog.getId()));

        // Set main layout item components
        Picasso.get().load(dog.getUrl()).into(holder.dogBreedImage);
        holder.dogBreedName.setText(dog.getName());
        holder.description.setText(dog.getBred() == null || dog.getBred().equals("") ? "Unknown" : dog.getBred());
        holder.setDog(dog);
        if(!dog.isLiked()){
            holder.like.setImageResource(R.drawable.ic_heart_svgrepo_com);
        }
        else{
            holder.like.setImageResource(R.drawable.ic_heart_red3);
        }

        // Set secondary layout item components
        holder.dogNameTV.setText((dog.getName()));
        holder.dogOriginTV.setText(dog.getOrigin() == null || dog.getOrigin().equals("")
                ? "Unknown" : dog.getOrigin());
        holder.dogLifeSpanTV.setText(dog.getLifeSpan());
        holder.dogTemperamentTV
                .setText(dog.getTemperament() == null  || dog.getTemperament().equals("")
                        ? "Unknown" : dog.getTemperament());

        // Separate onClick and onSwipe
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && !v.hasFocus()){
                    xTouch = event.getX();
                    yTouch = event.getY();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP && !v.hasFocus()) {
                    if(Math.abs(xTouch - event.getX()) <= clickThreshold && Math.abs(yTouch - event.getY()) <= clickThreshold){
                        v.performClick();
                    }
                }
                return false;
            }
        });

        // Set on dog item click and move to detail fragment view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DogBreed dog = dogBreeds.get(holder.getAdapterPosition());
                Bundle dogBundle = new Bundle();
                dogBundle.putSerializable("dogBreed", dog);
                Navigation.findNavController(holder.itemView).navigate(R.id.detailFragment, dogBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.dogBreeds != null){
            return this.dogBreeds.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DogBreed> filterDogs = new ArrayList<DogBreed>();
            if (constraint == null || constraint.length() == 0) {
                filterDogs.addAll(dogBreedFull);
            } else {
                String searchPattern = constraint.toString().toLowerCase().trim();
                for (DogBreed dog : dogBreedFull) {
                    if (dog.getName().toLowerCase().contains(searchPattern)) {
                        filterDogs.add(dog);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterDogs;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dogBreeds.clear();
            dogBreeds.addAll((List)results.values);
            setDogBreeds(dogBreeds);
        }
    };

    public class DogViewHolder extends RecyclerView.ViewHolder{

        private SwipeLayout swipeLayout;
        private GestureDetectorCompat mDetector;
        private static final String DEBUG_TAG = "Gestures";

        public TextView dogNameTV;
        public TextView dogOriginTV;
        public TextView dogLifeSpanTV;
        public TextView dogTemperamentTV;

        public ImageView dogBreedImage;
        public TextView dogBreedName;
        public TextView description;
        public ImageView like;

        private DogBreed dog;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.dog_sl);

            dogNameTV = itemView.findViewById(R.id.info_dog_name_tv);
            dogOriginTV = itemView.findViewById(R.id.info_dog_origin_tv);
            dogLifeSpanTV = itemView.findViewById(R.id.info_dog_life_tv);
            dogTemperamentTV = itemView.findViewById(R.id.info_dog_temper_tv);


            dogBreedImage = itemView.findViewById(R.id.dog_iv);
            dogBreedName = itemView.findViewById(R.id.dog_breed_tv);
            description = itemView.findViewById(R.id.description_tv);
            like = itemView.findViewById(R.id.like_btn);

            like.setBackgroundResource(0);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dog.changeLikeState();
                    notifyDataSetChanged();
                    if (dog.isLiked()) {
                        like.setImageResource(R.drawable.ic_heart_red3);
                    } else {
                        like.setImageResource(R.drawable.ic_heart_svgrepo_com);
                    }
                    like.setBackgroundResource(0);
                }
            });
        }

        public void setDog(DogBreed dog){
            this.dog = dog;
        }

    }
}
