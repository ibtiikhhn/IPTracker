package com.muibsols.iptracker.Sync;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactUtil {
    public static final String TAG = "ContactUtil";

    Context context;
    List<ContactsModel> contactsModelList;

    public ContactUtil(Context context) {
        this.context = context;
        contactsModelList = new ArrayList<ContactsModel>();
    }

    public List<ContactsModel> getContacts(){
        ContentResolver contentResolver = context.getContentResolver();
        String contactId = null;
        String displayName = null;
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    ContactsModel contactsModel = new ContactsModel();
                    contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

//                    contactsInfo.setContactId(contactId);
                    contactsModel.setDisplayName(displayName);

                    Cursor phoneCursor = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsModel.setPhoneNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsModelList.add(contactsModel);
                }
            }
        }
        cursor.close();
        return contactsModelList;
    }
}
