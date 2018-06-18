package itsa.ubicatec.equihua.ubicatec.BD;

import android.provider.BaseColumns;

/**
 * Created by Jesus on 14/03/2018.
 *
 * THIS CLASS IS A TABLE REPRESENTATION FOR tblDepartamentos TABLE IN THE APP
 */

public class DepartmentsContract {

    private DepartmentsContract(){}

    /* TABLE VARIABLES */
    public static class DepartmentsEntry implements BaseColumns {
        public static final String TABLE_NAME   = "tblDepartamentos";
        public static final String TABLE_ID     = "idTable";
        public static final String DEPARTMENT   = "vDepartment";
        public static final String ID           = "idDepartamentos";
        public static final String EMAIL        = "vCorreoElectronico";
        public static final String CELLPHONE    = "vTelefono";
        public static final String RESPONSABLE  = "vResponsable";
        public static final String IMAGE        = "vImgResponsable";
        public static final String INFORMATION =  "tInformacion";
        public static final String BUILDING_ID  = "idEdificio";
    }

    /* CREATE TABLE STRING */
    public static final String SQL_CREATE_DEPARTMENTS =
            "CREATE TABLE " + DepartmentsEntry.TABLE_NAME + " (" +
                    DepartmentsEntry.TABLE_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                    DepartmentsEntry.ID             + " INTEGER NOT NULL, " +
                    DepartmentsEntry.DEPARTMENT     + " TEXT NOT NULL, " +
                    DepartmentsEntry.EMAIL          + " TEXT NOT NULL, " +
                    DepartmentsEntry.CELLPHONE      + " TEXT NULL, " +
                    DepartmentsEntry.INFORMATION    + " TEXT NULL, " +
                    DepartmentsEntry.RESPONSABLE    + " TEXT NOT NULL, " +
                    DepartmentsEntry.IMAGE          + " TEXT NULL, " +
                    DepartmentsEntry.BUILDING_ID    + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + DepartmentsEntry.BUILDING_ID + ") REFERENCES " + BuildingsContract.BuildingsEntry.TABLE_NAME + " (" + BuildingsContract.BuildingsEntry.ID + "));";

    /* DELETE TABLE STRING */
    public static final String SQL_DELETE_DEPARTMENTS =
            "DROP TABLE IF EXISTS " + DepartmentsEntry.TABLE_NAME;

}
