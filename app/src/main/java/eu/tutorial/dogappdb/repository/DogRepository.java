package eu.tutorial.dogappdb.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import eu.tutorial.dogappdb.dao.DogDAO;
import eu.tutorial.dogappdb.dao.DogDatabase;
import eu.tutorial.dogappdb.model.DogBreed;

public class DogRepository {
    public DogDAO dogDAO;
    public List<DogBreed> allDogs;
    private DogDatabase dogDatabase;

    public DogRepository(Application application){
        dogDatabase = DogDatabase.getInstance(application);
        dogDAO = dogDatabase.dogDAO();
    }

    public void insert(List<DogBreed> dogs){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dogDAO.insert(dogs);
            }
        });
    }

    public List<DogBreed> getAllDogs(){
        try {
            return new LoadDataAsyncTask(dogDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class LoadDataAsyncTask extends AsyncTask<Void, Void, List<DogBreed>>{
        private DogDAO dogDAO;

        public LoadDataAsyncTask(DogDAO dogDAO){
            this.dogDAO = dogDAO;
        }

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return dogDAO.getAllDogs();
        }
    }
}
