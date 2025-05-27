package com.muibsols.iptracker.Sync;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class DataRepo {
    public static final String TAG = "Repo";

    private ContactsModelDAO contactsModelDAO;

    List<ContactsModel> contactsModelList;

    public DataRepo(Application application) {
        this.contactsModelDAO = BaseDB.getBaseDataDatabase(application).contactsModelDAO();
        contactsModelList = contactsModelDAO.getAllContactModels();

    }

    public List<ContactsModel> getContactsModelList() {
        return this.contactsModelList;
    }

    public void update(ContactsModel contactsModel) {
        new UpdateContactsModelAsyncTask(this.contactsModelDAO).execute(contactsModel);
    }

    public void delete(ContactsModel contactsModel) {
        new DeleteContactsModelAsyncTask(this.contactsModelDAO).execute(contactsModel);
    }

    public void insert(ContactsModel contactsModel) {
        new InsertContactsModelAsyncTask(this.contactsModelDAO).execute(contactsModel);
    }

    public void deleteAllContactModels() {
        new DeleteAllContactsAsyncTask(this.contactsModelDAO).execute();
    }

    public boolean isContactExist(ContactsModel contactsModel) {
        if(contactsModelDAO.isDataExist(contactsModel.getPhoneNumber()) == 1){
            return true;
        }else {
            return false;
        }
    }

    public static class UpdateContactsModelAsyncTask extends AsyncTask<ContactsModel, Void, Void> {
        ContactsModelDAO contactsModelDAO;

        public UpdateContactsModelAsyncTask(ContactsModelDAO contactsModelDAO) {
            this.contactsModelDAO = contactsModelDAO;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(ContactsModel... contactsModels) {
            this.contactsModelDAO.update(contactsModels[0]);
            return null;
        }
    }

    public static class DeleteContactsModelAsyncTask extends AsyncTask<ContactsModel, Void, Void> {
        ContactsModelDAO contactsModelDAO;

        public DeleteContactsModelAsyncTask(ContactsModelDAO contactsModelDAO) {
            this.contactsModelDAO = contactsModelDAO;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(ContactsModel... contactsModels) {
            this.contactsModelDAO.delete(contactsModels[0]);
            return null;
        }
    }

    public static class DeleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {
        ContactsModelDAO contactsModelDAO;

        public DeleteAllContactsAsyncTask(ContactsModelDAO contactsModelDAO) {
            this.contactsModelDAO = contactsModelDAO;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            this.contactsModelDAO.deleteAll();
            return null;
        }
    }

    public static class InsertContactsModelAsyncTask extends AsyncTask<ContactsModel, Void, Void> {
        ContactsModelDAO contactsModelDAO;

        public InsertContactsModelAsyncTask(ContactsModelDAO contactsModelDAO) {
            this.contactsModelDAO = contactsModelDAO;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(ContactsModel... contactsModels) {
            this.contactsModelDAO.insert(contactsModels[0]);
            return null;
        }
    }

}
