package com.example.uangkaskelas3ia15;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.uangkaskelas3ia15.model.PengeluaranModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PengeluaranActivity extends AppCompatActivity {

    String selectedTahun = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Uang Kas kelas 3IA15");
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable the back button
        }

        findViewById(R.id.table_pengeluaran).setVisibility(View.GONE);
        setSpinnerAdapter();
        setupSpinnerAction();
        setupButtonCari();
    }

    private void setupTable(String[] header, List<String[]> data) {
        TableView<String[]> tableView = findViewById(R.id.table_pengeluaran);
        tableView.setColumnCount(2);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(3);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 4);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, header));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, data));
    }

    private void setupButtonCari() {
        findViewById(R.id.btn_cari).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTahun.isEmpty()) {
                    Toast.makeText(PengeluaranActivity.this, "Tahun tidak boleh kosong!",
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading(true);
                FirebaseDatabase.getInstance().getReference().child("pengeluaran").child(selectedTahun)
                    .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int totalPengeluaran = 0;
                                List<String[]> data = new ArrayList<>();
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    // try catch -> to prevent crash when the data is not a correct format
                                    try {
                                        PengeluaranModel pengeluaranModel = ds.getValue(PengeluaranModel.class);
                                        if (pengeluaranModel != null) {
                                            data.add(new String[]{ds.getKey(), Objects.requireNonNull(
                                                pengeluaranModel.getKeterangan()), String.valueOf(
                                                pengeluaranModel.getNominal())});

                                            // try catch -> to prevent crash when the data is not a number
                                            try {
                                                totalPengeluaran += pengeluaranModel.getNominal();
                                            } catch (Exception e) {
                                                // ignore
                                            }
                                        }
                                    } catch (Exception e) {
                                        // ignore
                                    }
                                }
                                if (data.isEmpty()) {
                                    findViewById(R.id.tv_empty_pengeluaran).setVisibility(View.VISIBLE);
                                    findViewById(R.id.table_pengeluaran).setVisibility(View.GONE);
                                    findViewById(R.id.tv_total_pengeluaran).setVisibility(View.GONE);
                                } else {
                                    findViewById(R.id.table_pengeluaran).setVisibility(View.VISIBLE);
                                    findViewById(R.id.tv_empty_pengeluaran).setVisibility(View.GONE);
                                    TextView tvPengeluaran = findViewById(R.id.tv_total_pengeluaran);
                                    tvPengeluaran.setVisibility(View.VISIBLE);
                                    tvPengeluaran.setText("Total pengeluaran " + selectedTahun +" yaitu " + totalPengeluaran);

                                    String[] header = new String[]{"Tanggal", "Keterangan", "Nominal"};
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
        });
    }

    private void setSpinnerAdapter() {
        AutoCompleteTextView spinnerTahun = findViewById(R.id.spinnerTahun);
        spinnerTahun.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_list,
            getResources().getStringArray(R.array.adapter_tahun)));
    }

    private void setupSpinnerAction() {
        AutoCompleteTextView spinnerTahun = findViewById(R.id.spinnerTahun);
        spinnerTahun.setOnItemClickListener((adapterView, view, position, l) -> {
            String[] arrayBulan = getResources().getStringArray(R.array.adapter_tahun);
            selectedTahun = arrayBulan[position];
        });

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