package co.edu.uniautonoma.posgradosapp.Actividades;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import co.edu.uniautonoma.posgradosapp.Helper.HttpJsonParser;
import co.edu.uniautonoma.posgradosapp.R;
import co.edu.uniautonoma.posgradosapp.ViewModels.UbicacionViewModel;
import xdroid.toaster.Toaster;

public class UbicacionActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private Double[] coordenadas = {2.442971, -76.605247};
    private String direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.mpSedes);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }

        UbicacionViewModel viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);
        if (viewModel.getEscuela()!=null){
            viewModel.getEscuela().observe(this, escuela -> {
                coordenadas[0] = escuela.getCoordenada1();
                coordenadas[1] = escuela.getCoordenada2();
                direccion = escuela.getDireccion();
                AgregarMarcador();
            });
        }

        viewModel.getEstado().observe(this, estado ->{
            if (estado)
                mostrarDialog();
            else
                ocultarDialog();
        });
    }

    protected synchronized void buildGoogleApiClient() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void AgregarMarcador(){
        LatLng escuela = new LatLng(coordenadas[0], coordenadas[1]);

        mMap.addMarker(new MarkerOptions()
                .position(escuela)
                .title("Escuela de Posgrados Uniautonoma")
                .snippet(direccion)
        ).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(escuela));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}
