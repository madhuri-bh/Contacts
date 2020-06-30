package com.example.contacts;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contacts.class}, version = 1, exportSchema = false)
public abstract class ContactsRoomDatabase extends RoomDatabase {

    public static final int OTHERS = 0;
    public static final int MALE = 1;
    public static final int FEMALE = 2;

    private static ContactsRoomDatabase INSTANCE = null;

    public abstract ContactsDao contactsDao();

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static ContactsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactsRoomDatabase.class,
                            "ContactsDatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static ContactsRoomDatabase.Callback roomCallBack = new ContactsRoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(INSTANCE).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void, Void, Void> {
        private ContactsDao contactsDao;

        private PopulateDb(ContactsRoomDatabase db) {
            contactsDao = db.contactsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactsDao.insert(new Contacts("Madhuri", "5467876549","abc@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            contactsDao.insert(new Contacts("Madhuri", "5467876549","abc@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            contactsDao.insert(new Contacts("Madhuri", "5467876549","abc@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            contactsDao.insert(new Contacts("Savitha", "5467876549","abc@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            contactsDao.insert(new Contacts("Savitha", "5467876549","xyz@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            contactsDao.insert(new Contacts("Savitha", "5467876549","abc@gmail.com", 19, FEMALE,"Mysuru", "Vvce" ));
            return null;
        }
    }

}
