package br.com.luan.myskeletonv2.view.ui.location


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.id.content
import br.com.luan.myskeletonv2.data.model.Machine
import br.com.luan.myskeletonv2.data.model.MachineItem
import br.com.luan.myskeletonv2.push.RegistrationIntentService
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : BaseDrawerActivity() , OnMapReadyCallback, DataRequestActivity.RequestListener, GoogleMap.OnMarkerClickListener {

    lateinit var mapFragment: SupportMapFragment
    private var mMap: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        val servicegcm = Intent(baseContext, RegistrationIntentService::class.java)
        startService(servicegcm)
        this.nearby.visibility = View.GONE



    }

    override fun onResume() {
        super.onResume()

    }

    fun initView(){
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


//        val mapView = mapFragment.getView()

//        change mylocation button position

//        if (mapView?.findViewById(Integer.parseInt("1")) != null) {
//            // Get the button view
//            val locationButton = (mapView.findViewById(Integer.parseInt("1")).getParent() as View).findViewById(Integer.parseInt("2"))
//            // and next place it, on bottom right (as Google Maps app)
//            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
//            // position on right bottom
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
//            layoutParams.setMargins(0, 0, 30, 250)
//        }


    }

    override fun onMapReady(p0: GoogleMap?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)
                    startAnimationLaunch()

                }
            })
        }
        try {
            mMap = p0
//            mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
            mMap!!.setMyLocationEnabled(true)
            getMyLocation()

        } catch (e: SecurityException) {
            onErrorAlert(resources.getString(R.string.errorGps))
        }
    }


    fun setDirections(latitude:String, longitude:String){
        val builder = Uri.Builder()
        builder.scheme("https")
                .authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1")
                .appendQueryParameter("destination", latitude+ "," + longitude)
        val url = builder.build().toString()
        Log.d("Directions", url)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    lateinit var client: FusedLocationProviderClient

    internal var mLocation: Location? = null

    fun getMyLocation() {
        try {
            client = LocationServices.getFusedLocationProviderClient(this)

            client.getLastLocation().addOnSuccessListener(OnSuccessListener<Location> { location ->
                try {
                    val cameraZoom = 13
                    mLocation = location
                    var latLng = LatLng(location.getLatitude(), location.getLongitude());
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, cameraZoom.toFloat()));
//                    MapRequest(this@MainActivity, this@MainActivity).getProfissional(mLocation)


                } catch (e: Exception) {
                    onErrorAlert("Habilite seu GPS para utilizar o aplicativo.")
                    //                        finishAffinity();
                }
            })


        } catch (e: SecurityException) {
            onErrorAlert("Habilite seu GPS para utilizar o aplicativo.")

        }

    }

    private val options = MarkerOptions()

    internal var builder = LatLngBounds.Builder()


    override fun onMarkerClick(marker: Marker?): Boolean {
        onBottomSheetMessage(marker!!.tag as Machine)
        return true
    }

    fun addMarker(latitude: Double, longitude: Double, title: String, snip: String, obj: Machine) {

        var icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.pin);

        options.position(LatLng(latitude, longitude))
        options.title(title)
        options.snippet(snip)
        options.icon(BitmapDescriptorFactory.fromBitmap(icon))
        mMap!!.addMarker(options).tag = obj
        mMap!!.setOnMarkerClickListener(this)
        mMap!!.getUiSettings().setZoomControlsEnabled(true);
        mMap!!.setPadding(0,0,0,280)

        builder.include(LatLng(latitude, longitude))

    }

    internal fun startAnimationLaunch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = parentLayout.getMeasuredWidth() / 2
            val cy = parentLayout.getMeasuredHeight() / 2

            val anim = ViewAnimationUtils.createCircularReveal(content, cx, cy, 50f, content.getWidth().toFloat())

            anim.duration = 1000
            anim.interpolator = AccelerateInterpolator(2f)
            anim.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    DataRequestActivity(this@MainActivity,this@MainActivity).getMachines()


                }
            })

            anim.start()
        }
    }

    var isNearbyShow:Boolean = false;

    override fun onSuccess(machines: List<Machine>) {
        if(machines.size > 0){
            isNearbyShow = true
            this.showNearby.setOnClickListener(View.OnClickListener {  onBottomSheetMessage(machines.get(0))})
            this.closeNearby.setOnClickListener(View.OnClickListener {
                this.nearby.visibility = View.GONE
                isNearbyShow = false
            })
            this.nearby.visibility = View.VISIBLE
            this.addressNearby.text = machines.get(0).locStreet
            for (machine in machines){
                if(machine.locLat != null && machine.locLong != null) {
                    addMarker(machine.locLat!!.toDouble(),machine.locLong!!.toDouble(),machine.locName!!,"",machine)
                }
            }
        }else{
            isNearbyShow = false
            this.nearby.visibility = View.GONE
        }


        //--------------------------------
        isNearbyShow = false
        this.nearby.visibility = View.GONE


    }


    override fun onSuccessItens(itens: List<MachineItem>?) {
    }

    override fun onError(error: String) {
        onErrorAlert(error)
    }

    override fun onSuccessDetails(itens: MachineItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onBottomSheetMessage(machine: Machine) {
        val view = layoutInflater.inflate(R.layout.window_map, null)
        val title = view.findViewById(R.id.title) as TextView
        val desc = view.findViewById(R.id.description) as TextView
        val route = view.findViewById(R.id.location) as RelativeLayout
        val itens = view.findViewById(R.id.itens) as RelativeLayout
        val rating = view.findViewById(R.id.rating) as Button

        title.text = machine.locName
        desc.text = machine.locStreet

        val mBottomSheetDialog = Dialog(this, R.style.MaterialDialogSheet)

        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(true)
        mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()
        bottomLayout.visibility = View.INVISIBLE
        this.nearby.visibility = View.GONE

        rating.setOnClickListener(View.OnClickListener { startActivity(Intent(baseContext,RatingActivity::class.java).putExtra("machine",machine)) })

        route.setOnClickListener(View.OnClickListener {
            setDirections(machine.locLat!!,machine.locLong!!)
        })

        itens.setOnClickListener(View.OnClickListener {
            startActivity(Intent(baseContext,ItensActivity::class.java).putExtra("machine",machine))
        })

        mBottomSheetDialog.setOnCancelListener {
            getMyLocation()
            bottomLayout.visibility = View.VISIBLE
            if(isNearbyShow)
                this.nearby.visibility = View.VISIBLE
        }

        var latLng = LatLng(machine.locLat!!.toDouble(),machine.locLong!!.toDouble());
        var zoom = 17

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom.toFloat()));

    }

}
