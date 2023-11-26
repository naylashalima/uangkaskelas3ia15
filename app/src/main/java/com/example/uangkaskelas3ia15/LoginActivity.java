package com.example.uangkaskelas3ia15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uangkaskelas3ia15.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Uang Kas kelas 3IA15");
        }
        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.logButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Mohon lengkapi semua field",
                        Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(username).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel user = snapshot.getValue(UserModel.class);
                                if (user != null) {
                                    if (user.getUsername().equals(username) && user.getPassword()
                                        .equals(password)) {
                                        Toast.makeText(LoginActivity.this, "Berhasil login",
                                            Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(LoginActivity.this, Beranda
                                            .class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Password salah!",
                                            Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Akun tidak ditemukan!",
                                        Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LoginActivity.this,
                                        "Terjadi kesalahan " + error.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                            }
                        });
                }
            }
        });
    }
}
