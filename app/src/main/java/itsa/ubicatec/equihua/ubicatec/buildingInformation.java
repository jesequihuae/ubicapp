package itsa.ubicatec.equihua.ubicatec;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import itsa.ubicatec.equihua.ubicatec.Adapters.DepartmentListAdapter;
import itsa.ubicatec.equihua.ubicatec.BD.BuildingsContract;
import itsa.ubicatec.equihua.ubicatec.BD.DepartmentsContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.Structures.departmentListPOJO;

public class buildingInformation extends AppCompatActivity {

    Toolbar toolbar;
    SQLiteDatabase db;
    ImageView ImagenPerfilIV;
    RecyclerView buildingListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_information);

        Bundle datos = this.getIntent().getExtras();
        String idBuilding = datos.getString("id", "");

        ImagenPerfilIV = (ImageView) findViewById(R.id.imgBuilding);
        buildingListRV = (RecyclerView) findViewById(R.id.departmentList);

        UBT_DBHelper dbHelper = new UBT_DBHelper(this.getApplicationContext());
        db = dbHelper.getReadableDatabase();

        setToolbar();
        getBuildingById(idBuilding);
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

    private void getBuildingById(String idBuilding) {
        String condicion = BuildingsContract.BuildingsEntry.ID + " = ?";
        String[] valoresCondicion = {idBuilding};

        Cursor cursor = db.query(
                BuildingsContract.BuildingsEntry.TABLE_NAME,
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
            getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.NAME)));
            try {
                File dir = new File(getApplicationContext().getFilesDir(), "/EDIFICIOS");
                FileInputStream fileInputStream =
                        new FileInputStream(dir + "/" + cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.IMAGE)));
                imgLoncheria = BitmapFactory.decodeStream(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                imgLoncheria = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.buildingdefault);
            }
        }
        ImagenPerfilIV.setImageBitmap(imgLoncheria);

        LinearLayoutManager linearManager;
        //List<BuildingStructurePOJO> buildingsListSQLITE = new ArrayList<>();
        List<departmentListPOJO> departmentsSQLITE = new ArrayList<>();

        linearManager = new LinearLayoutManager(getApplicationContext());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        buildingListRV.setLayoutManager(linearManager);

        condicion = DepartmentsContract.DepartmentsEntry.BUILDING_ID + " = ?";

        Cursor cursorDepartment = db.query(
                DepartmentsContract.DepartmentsEntry.TABLE_NAME,
                null,
                condicion,
                valoresCondicion,
                null,
                null,
                null
        );

        Bitmap imgResponsable = null;
        for(cursorDepartment.moveToFirst(); !cursorDepartment.isAfterLast(); cursorDepartment.moveToNext()){
            try {
                File dir = new File(getApplicationContext().getFilesDir(), "/DEPARTAMENTOS");
                FileInputStream fileInputStream =
                        new FileInputStream(dir + "/" + cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.IMAGE)));
                imgResponsable = BitmapFactory.decodeStream(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                imgResponsable = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.departmentdefault);
            }

            departmentsSQLITE.add(new departmentListPOJO(
                    cursorDepartment.getInt(cursorDepartment.getColumnIndex(DepartmentsContract.DepartmentsEntry.ID)),
                    imgResponsable,
                    cursorDepartment.getString(cursorDepartment.getColumnIndex(DepartmentsContract.DepartmentsEntry.DEPARTMENT)),
                    cursorDepartment.getString(cursorDepartment.getColumnIndex(DepartmentsContract.DepartmentsEntry.RESPONSABLE))
            ));
        }

        DepartmentListAdapter adapter = new DepartmentListAdapter(departmentsSQLITE);
        buildingListRV.setAdapter(adapter);
    }
}
