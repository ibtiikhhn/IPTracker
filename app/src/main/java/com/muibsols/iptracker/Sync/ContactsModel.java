package com.muibsols.iptracker.Sync;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts_model")
public class ContactsModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;
    private String displayName;
    private String phoneNumber;



    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof ContactsModel)){
            //implicit null check
            return false;
        }
        return (this.displayName + this.phoneNumber).equals(((ContactsModel) o).displayName + ((ContactsModel) o).phoneNumber);
    }

    @Override
    public int hashCode(){
        return this.displayName.hashCode()+this.phoneNumber.hashCode();
    }

//    public String toString() {
////        return "ContactsInfo{contactId='" + this.contactId + '\'' + ", displayName='" + this.displayName + '\'' + ", phoneNumber='" + this.phoneNumber + '\'' + '}';
//    }
}
