package eu.tutorial.dogappdb;

import java.util.List;

import eu.tutorial.dogappdb.model.DogBreed;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface DogApi {
    // API endpoint
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreed>> getDogs();
}
