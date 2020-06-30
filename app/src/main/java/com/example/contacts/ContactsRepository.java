package com.example.contacts;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactsRepository {

    private static ContactsRepository repository = null;

    private ContactsDao mContactsDao;

    private static int PAGE_SIZE = 15;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public ContactsRepository(Application application) {
        ContactsRoomDatabase db = ContactsRoomDatabase.getDatabase(application);
        mContactsDao = db.contactsDao();
    }

    public static ContactsRepository getRepository(Application application) {
        if(repository == null) {
            synchronized (ContactsRepository.class) {
                if(repository == null) {
                    repository = new ContactsRepository(application);
                }
            }
        }

        return repository;
    }

    public void insert(final Contacts contacts) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactsDao.insert(contacts);
            }
        });
    }


    public void update(final Contacts contacts) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactsDao.update(contacts);
            }
        });
    }

    public void delete(final Contacts contacts) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactsDao.delete(contacts);
            }
        });
    }

    public void deleteAllContacts() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactsDao.deleteAllContacts();
            }
        });

    }

    public LiveData<PagedList<Contacts>> getAllContacts(){
        return new LivePagedListBuilder<>(
                mContactsDao.getAllContacts(), PAGE_SIZE
        ).build();
    }
}