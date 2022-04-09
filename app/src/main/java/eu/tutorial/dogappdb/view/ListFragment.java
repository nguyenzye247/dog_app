package eu.tutorial.dogappdb.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eu.tutorial.dogappdb.R;
import eu.tutorial.dogappdb.api.DogAPIService;
import eu.tutorial.dogappdb.databinding.FragmentListBinding;
import eu.tutorial.dogappdb.model.DogBreed;
import eu.tutorial.dogappdb.repository.DogRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment{

    private DogAPIService dogApiService;
    private RecyclerView recyclerViewDogs;
    private DogAdapter dogAdapter;
    private ArrayList<DogBreed> dogBreeds;
    private DogRepository dogRepository;

    private FragmentListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dogAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogRepository = new DogRepository(requireActivity().getApplication());

        // Load data from API to DB
        dogApiService = new DogAPIService();
        dogApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogs) {
                        dogRepository.insert(dogs);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        recyclerViewDogs = binding.dogBreedRv;
        dogBreeds = new ArrayList<DogBreed>();

        dogAdapter = new DogAdapter(dogBreeds);
        recyclerViewDogs.setAdapter(dogAdapter);
        recyclerViewDogs.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dogBreeds = (ArrayList<DogBreed>) dogRepository.getAllDogs();
        dogAdapter.setDogBreeds(dogBreeds);
    }
}