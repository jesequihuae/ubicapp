package itsa.ubicatec.equihua.ubicatec;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import itsa.ubicatec.equihua.ubicatec.Adapters.ScheduleListAdapter;
import itsa.ubicatec.equihua.ubicatec.BD.FoodbuildingContract;
import itsa.ubicatec.equihua.ubicatec.BD.SchedulesContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.Structures.SchedulePOJO;

public class foodBuildingInformation extends AppCompatActivity {

    Toolbar toolbar;
    SQLiteDatabase db;
    ImageView ImagenPerfilIV;
    RecyclerView buildingListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_building_information);

        Bundle datos = this.getIntent().getExtras();
        String idBuilding = datos.getString("id", "");

        ImagenPerfilIV = (ImageView) findViewById(R.id.imgBuilding);
        buildingListRV = (RecyclerView) findViewById(R.id.foodbuildingList);

        UBT_DBHelper dbHelper = new UBT_DBHelper(this.getApplicationContext());
        db = dbHelper.getReadableDatabase();

        setToolbar();
        getFoodbuildById(idBuilding);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Cargando...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getFoodbuildById(String idBuilding) {

        String condicion = FoodbuildingContract.FoodBuildingEntry.ID + " = ?";
        String[] valoresCondicion = {idBuilding};

        Cursor cursor = db.query(
                FoodbuildingContract.FoodBuildingEntry.TABLE_NAME,
                null,
                condicion,
                valoresCondicion,
                null,
                null,
                null
        );

        Bitmap imgLoncheria = null;

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            //tituloBuilding = cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.NAME));
            getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.NAME)));
            try {
                File dir = new File(getApplicationContext().getFilesDir(), "/LONCHERIAS");
                FileInputStream fileInputStream =
                        new FileInputStream(dir + "/" + cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.IMAGE)));
                imgLoncheria = BitmapFactory.decodeStream(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                imgLoncheria = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.foodbuildingsdefault);
            }
        }
       ImagenPerfilIV.setImageBitmap(imgLoncheria);

        LinearLayoutManager linearManager;
        List<SchedulePOJO> horarioList = new ArrayList<>();
        linearManager = new LinearLayoutManager(getApplicationContext());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        buildingListRV.setLayoutManager(linearManager);

        condicion = SchedulesContract.SchedulesEntry.BUILDING_ID + " = ?";

        Cursor cursorSchedule = db.query(
                SchedulesContract.SchedulesEntry.TABLE_NAME,
                null,
                condicion,
                valoresCondicion,
                null,
                null,
                null
        );

        for(cursorSchedule.moveToFirst(); !cursorSchedule.isAfterLast(); cursorSchedule.moveToNext()){
            horarioList.add(new SchedulePOJO(
                    cursorSchedule.getInt(cursorSchedule.getColumnIndex(SchedulesContract.SchedulesEntry.WEEKDAY_ID)),
                    cursorSchedule.getString(cursorSchedule.getColumnIndex(SchedulesContract.SchedulesEntry.INITIAL_TIME)),
                    cursorSchedule.getString(cursorSchedule.getColumnIndex(SchedulesContract.SchedulesEntry.END_TIME))
            ));
        }

        ScheduleListAdapter adapter = new ScheduleListAdapter(horarioList);
        buildingListRV.setAdapter(adapter);
    }
}
