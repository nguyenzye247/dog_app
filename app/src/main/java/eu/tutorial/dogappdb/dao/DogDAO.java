package eu.tutorial.dogappdb.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.tutorial.dogappdb.model.DogBreed;

@Dao
public interface DogDAO {

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDogs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DogBreed> dogs);

    @Query("DELETE FROM dogbreed")
    void deleteAll();
}
