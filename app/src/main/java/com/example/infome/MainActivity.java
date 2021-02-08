package com.example.infome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("user locations").document("popo");

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

    }

}