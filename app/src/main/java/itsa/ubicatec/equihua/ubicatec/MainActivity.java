package itsa.ubicatec.equihua.ubicatec;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.SYNC.downloadData;

public class MainActivity extends AppCompatActivity {

    UBT_DBHelper dbHelper = new UBT_DBHelper(this);

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_map,
            R.drawable.ic_list
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UBT_DBHelper dbHelper = new UBT_DBHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        isInternetAvailable internetConnection = new isInternetAvailable(this);

        if(internetConnection.validaConexionInternet()){
            tabLayout.getTabAt(0).select();
        } else {
            internetConnection.mostrarAlerta();
            tabLayout.getTabAt(1).select();
        }

        setupTabIcons();


        //if(!checkDataBase(getApplicationContext().getDatabasePath("ubtc.db").toString())) {
            new downloadData(this).execute();
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInternetAvailable internetConnection = new isInternetAvailable(this);
        if(internetConnection.validaConexionInternet()){
            tabLayout.getTabAt(0).select();
        } else {
            internetConnection.mostrarAlerta();
            tabLayout.getTabAt(1).select();
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new mapView(), "Mapa");
        adapter.addFragment(new buildingsList(), "Lista");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkDataBase(String Database_path) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Log.e("Error", "No existe la base de datos " + e.getMessage());
        }
        return checkDB != null;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                    new downloadData(this).execute();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
