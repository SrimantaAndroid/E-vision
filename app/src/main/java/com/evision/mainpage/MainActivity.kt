package com.evision.mainpage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.evision.CartManage.CartActivity
import com.evision.Category.CategoryActivity
import com.evision.ContactUs.ContactUsActivity
import com.evision.Dashboard.DashboardFragment
import com.evision.Login_Registration.LoginActivity
import com.evision.MyAccount.MyAccActivity
import com.evision.ProductList.ProductListActivity
import com.evision.R
import com.evision.Utils.Converter
import com.evision.Utils.ShareData
import com.google.android.material.navigation.NavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jsoup.Jsoup
import java.io.IOException
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainContarctor.Main_View {
    private val REQ_LOGIN: Int = 12
    lateinit var  menuCartItem:MenuItem
var isReadyforCourtCount=false
    lateinit var TXT_username: TextView
    lateinit var TXT_useremail: TextView
    lateinit var nav_view:NavigationView
    lateinit var drawer_layout:DrawerLayout
     lateinit var mFirebaseAnalytics:FirebaseAnalytics
    var currentVersion: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        /*var  bundle= Bundle()
bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "uu");
bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "vghj");
bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        nav_view=findViewById(R.id.nav_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setLogo(R.drawable.logo_24_93)
        drawer_layout=findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.getMenu().getItem(1).setActionView(R.layout.next_menu)
        nav_view.itemIconTintList=null

        TXT_username = nav_view.getHeaderView(0).findViewById(R.id.TXT_Login)
        TXT_useremail = nav_view.getHeaderView(0).findViewById(R.id.TXT_EMAIL)
        hideItem()
        supportFragmentManager.beginTransaction().replace(R.id.container, DashboardFragment.newInstance("", "")).commit()
        TXT_username.setOnClickListener {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQ_LOGIN)
        }
        getCurrentVersion()

    }
    private fun getCurrentVersion() {
        val pm = this.packageManager
        var pInfo: PackageInfo? = null

        try {
            pInfo = pm.getPackageInfo(this.packageName, 0)
        } catch (e1: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        currentVersion = pInfo!!.versionName
        GetVersionCode().execute()

    }

    internal inner class GetVersionCode : AsyncTask<Void, String, String>() {

        override fun doInBackground(vararg voids: Void): String? {
            var newVersion: String? = null
            try {
                val document =
                        Jsoup.connect("https://play.google.com/store/apps/details?id=" + this@MainActivity.packageName + "&hl=en")
                                //  Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.app.astrolab"  + "&hl=en")
                                .timeout(30000)
                                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                .referrer("http://www.google.com")
                                .get()
                if (document != null) {
                    val element = document!!.getElementsContainingOwnText("Current Version")
                    for (ele in element) {
                        if (ele.siblingElements() != null) {
                            val sibElemets = ele.siblingElements()
                            for (sibElemet in sibElemets) {
                                newVersion = sibElemet.text()
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return newVersion

        }


        override fun onPostExecute( onlineVersion: String?) {

            super.onPostExecute(onlineVersion)
           // var onlineVersion="2"
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (java.lang.Float.valueOf(currentVersion) < java.lang.Float.valueOf(onlineVersion)) {
                    //show anything
                    showUpdateDialog()
                }

            }

            // Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    private fun showUpdateDialog() {
        var dialog: Dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.newversionavalible))
        builder.setPositiveButton(
                resources.getText(R.string.update)
        ) { dialog, which ->
            /* startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("market://details?id=com.wecompli")));*/
            val appPackageName = packageName // getPackageName() from Context or Activity object
            try {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                        )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                )
            }

            dialog.dismiss()
        }

        builder.setNegativeButton(
                resources.getString(R.string.cancel),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                    }
                })

        builder.setCancelable(true)
        dialog = builder.show()
        dialog.setCancelable(false)
    }

    private fun LOGINMANAGE() {
        val logindata = ShareData(this).getUser()
        if (logindata != null) {
            ShowItem()
            TXT_useremail.setText(logindata!!.email)
            TXT_username.setText("Hello "+logindata!!.firstName)
            TXT_username.isClickable = false
            if(logindata.cartCount!=null)
            ManageCartView(logindata.cartCount)
            
        }
    }

    override fun onResume() {
        super.onResume()
        if(ShareData(this).getUser()!=null && isReadyforCourtCount)
            LOGINMANAGE()
    }

    override fun onPostResume() {
        super.onPostResume()
//        LOGINMANAGE()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    /*************** MANAGE CART VIEW  */
    fun ManageCartView(i: Int) {
        val inflatedFrame = layoutInflater.inflate(R.layout.cart_layout, null)
        val item = inflatedFrame.findViewById(R.id.TXT_Counter) as TextView
        if (i <= 0)
            item.visibility = View.GONE
        else
            item.visibility = View.VISIBLE
        item.text = i.toString() + ""
        val drawable = BitmapDrawable(resources, Converter.getBitmapFromView(inflatedFrame))
        menuCartItem.setIcon(drawable)
         isReadyforCourtCount=true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menuCartItem = menu.findItem(R.id.action_cart)
        LOGINMANAGE()
        return true
    }

    @SuppressLint("MissingPermission")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_call -> {

                Dexter.withActivity(this)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+507-3021030"))
                                startActivity(intent)
                            }

                            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                                token!!.continuePermissionRequest()
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            }

                        }).check()

                return true
            }
            R.id.action_whatsapp -> {
                if (isWhatsapp()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://api.whatsapp.com/send?phone=+507-6444-7679&text= Guarda nuestro número en tus contactos para recibir nuestra newsletter. Y después envía este mensaje para comenzar.")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please make whatsapp to +507-6444-7679", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.action_cart -> {
                val logindata = ShareData(this).getUser()
                if(logindata==null)
                {
                    startActivityForResult(Intent(this, LoginActivity::class.java), REQ_LOGIN)
                    return true
                }
                if(logindata!!.cartCount!=null)
                startActivity(Intent(this,CartActivity::class.java))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_category -> {
//                supportFragmentManager.beginTransaction().replace(R.id.container, CategoryFragment.newInstance("", "")).commit()
                startActivity(Intent(this,CategoryActivity::class.java))
                return true
            }
            R.id.nav_acc -> {
                startActivity(Intent(this, MyAccActivity::class.java))

            }
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DashboardFragment.newInstance("", "")).commit()

            }
            R.id.nav_signout -> {
                ShareData(this).Logout()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                return true
            }
            R.id.nav_login ->{
                startActivityForResult(Intent(this, LoginActivity::class.java), REQ_LOGIN)
                return true
            }
            R.id.nav_onlinesale ->{
                startActivity(Intent(this, ProductListActivity::class.java).putExtra("pid", "BYONLINENAV").putExtra("cname", resources.getString(R.string.menu_onlinesale)))

            }
            R.id.nav_send -> {
                startActivity(Intent(this, ContactUsActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LOGIN && resultCode == Activity.RESULT_OK) {
            LOGINMANAGE()
        }
    }


    private fun isWhatsapp(): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

    private fun hideItem() {
       val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu = navigationView.getMenu()
        nav_Menu.findItem(R.id.nav_acc).setVisible(false)
        nav_Menu.findItem(R.id.nav_signout).setVisible(false)
//        nav_Menu.findItem(R.id.nav_trackorder).setVisible(false)
        nav_Menu.findItem(R.id.nav_login).setVisible(true)
    }

    private fun ShowItem() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu = navigationView.getMenu()
        nav_Menu.findItem(R.id.nav_acc).setVisible(true)
        nav_Menu.findItem(R.id.nav_signout).setVisible(true)
//        nav_Menu.findItem(R.id.nav_trackorder).setVisible(true)
        nav_Menu.findItem(R.id.nav_login).setVisible(false)
    }
}
