package com.example.contacts;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactsDao {

    @Insert
    void insert (Contacts contacts);

    @Update
    void update(Contacts contacts);

    @Delete
    void delete(Contacts contacts);

    @Query("SELECT * from contacts_table ORDER BY Name ASC")
    DataSource.Factory<Integer,Contacts> getAllContacts();
    //LiveData<List<Contacts>> getAllContacts();

    @Query("DELETE FROM contacts_table")
    void deleteAllContacts();
}
