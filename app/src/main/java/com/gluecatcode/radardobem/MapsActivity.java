package com.gluecatcode.radardobem;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.gluecatcode.radardobem.entities.Entidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Entidade[] entidades = new Entidade[]{};
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);



        builder.addInterceptor(new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder().
                                        baseUrl("http://www.radardobem.com.br/mapa/").
                                        client(client).
                                        addConverterFactory(GsonConverterFactory.create(gson)).build();
        RadarService service = retrofit.create(RadarService.class);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                return false;
            }
        });


        Call<Entidade[]> call = service.findEntidades(-3.76999f,-38.52562f);

        call.enqueue(new Callback<Entidade[]>() {
            @Override
            public void onResponse(Call<Entidade[]> call, Response<Entidade[]> response) {
                if(response.isSuccessful()){
                    entidades = response.body();
                    Log.i("Map", response.toString());
                    mapFragment.getMapAsync(MapsActivity.this);
                    Toast.makeText(MapsActivity.this,response.body().length+" instituições encontradas!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Entidade[]> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng center = new LatLng(-3.76999,-38.52562);

        for(Entidade e : entidades) {
            LatLng point = new LatLng(e.getLatitude(),e.getLongitude());
            mMap.addMarker(new MarkerOptions().position(point).title(e.getName()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}

