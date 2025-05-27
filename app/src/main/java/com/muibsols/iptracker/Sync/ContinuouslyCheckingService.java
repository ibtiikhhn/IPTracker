package com.muibsols.iptracker.Sync;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.List;

public class ContinuouslyCheckingService extends Worker {


    Context context;
    List<ContactsModel> contactsModelList;
    DataRepo baseDataRepo;

    public ContinuouslyCheckingService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {

        baseDataRepo = new DataRepo((Application) context.getApplicationContext());
        contactsModelList = new ArrayList<>();

        getContacts();


        Data outputData = new Data.Builder().putBoolean("Done", true).build();
        return Result.success(outputData);
    }

    private void getContacts() {
        ContactUtil contactUtil = new ContactUtil(getApplicationContext());
        contactsModelList.addAll(contactUtil.getContacts());
        for (ContactsModel contactsModel : contactsModelList) {
            if (!baseDataRepo.isContactExist(contactsModel)) {
                baseDataRepo.insert(contactsModel);
            }
        }
        StartSync();
        Log.i("HELL", "getContacts: "+"Sync Started");
    }


    public void StartSync() {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest build = new OneTimeWorkRequest.Builder(SyncOnline.class)
                .setConstraints(constraints)
                .build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueueUniqueWork("BaseDataUploadingService", ExistingWorkPolicy.KEEP,build);
    }

}
