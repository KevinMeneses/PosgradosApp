package co.edu.uniautonoma.posgradosapp.presentation.ui.ubicacion

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.uniautonoma.posgradosapp.presentation.R
import co.edu.uniautonoma.posgradosapp.presentation.ui.base.BaseActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionActivity : BaseActivity(), OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private var mMap: GoogleMap? = null
    private val coordenadas = arrayOf(2.442971, -76.605247)
    private var direccion: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mpSedes) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.uiSettings.isZoomGesturesEnabled = true
        mMap!!.uiSettings.isCompassEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
            }
        } else {
            buildGoogleApiClient()
        }
        val viewModel = ViewModelProvider(this)[UbicacionViewModel::class.java]
        if (viewModel.escuela != null) {
            viewModel.escuela.observe(this, Observer{
                coordenadas[0] = it.coordenada1
                coordenadas[1] = it.coordenada2
                direccion = it.direccion
                AgregarMarcador()
            })
        }
        viewModel.estado.observe(this, Observer { estado: Boolean -> if (estado) mostrarDialog() else ocultarDialog() })
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        val mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }

    override fun onConnected(bundle: Bundle?) {}
    override fun onConnectionSuspended(i: Int) {}
    override fun onLocationChanged(location: Location) {}
    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    fun AgregarMarcador() {
        val escuela = LatLng(coordenadas[0], coordenadas[1])
        mMap!!.addMarker(MarkerOptions()
                .position(escuela)
                .title("Escuela de Posgrados Uniautonoma")
                .snippet(direccion)
        ).showInfoWindow()
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(escuela))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(17f))
    }
}