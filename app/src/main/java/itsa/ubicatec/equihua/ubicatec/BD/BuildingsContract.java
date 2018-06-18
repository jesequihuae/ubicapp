package itsa.ubicatec.equihua.ubicatec.BD;

import android.provider.BaseColumns;

/**
 * Created by Jesus on 14/03/2018.
 *
 *  THIS CLASS IS A TABLE REPRESENTATION FOR tblEdificios TABLE IN THE APP
 */

public class BuildingsContract {

    private BuildingsContract(){};

    /* TABLE VARIABLES */
    public static class BuildingsEntry implements BaseColumns {
        public static final String TABLE_NAME      = "tblEdificios";
        public static final String TABLE_ID        = "idTabla";
        public static final String ID              = "idEdificio";
        public static final String NAME            = "vNombre";
        public static final String COORDS          = "vCoordenadas";
        public static final String INFORMATION     = "tInformacion";
        public static final String IMAGE           = "bImgDefault";
    }

    /* CREATE TABLE STRING */
    public static final String SQL_CREATE_BUILDINGS  =
            "CREATE TABLE " + BuildingsEntry.TABLE_NAME + " (" +
                    BuildingsEntry.TABLE_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                    BuildingsEntry.ID              + " INTEGER NOT NULL, " +
                    BuildingsEntry.NAME            + " TEXT NOT NULL, " +
                    BuildingsEntry.COORDS          + " TEXT NOT NULL, " +
                    BuildingsEntry.INFORMATION     + " TEXT NULL, " +
                    BuildingsEntry.IMAGE      + " TEXT NULL);";

    /* DELETE TABLE STRING */
    public static final String SQL_DELETE_BUILDINGS =
            "DROP TABLE IF EXISTS " + BuildingsEntry.TABLE_NAME;

}
