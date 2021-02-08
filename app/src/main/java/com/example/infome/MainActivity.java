package com.example.infome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String name, genPro;
    EditText nameInput;
    EditText genProInput;
    Button submitButton;
    Button fetchButton;
    TextView fetchInfo;
    TextView locationJohn;
    TextView locationJane;

    public DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("user locations").document("John");
    public DocumentReference mDocRef2 = FirebaseFirestore.getInstance().collection("user locations").document("Jane Doe");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        nameInput = (EditText) findViewById(R.id.editName);
        genProInput = (EditText) findViewById(R.id.editGenPro);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = nameInput.getText().toString();
                genPro = genProInput.getText().toString();
                if(name.isEmpty() || genPro.isEmpty()){return;}
                Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put("name", name);
                dataToSave.put("genpro", genPro);
                mDocRef.set(dataToSave);
            }
        });

        fetchInfo = (TextView) findViewById(R.id.fetchInfo);
        fetchButton = (Button) findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener(){
            //@Override
            public void onClick(View v){
                mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    //@Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String name = documentSnapshot.getString("name");
                            String genPro = documentSnapshot.getString("genpro");
                            fetchInfo.setText("Name: " + name + "\n" + "Gender and Pronouns: " + genPro);

                        }
                    }
                });
            }
        });

/*
        public void getLocation(){
            final GeoPoint[] b = new GeoPoint[1];
            mDocRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        b[0] = documentSnapshot.getGeoPoint("location");
                    }
                }
            });
            mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                   if (documentSnapshot.exists()) {
                       GeoPoint a = documentSnapshot.getGeoPoint("location");
                       if (a == b[0]) {
                           //code
                       }

                   }
               }
           }
            );
        }
*/

    }

}