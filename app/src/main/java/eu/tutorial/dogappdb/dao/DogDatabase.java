package eu.tutorial.dogappdb.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import eu.tutorial.dogappdb.helper.Converters;
import eu.tutorial.dogappdb.model.DogBreed;

@Database(entities = {DogBreed.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DogDatabase extends RoomDatabase {
    public abstract DogDAO dogDAO();

    private static DogDatabase instance = null;

    public static DogDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, DogDatabase.class, "dogapp")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(instance);
        }
    };

    static class PopulateDBAsync extends AsyncTask<Void, Void, Void>{
        private DogDAO dogDAO;
        public PopulateDBAsync(DogDatabase dogDatabase){
            dogDAO = dogDatabase.dogDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dogDAO.deleteAll();
            return null;
        }
    }
}
