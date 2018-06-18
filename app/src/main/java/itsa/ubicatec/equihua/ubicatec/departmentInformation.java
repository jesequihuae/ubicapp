package itsa.ubicatec.equihua.ubicatec;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import itsa.ubicatec.equihua.ubicatec.BD.DepartmentsContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;

public class departmentInformation extends AppCompatActivity {

    Toolbar toolbar;
    SQLiteDatabase db;
    TextView nombreResponsable;
    TextView correoResponsable;
    TextView telefonoResponsable;
    TextView descripcionDepartamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_information);

        Bundle datos = this.getIntent().getExtras();
        String idDepartment = datos.getString("id", "");

        nombreResponsable = (TextView) findViewById(R.id.nombreResponsable);
        correoResponsable = (TextView) findViewById(R.id.correoResponsable);
        telefonoResponsable = (TextView) findViewById(R.id.telefonoResponsable);
        descripcionDepartamento = (TextView) findViewById(R.id.descripcionResponsable);

        UBT_DBHelper dbHelper = new UBT_DBHelper(this.getApplicationContext());
        db = dbHelper.getReadableDatabase();

        setToolbar();
        getDepartmentById(idDepartment);
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

    private void getDepartmentById(String idDepartment) {
        String condicion = DepartmentsContract.DepartmentsEntry.ID + " = ?";
        String[] valoresCondicion = {idDepartment};

        Cursor cursor = db.query(
                DepartmentsContract.DepartmentsEntry.TABLE_NAME,
                null,
                condicion,
                valoresCondicion,
                null,
                null,
                null
        );

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.DEPARTMENT)));
            nombreResponsable.setText(cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.RESPONSABLE)));
            correoResponsable.setText(cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.EMAIL)));
            telefonoResponsable.setText(cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.CELLPHONE)));
            descripcionDepartamento.setText(cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.INFORMATION)));
        }
    }
}
