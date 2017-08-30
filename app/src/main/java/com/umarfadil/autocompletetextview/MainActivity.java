package com.umarfadil.autocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.at_klasifikasi)
    AutoCompleteTextView at_klasifikasi;
    @BindView(R.id.hasilKlasifikasi)
    TextView hasilKlasifikasi;
    @BindView(R.id.kodeKlasifikasi)
    TextView kodeKlasifikasi;

    //Klasifikasi
    private ArrayList<Klasifikasi> mKlasifikasiArrayList;
    private Klasifikasi klasifikasi;
    private KlasifikasiAdapter klasifikasiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //AutoCompleteTextView
        mKlasifikasiArrayList = new ArrayList<Klasifikasi>();
        loadKlasifikasi(mKlasifikasiArrayList);
    }

    private void loadKlasifikasi(final ArrayList<Klasifikasi> mKlasifikasiArrayList) {
        AndroidNetworking.post(Api.klasifikasi)
                .addBodyParameter("apitoken", "368589e74ec64600c33f4534d4236772")
                .addBodyParameter("apikey", "384ec6aaf87d4ca8c99139d3ff5f70ac")
                .addBodyParameter("limit", "2000")
                .setTag("Klasifikasi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("Result", response.toString());
                        try {
                            String status = response.getString("result");
                            if (status == "1") {
                                try {
                                    JSONArray klas = response.optJSONArray("data");
                                    Log.d("Result Data", response.getString("data"));
                                    for (int i = 0; i < klas.length(); i++) {
                                        klasifikasi = new Klasifikasi();
                                        klasifikasi.setName(klas.optJSONObject(i).optString("name"));
                                        klasifikasi.setKode(klas.optJSONObject(i).optString("kode"));
                                        mKlasifikasiArrayList.add(klasifikasi);
                                    }

                                    klasifikasiAdapter = new KlasifikasiAdapter(getApplicationContext(), mKlasifikasiArrayList);
                                    at_klasifikasi.setThreshold(1);
                                    at_klasifikasi.setAdapter(klasifikasiAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("Error", "onError errorCode : " + error.getErrorCode());
                            Log.d("Error", "onError errorBody : " + error.getErrorBody());
                            Log.d("Error", "onError errorDetail : " + error.getErrorDetail());

                        } else {
                            Log.d("Error", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }

    public void setHasil(View view) {
        hasilKlasifikasi.setText(klasifikasi.getName());
        kodeKlasifikasi.setText(klasifikasi.getName());
    }
}
