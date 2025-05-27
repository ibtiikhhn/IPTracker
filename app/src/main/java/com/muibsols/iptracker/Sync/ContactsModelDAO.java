package com.muibsols.iptracker.Sync;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactsModelDAO {
    @Delete
    void delete(ContactsModel contactsModel);

    @Query("DELETE from contacts_model")
    void deleteAll();
    @Query("SELECT * FROM contacts_model")
    List<ContactsModel> getAllContactModels();
    @Insert
    void insert(ContactsModel contactsModel);
    @Update
    void update(ContactsModel contactsModel);

    @Query("SELECT EXISTS (SELECT * FROM contacts_model WHERE phoneNumber = :phoneNumber)")
    int isDataExist(String phoneNumber);
}
