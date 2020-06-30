package com.example.contacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsRepository repository;
    public LiveData<PagedList<Contacts>> pagedListLiveData;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
        pagedListLiveData = repository.getAllContacts();
    }

    public void insert(Contacts contacts){
        repository.insert(contacts);
    }

    public void update(Contacts contacts){
        repository.update(contacts);
    }

    public  void delete(Contacts contacts) {
        repository.delete(contacts);
    }

    public void deleteContacts() {
        repository.deleteAllContacts();
    }

    public LiveData<PagedList<Contacts>> getAllContacts() {
        return pagedListLiveData;
    }

}
