package itsa.ubicatec.equihua.ubicatec.BD;

import android.provider.BaseColumns;

/**
 * Created by Jesus on 11/05/2018.
 */

public class FoodbuildingContract {

    private FoodbuildingContract(){}

    /* TABLE VARIABLES */
    public static class FoodBuildingEntry implements BaseColumns {
        public static final String TABLE_NAME      = "tblLoncherias";
        public static final String TABLE_ID        = "idTabla";
        public static final String ID              = "idLoncheria";
        public static final String NAME            = "vNombre";
        public static final String COORDS          = "vCoordenadas";
        public static final String IMAGE           = "vImagenEdificio";
    }

    /* CREATE TABLE STRING */
    public static final String SQL_CREATE_FOODBUILDINGS  =
            "CREATE TABLE " + FoodBuildingEntry.TABLE_NAME + " (" +
                    FoodBuildingEntry.TABLE_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL , " +
                    FoodBuildingEntry.ID              + " INTEGER NOT NULL, " +
                    FoodBuildingEntry.NAME            + " TEXT NOT NULL, " +
                    FoodBuildingEntry.COORDS          + " TEXT NOT NULL, " +
                    FoodBuildingEntry.IMAGE           + " TEXT NULL);";

    /* DELETE TABLE STRING */
    public static final String SQL_DELETE_FOODBUILDINGS =
            "DROP TABLE IF EXISTS " + FoodBuildingEntry.TABLE_NAME;
}
