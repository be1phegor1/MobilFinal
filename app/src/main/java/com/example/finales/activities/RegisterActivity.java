package com.example.finales.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.finales.R;
import com.example.finales.models.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    EditText first, last, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first = findViewById(R.id.first);
        last = findViewById(R.id.last);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);



    }

    public void Login(View v){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void Register(View v){
        String first = this.first.getText().toString();
        String last = this.last.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        if(first.isEmpty() || last.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
                            String uid - task.getResult().getUser().getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            UserModel userModel = new UserModel(first, last, email);
                            db.collection("users").document(uid).set(userModel).addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "User added to database", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(this, "Error on adding user to database", Toast.LENGTH_SHORT).show();
                            });


                        } else {
                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error on registering new user", Toast.LENGTH_SHORT).show();
                    }
            ;
        }




        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}