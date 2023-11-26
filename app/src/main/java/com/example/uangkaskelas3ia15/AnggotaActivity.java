package com.example.uangkaskelas3ia15;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnggotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Uang Kas kelas 3IA15");
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable the back button
        }

        findViewById(R.id.table_anggota).setVisibility(View.GONE);
        getAnggota();
    }

    private void getAnggota() {
        showLoading(true);
        FirebaseDatabase.getInstance().getReference().child("anggota")
            .addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String[]> data = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            // try catch -> to prevent crash when the data is not a correct format
                            try {
                                data.add(new String[]{ds.getKey(), Objects.requireNonNull(
                                    ds.getValue()).toString()});
                            } catch (Exception e) {
                                // ignore
                            }
                        }
                        if (data.isEmpty()) {
                            findViewById(R.id.table_anggota).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.table_anggota).setVisibility(View.VISIBLE);
                            String[] header = new String[]{"Nama Anggota"};
                            setupTable(header, data);
                        }
                        showLoading(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showLoading(false);
                    }
                });
    }

    private void setupTable(String[] header, List<String[]> data) {
        TableView<String[]> tableView = findViewById(R.id.table_anggota);
        tableView.setColumnCount(1);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, header));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, data));
    }

    private void showLoading(boolean isShow) {
        if (isShow) {
            findViewById(R.id.pbLoading).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.pbLoading).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}