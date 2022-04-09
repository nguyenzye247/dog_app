package eu.tutorial.dogappdb.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import eu.tutorial.dogappdb.R;
import eu.tutorial.dogappdb.databinding.FragmentDetailBinding;
import eu.tutorial.dogappdb.model.DogBreed;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private DogBreed dogBreed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            dogBreed = (DogBreed) getArguments().getSerializable("dogBreed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_detail,
                null,
                false);
        View detailViewRoot = binding.getRoot();
        binding.setDog(dogBreed);
        Picasso.get().load(dogBreed.getUrl()).into(binding.detailDogIv);
        return detailViewRoot;
    }
}