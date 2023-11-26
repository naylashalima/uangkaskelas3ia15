package com.example.uangkaskelas3ia15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView sudahpunyaakunTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Uang Kas kelas 3IA15");
        }
        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword);
        registerButton = findViewById(R.id.regButton);
        sudahpunyaakunTextView = findViewById(R.id.sudahpunyaakun);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Mohon lengkapi semua field",
                        Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password tidak sesuai",
                        Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> user = new HashMap<>();
                    user.put("username", username);
                    user.put("password", password);
                    FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(username).setValue(user).addOnSuccessListener(
                            unused -> {
                                Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT)
                                    .show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }).addOnFailureListener(
                            e -> Toast.makeText(RegisterActivity.this, "Terjadi kesalahan " + e.getMessage(), Toast.LENGTH_SHORT)
                                .show());
                }
            }
        });
        sudahpunyaakunTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
