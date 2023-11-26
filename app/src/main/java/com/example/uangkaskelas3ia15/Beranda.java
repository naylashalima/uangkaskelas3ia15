package com.example.uangkaskelas3ia15;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Beranda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Uang Kas kelas 3IA15");
        }

        ImageView pemasukan = findViewById(R.id.btnpemasukan);
        ImageView pengeluaran = findViewById(R.id.btnpengeluaran);
        ImageView bayar = findViewById(R.id.btnbayar);
        ImageView anggota = findViewById(R.id.btnanggota);

        pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, PemasukanActivity.class);
                startActivity(intent);
            }
        });

        pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, PengeluaranActivity.class);
                startActivity(intent);
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, BayarActivity.class);
                startActivity(intent);
            }
        });

        anggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, AnggotaActivity.class);
                startActivity(intent);
            }
        });
    }
}