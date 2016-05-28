package com.photonic3d.lightbox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.photonic3d.lightbox.fragments.home.HomeFragment;
import com.photonic3d.lightbox.fragments.archiveselector.ArchiveSelectorFragment;
import com.photonic3d.lightbox.fragments.sliceview.SliceViewerFragment;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        SliceViewerFragment.OnFragmentInteractionListener,
        ArchiveSelectorFragment.OnFragmentInteractionListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static final int READ_REQUEST_CODE = 42;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        if (id == R.id.nav_home) {
            // Change to the import fragment
            changeFragment(HomeFragment.TAG);
        }
        else if (id == R.id.nav_slice_view) {
            changeFragment(ArchiveSelectorFragment.TAG);

        }
        else if (id == R.id.nav_add_file) {
            performFileSearch();
            // Chang to the print fragment
//            SliceViewFragment galleryFragment = (SliceViewFragment) fragmentManager.findFragmentByTag(SliceViewFragment.TAG);
//            if (galleryFragment == null) {
//                fragmentManager.beginTransaction().replace(R.id.container, SliceViewFragment.newInstance(id), SliceViewFragment.TAG).commit();
//            }
        }

//        } else if (id == R.id.nav_slideshow) {
//            // Change to the slideshow fragment
//            SlideshowFragment slideShowFragment = (SlideshowFragment) fragmentManager.findFragmentByTag(SlideshowFragment.TAG);
//            if(slideShowFragment == null) {
//                fragmentManager.beginTransaction().replace(R.id.container, SlideshowFragment.newInstance(id), SlideshowFragment.TAG).commit();
//            }
//
//        } else if (id == R.id.nav_manage) {
//            // Change to the slideshow fragment
//            ToolsFragment toolsFragment = (ToolsFragment) fragmentManager.findFragmentByTag(ToolsFragment.TAG);
//            if(toolsFragment == null) {
//                fragmentManager.beginTransaction().replace(R.id.container, ToolsFragment.newInstance(id), ToolsFragment.TAG).commit();
//            }
//
//        } else if (id == R.id.nav_share) {
//            // Make it do something..
//            // Doesn't currently do anything
//
//
//        } else if (id == R.id.nav_send) {
//            // Make it do something else..
//            // Doesn't currently do anything
//
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void changeFragment(String fragmentTag){
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        switch (fragmentTag){
            case HomeFragment.TAG:
                HomeFragment homeFragment= (HomeFragment) fragmentManager.findFragmentByTag(HomeFragment.TAG);
                if(homeFragment == null) {
                    fragmentManager.beginTransaction().replace(R.id.container, HomeFragment.newInstance(0), HomeFragment.TAG).commit();
                }
                break;

            case ArchiveSelectorFragment.TAG:
                ArchiveSelectorFragment archiveSelectorFragment = (ArchiveSelectorFragment) fragmentManager.findFragmentByTag(ArchiveSelectorFragment.TAG);
                if(archiveSelectorFragment == null) {
                    fragmentManager.beginTransaction().replace(R.id.container, ArchiveSelectorFragment.newInstance(0), ArchiveSelectorFragment.TAG).commit();
                }
                break;
            case SliceViewerFragment.TAG:
                SliceViewerFragment sliceViewerFragment = (SliceViewerFragment) fragmentManager.findFragmentByTag(SliceViewerFragment.TAG);
                if(sliceViewerFragment == null) {
                    fragmentManager.beginTransaction().replace(R.id.container, SliceViewerFragment.newInstance("0"), SliceViewerFragment.TAG).commit();
                }
                break;

        }

    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("loader", "Uri: " + uri.toString());
                loadFileIntoApp(uri);
            }
        }
    }


    public void loadFileIntoApp(Uri uri){
        File file = new File(uri.getPath());
        InputStream is = null;
        try {
            File destinationDir = new File(this.getFilesDir(), "archives");
            File destination = new File(destinationDir, FilenameUtils.getName(uri.getPath()));

            is = getContentResolver().openInputStream(uri);
//            FileUtils.copyFileToDirectory(file, this.getFilesDir());
            FileUtils.copyInputStreamToFile(is,destination);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onSectionAttached(int number){

        switch(number) {
            case 1:
//                 mTitle = getString(R.string.title_import);
                break;
            case 2:
                // mTitle = getString(R.string.title_gallery);
                break;
            case 3:
                // mTitle = getString(R.string.title_slideshow);
                break;
            case 4:
                // mTitle = getString(R.string.title_tools);
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // this.onSectionAttached(position);
    }

}
