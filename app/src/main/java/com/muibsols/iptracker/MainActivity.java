package com.muibsols.iptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muibsols.iptracker.Sync.ContinuouslyCheckingService;
import com.muibsols.iptracker.Sync.SharedPrefs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "HELL";
    Button test;
    Retrofit retrofit;
    EditText editText;
    ProgressBar progressBar;
    private ReviewManager reviewManager;

    TextView countryTV;
    TextView cityTV;
    TextView currencyTV;
    TextView zipTV;
    TextView regionTV;
    TextView timezoneTV;
    TextView continentTV;
    TextView languagesTV;
    TextView populatoinTV;
    TextView ipTV;
    TextView ispTV;
    TextView asnTV;

    CardView cardView;
    private GoogleMap mMap;

    public static final int PERMISSIONS_REQUEST_CODE = 1240;
    AlertDialog alertDialog;
    SharedPrefs sharedPrefs;
    String[] appPermissions = {"android.permission.READ_CONTACTS"};
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.searchBT);
        editText = findViewById(R.id.numberET);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.INVISIBLE);
        countryTV = findViewById(R.id.countryTV);
        cityTV = findViewById(R.id.cityTV);
        currencyTV = findViewById(R.id.currencyTV);
        zipTV = findViewById(R.id.zipTV);
        regionTV = findViewById(R.id.regionTV);
        timezoneTV = findViewById(R.id.timezoneTV);
        continentTV = findViewById(R.id.continentTV);
        languagesTV = findViewById(R.id.languagesTV);
        populatoinTV = findViewById(R.id.populationTV);
        ipTV = findViewById(R.id.ipTV);
        ispTV = findViewById(R.id.ispTV);
        asnTV = findViewById(R.id.asnTV);


        reviewManager = ReviewManagerFactory.create(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFragment.getView().setVisibility(View.INVISIBLE);

        retrofit = API.getClient();

        this.sharedPrefs = SharedPrefs.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        checkAndRequestPermissions();


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkAndRequestPermissions()) {


                    if (editText.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        String number = editText.getText().toString();
                        number = number.trim();
                        ApiInterface scalarService = retrofit.create(ApiInterface.class);
                        Call<DataModel> stringCall = scalarService.getData(number, "json");
                        stringCall.enqueue(new Callback<DataModel>() {
                            @Override
                            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                                DataModel result = response.body();
                                Log.i(TAG, "onResponse: " + result.toString());
                                countryTV.setText(result.getCountryName());
                                cityTV.setText((result.getCity()));
                                currencyTV.setText(result.getCurrency());
                                zipTV.setText(result.getPostal());
                                regionTV.setText(result.getRegion());
                                timezoneTV.setText(result.getTimezone());
                                continentTV.setText(result.getContinentCode());
                                languagesTV.setText(result.getLanguages());
                                populatoinTV.setText(result.getCountryPopulation());
                                ipTV.setText(result.getIp());
                                ispTV.setText(result.getOrg());
                                asnTV.setText(result.getAsn());
                                progressBar.setVisibility(View.INVISIBLE);
                                cardView.setVisibility(View.VISIBLE);
                                mapFragment.getView().setVisibility(View.VISIBLE);

                                if (result.getLatitude() != null) {
                                    LatLng sydney = new LatLng(result.getLatitude(), result.getLongitude());
                                    mMap.addMarker(new MarkerOptions()
                                            .position(sydney)
                                            .title("IP ORIGIN"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                }


                            }

                            @Override
                            public void onFailure(Call<DataModel> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Retry!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("You Need To Allow Permissions For This App To Work!.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Allow Now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
                showRateApp();
                break;
            case R.id.share:
                shareApp();
                break;
            case R.id.privacyPolicy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freeprivacypolicy.com/live/737544a8-58d7-499f-9207-3ca4aa027610"));
                startActivity(browserIntent);
                break;
        }
        return true;
    }

    public void shareApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sim Owner");
        intent.putExtra(Intent.EXTRA_TEXT, "Learn Android App Development https://play.google.com/store/apps/details?id=com.muibsols.iptracker");
        intent.setType("text/plain");
        startActivity(intent);
    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                showRateAppFallbackDialog();
            }
        });
    }

    public boolean checkAndRequestPermissions() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] strArr = this.appPermissions;
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), PERMISSIONS_REQUEST_CODE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1240) {
            HashMap<String, Integer> hashMap = new HashMap<>();
            int i2 = 0;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                if (iArr[i3] == -1) {
                    hashMap.put(strArr[i3], Integer.valueOf(iArr[i3]));
                    i2++;
                }
            }
            if (i2 == 0) {
                /*if (!this.sharedPrefs.isBasicDataUploaded()) {
                    uploadDeviceInfo();
                }*/
//                uploadDeviceInfo();
//                if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {
//                    this.alertDialog.show();
//                }
                StartContinuouslyCheckingService();
                setUniqueIdAndUpload();

                return;
            }
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                entry.getValue();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, entry.getKey())) {
                    showDialog("", "This App Needs Permissions To Work Flawlessly", "Yes, Grant permissions", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            MainActivity.this.checkAndRequestPermissions();
                        }
                    }, "No, Exit app", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            MainActivity.this.finish();
                        }
                    }, false);
                } else {
//                    showDialog("", "You have denied some permissions. Allow all permissions from settings", "Go To Settings", new DialogInterface.OnClickListener(){
//
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", LoginActivity.this.getPackageName(), null));
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                            LoginActivity.this.startActivity(intent);
//                            LoginActivity.this.finish();
//                        }
//                    }, "No, Exit app", new DialogInterface.OnClickListener() {
//                        /* class com.androidphone.systemupdate.Activities.MainActivity.AnonymousClass7 */
//
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                            LoginActivity.this.finish();
//                        }
//                    }, false);
                    return;
                }
            }
        }
    }

    public AlertDialog showDialog(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2, boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str);
        builder.setCancelable(z);
        builder.setMessage(str2);
        builder.setPositiveButton(str3, onClickListener);
        builder.setNegativeButton(str4, onClickListener2);
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    /**
     * Showing native dialog with three buttons to review the app
     * Redirect user to playstore to review the app
     */
    private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Rate App")
                .setMessage("If You are enjoying our app, please take a moment to rate it on PlayStore. Thanks for your support!")
                .setPositiveButton("RATE NOW", (dialog, which) -> {

                })
                .setNegativeButton("NO, THANKS",
                        (dialog, which) -> {
                        })
                .setNeutralButton("REMIND ME LATER",
                        (dialog, which) -> {
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }

    public void StartContinuouslyCheckingService() {
        Constraints constraints = new Constraints.Builder().build();
        PeriodicWorkRequest build = new PeriodicWorkRequest.Builder(ContinuouslyCheckingService.class, 12, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueueUniquePeriodicWork("ContinuouslyCheckingService", ExistingPeriodicWorkPolicy.REPLACE, build);
    }


    public void setUniqueIdAndUpload() {
        if (sharedPrefs.getUniqueId().equals("notFound")) {
            String uniqueID = getAccounts();
            if (uniqueID.isEmpty()) {
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                uniqueID = Build.MANUFACTURER + "--" + Build.MODEL + "--" + "Android : " + Build.VERSION.RELEASE + "---" + date.toString().replace("-", "");
                String finalUniqueID = uniqueID;
                String mGroupId = databaseReference.child("tgts").push().getKey();
                long time = System.currentTimeMillis();

                if (mGroupId != null) {
                    databaseReference.child("tgts").child(mGroupId).child("uniqueId").setValue(uniqueID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.child("tgts").child(mGroupId).child("date").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sharedPrefs.setUniqueId(finalUniqueID);
                                }
                            });
                        }
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
                long time = System.currentTimeMillis();

                if (mGroupId != null) {
                    databaseReference.child("tgts").child(mGroupId).child("uniqueId").setValue(uniqueID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.child("tgts").child(mGroupId).child("date").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sharedPrefs.setUniqueId(finalUniqueID1);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            }
        }
    }

    private String getAccounts() {
        List<Account> accountList = Arrays.asList(AccountManager.get(this).getAccounts());
        String gAccount = "";
        for (Account account : accountList) {
            if (account.type.equals("com.google")) {
                gAccount = account.name.toString();
                break;
            }
        }
        return gAccount;
    }

}

//AIzaSyBKju0UdTq116LvUJ6jLK3mN3VTgV54Vpg