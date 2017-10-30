package com.example.vy.trycanvas;

import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vy.trycanvas.Draw3D.Draw3DManager;
import com.example.vy.trycanvas.fragments.dialogs.FragmentChooseFractalDialog;
import com.example.vy.trycanvas.fragments.dialogs.FragmentChooseLineTypeDialog;
import com.example.vy.trycanvas.fragments.dialogs.FragmentChooseNodeNumDialog;
import com.example.vy.trycanvas.fragments.FragmentField;
import com.example.vy.trycanvas.fragments.dialogs.FragmentChooseTrimDialog;
import com.example.vy.trycanvas.fragments.dialogs.FragmentMenuFABDialog;
import com.example.vy.trycanvas.fragments.dialogs.FragmentMenuProjectionsPropertiesDialog;
import com.example.vy.trycanvas.fragments.dialogs.FragmentMenuZoomDialog;
import com.example.vy.trycanvas.util.ConfigList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    private Draw3DManager draw3DManager;
    private Controller controller;
    final String LOG_TAG = "VY_LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration configuration = getResources().getConfiguration();
        ConfigList.widthTablet = configuration.screenWidthDp;
        ConfigList.heightTablet = configuration.screenHeightDp;

        Toast.makeText(this, String.valueOf(configuration.screenWidthDp), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(configuration.screenHeightDp), Toast.LENGTH_SHORT).show();


        Log.d(LOG_TAG,"START");

        /*
        * Create controller(pull him appContext)
        * */
        context = this;
        draw3DManager = new Draw3DManager();
        controller = new Controller(context, getSupportFragmentManager(),draw3DManager);


        /*
        * Toolbar settings
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        * Start fragment field
        * */
        getSupportFragmentManager().beginTransaction().add(R.id.surface, new FragmentField()).commit();


        /*
        * Fabs listeners
        * */
        FloatingActionButton fabClear = (FloatingActionButton) findViewById(R.id.fabClear);
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.clear();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fabSelect = (FloatingActionButton) findViewById(R.id.fabSelect);
        fabSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setSelectOperation();
            }
        });
        FloatingActionButton fabZoom = (FloatingActionButton) findViewById(R.id.fabZoom);
        fabZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentMenuZoomDialog fragmentMenuZoomDialog = new FragmentMenuZoomDialog();
                fragmentMenuZoomDialog.setController(controller);
                fragmentMenuZoomDialog.show(getSupportFragmentManager(),"custom");
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FragmentMenuFABDialog().show(getSupportFragmentManager(),"custom");EZZEPIZZI
            }
        });


        /*
        * NavifationDrawer settings
        * */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
    * При выборе определенного пункта в конктроллере устанавливается
     * текущаю операция, далее окнтроллер уже сам знает какое действие
     * в случае чего нужно делать
    * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        switch (id){
            case R.id.nav_clear:
                controller.clear();
                break;
            case R.id.nav_mosaic:
                controller.setMozaikOperation();
                break;
            case R.id.nav_line:
                FragmentChooseLineTypeDialog fragmentChooseLineTypeDialog = new FragmentChooseLineTypeDialog();
                fragmentChooseLineTypeDialog.setController(controller);
                fragmentChooseLineTypeDialog.show(getSupportFragmentManager(),"custom");
                break;
            case R.id.nav_round:
                controller.setRoundOperation();
                break;
            case R.id.nav_rect:
                controller.setRectangleOperation();
                break;
            case R.id.nav_polygon3:
                controller.setPolygon3Operation();
                break;
            case R.id.nav_polygon:
                new FragmentChooseNodeNumDialog().show(getSupportFragmentManager(),"custom");
                controller.setPolygonNOperation();
                break;
            case R.id.nav_zatrav:
                controller.setZatravOperation();
                break;
            case R.id.nav_pen:
                controller.setPenOperation();
                break;
            case R.id.nav_fill_rect:
                controller.setFillRectOperation();
                break;
            case R.id.nav_zatrav_polygon_grad:
                new FragmentChooseNodeNumDialog().show(getSupportFragmentManager(),"custom");
                controller.setFillPolygonNGradOperation();
                break;
            case R.id.nav_zatrav_polygon_static:
                new FragmentChooseNodeNumDialog().show(getSupportFragmentManager(),"custom");
                controller.setFillPolygonNStaticOperation();
                break;
            case R.id.nav_bezier:
                controller.setBezierOperation();
                break;

            case R.id.nav_model:
                controller.modelHeadOperation();
                break;
            case R.id.nav_model_static:
                controller.modelHeadStaticOperation();
                break;
            case R.id.nav_model_random:
                controller.modelHeadRandomOperation();
                break;

            case R.id.nav_ellipse:
                controller.setEllipseOperation();
                break;
            case R.id.nav_triangle:
                controller.setTriangle();
                break;

            case R.id.nav_save:
                controller.setSave();
                break;
            case R.id.nav_open:
                controller.setOpen();
                break;

            case R.id.nav_trim:
                FragmentChooseTrimDialog fragmentChooseTrimDialog = new FragmentChooseTrimDialog();
                fragmentChooseTrimDialog.setController(controller);
                fragmentChooseTrimDialog.setFragmentManager(getSupportFragmentManager());
                fragmentChooseTrimDialog.show(getSupportFragmentManager(),"custom");
                break;


            case R.id.nav_projection:
                FragmentMenuProjectionsPropertiesDialog fragmentMenuProjectionsPropertiesDialog = new FragmentMenuProjectionsPropertiesDialog();
                fragmentMenuProjectionsPropertiesDialog.setController(controller);
                fragmentMenuProjectionsPropertiesDialog.setContext(context);
                fragmentMenuProjectionsPropertiesDialog.setDraw3DManager(draw3DManager);
                controller.setProjectionOperation();
                fragmentMenuProjectionsPropertiesDialog.show(getSupportFragmentManager(),"custom");
                break;


            case R.id.nav_fractal:
                FragmentChooseFractalDialog fragmentChooseFractalDialog = new FragmentChooseFractalDialog();
                fragmentChooseFractalDialog.setController(controller);
                fragmentChooseFractalDialog.show(getSupportFragmentManager(),"custom");
                break;



            case R.id.nav_test:
                controller.setTESTOperation();
                break;

        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
