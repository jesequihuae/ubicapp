package itsa.ubicatec.equihua.ubicatec.SYNC;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import itsa.ubicatec.equihua.ubicatec.BD.BuildingsContract;
import itsa.ubicatec.equihua.ubicatec.BD.DepartmentsContract;
import itsa.ubicatec.equihua.ubicatec.BD.FoodbuildingContract;
import itsa.ubicatec.equihua.ubicatec.BD.SchedulesContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.BD.WeekDaysContract;

/**
 * Created by Jesus on 11/05/2018.
 */

public class downloadData extends AsyncTask<Void, Void, Boolean>{

    Context context;
    ProgressDialog progress;
    String WS = "https://jesusequihua.000webhostapp.com/ubicatec/api";
    //String WS = "http://ubicatec.diplomadosdelasep.com.mx/api";
    String TAG = "DOWNLOAD";
    SQLiteDatabase bd;

    public downloadData(Context context) {
        this.context = context;
        Log.d(TAG, "CONSTRUCTOR");
        UBT_DBHelper dbHelper = new UBT_DBHelper(context.getApplicationContext());
        bd = dbHelper.getWritableDatabase();
    }

    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setTitle("Espere por favor");
        progress.setMessage("Obteniendo la información más actual");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        Log.d(TAG, "PRE");
    }

    @Override
    protected Boolean doInBackground(Void... voids){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conection;

        try {
            url = new URL(WS + "/getTables");
            conection = (HttpURLConnection) url.openConnection();

            conection.setReadTimeout(15000);
            conection.setConnectTimeout(15000);
            conection.setRequestMethod("GET");
            int codigoRespuesta = conection.getResponseCode();
            if(codigoRespuesta == HttpsURLConnection.HTTP_OK){
                eliminarDatos();    //LIMPIA LA BD
                insertatWeekDays(); //INSERTAR DIAS DE SEMANA
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                while((line = in.readLine()) != null){
                    stringBuffer.append(line);
                }
                in.close();

                String JSONString = stringBuffer.toString();
                JSONObject obj = new JSONObject(JSONString);

                String Edificios = obj.getString("Edificios");
                String Loncherias = obj.getString("Loncherias");
                String Departamentos = obj.getString("Departamentos");
                String Horarios = obj.getString("Horarios");

                JSONArray EdificiosArray = new JSONArray(Edificios);
                JSONArray LoncheriasArray = new JSONArray(Loncherias);
                JSONArray DepartamentosArray = new JSONArray(Departamentos);
                JSONArray HorariosArray = new JSONArray(Horarios);

                for(int i = 0; i < EdificiosArray.length(); i++) {
                    JSONObject objEdificios = (JSONObject) EdificiosArray.get(i);
                    Log.d(TAG, objEdificios.getString("vImgEdificio"));
                    String[] nombre = objEdificios.getString("vImgEdificio").split("/");
                    if(!objEdificios.getString("imgBase64").toString().equals("default")) {
                        byte[] decodedString = Base64.decode(objEdificios.getString("imgBase64").substring(objEdificios.getString("imgBase64").indexOf(",")+1), Base64.DEFAULT);
                        //String[] nombre = objEdificios.getString("vImgEdificio").split("/");
                        Log.d(TAG, nombre[nombre.length-1]);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        GuardarImagen(nombre[nombre.length-1], decodedByte, 1);
                    }

                    ContentValues valores = new ContentValues();
                    valores.put(BuildingsContract.BuildingsEntry.ID, objEdificios.getInt("idEdificio"));
                    valores.put(BuildingsContract.BuildingsEntry.NAME, objEdificios.getString("vNombre"));
                    valores.put(BuildingsContract.BuildingsEntry.COORDS, objEdificios.getString("vCoordenadas"));
                    valores.put(BuildingsContract.BuildingsEntry.INFORMATION, objEdificios.getString("tInformacion"));
                    valores.put(BuildingsContract.BuildingsEntry.IMAGE, nombre[nombre.length-1]);
                    bd.insert(BuildingsContract.BuildingsEntry.TABLE_NAME, null, valores);
                }

                for(int i = 0; i < LoncheriasArray.length(); i++) {
                    JSONObject objLoncheria = (JSONObject) LoncheriasArray.get(i);
                    String[] nombre = objLoncheria.getString("vImgEdificio").split("/");
                    if(!objLoncheria.getString("imgBase64").toString().equals("default")) {
                        byte[] decodedString = Base64.decode(objLoncheria.getString("imgBase64").substring(objLoncheria.getString("imgBase64").indexOf(",")+1), Base64.DEFAULT);
                        //String[] nombre = objLoncheria.getString("vImgEdificio").split("/");
                        Log.d(TAG, nombre[nombre.length-1]);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        GuardarImagen(nombre[nombre.length-1], decodedByte, 2);
                    }
                    ContentValues valores = new ContentValues();
                    valores.put(FoodbuildingContract.FoodBuildingEntry.ID, objLoncheria.getInt("idEdificio"));
                    valores.put(FoodbuildingContract.FoodBuildingEntry.NAME, objLoncheria.getString("vNombre"));
                    valores.put(FoodbuildingContract.FoodBuildingEntry.COORDS, objLoncheria.getString("vCoordenadas"));
                    valores.put(FoodbuildingContract.FoodBuildingEntry.IMAGE, nombre[nombre.length-1]);
                    bd.insert(FoodbuildingContract.FoodBuildingEntry.TABLE_NAME, null, valores);
                }

                for(int i = 0; i < DepartamentosArray.length(); i++) {
                    JSONObject objDepartamentos = (JSONObject) DepartamentosArray.get(i);
                    String[] nombre = objDepartamentos.getString("vImgResponsable").split("/");
                    if(!objDepartamentos.getString("imgBase64").toString().equals("default")) {
                        byte[] decodedString = Base64.decode(objDepartamentos.getString("imgBase64").substring(objDepartamentos.getString("imgBase64").indexOf(",")+1), Base64.DEFAULT);
                        //String[] nombre = objDepartamentos.getString("vImgResponsable").split("/");
                        Log.d(TAG, nombre[nombre.length-1]);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        GuardarImagen(nombre[nombre.length-1], decodedByte, 3);
                    }
                    ContentValues valores = new ContentValues();
                    valores.put(DepartmentsContract.DepartmentsEntry.ID, objDepartamentos.getInt("idDepartamento"));
                    valores.put(DepartmentsContract.DepartmentsEntry.DEPARTMENT, objDepartamentos.getString("vDepartamento"));
                    valores.put(DepartmentsContract.DepartmentsEntry.EMAIL, objDepartamentos.getString("vCorreoElectronico"));
                    valores.put(DepartmentsContract.DepartmentsEntry.CELLPHONE, objDepartamentos.getString("vTelefono"));
                    valores.put(DepartmentsContract.DepartmentsEntry.RESPONSABLE, objDepartamentos.getString("vResponsable"));
                    valores.put(DepartmentsContract.DepartmentsEntry.IMAGE, nombre[nombre.length-1]);
                    valores.put(DepartmentsContract.DepartmentsEntry.INFORMATION, objDepartamentos.getString("tInformacion"));
                    valores.put(DepartmentsContract.DepartmentsEntry.BUILDING_ID, objDepartamentos.getInt("idEdificio"));
                    bd.insert(DepartmentsContract.DepartmentsEntry.TABLE_NAME, null, valores);
                }

                for(int i = 0; i < HorariosArray.length(); i++) {
                    JSONObject objHorarios = (JSONObject) HorariosArray.get(i);
                    ContentValues valores = new ContentValues();
                    valores.put(SchedulesContract.SchedulesEntry.ID, objHorarios.getInt("idHorario"));
                    valores.put(SchedulesContract.SchedulesEntry.INITIAL_TIME, objHorarios.getString("tHoraInicial"));
                    valores.put(SchedulesContract.SchedulesEntry.END_TIME, objHorarios.getString("tHoraFinal"));
                    valores.put(SchedulesContract.SchedulesEntry.BUILDING_ID, objHorarios.getInt("idEdificio"));
                    valores.put(SchedulesContract.SchedulesEntry.WEEKDAY_ID, objHorarios.getInt("idDiaSemana"));
                    bd.insert(SchedulesContract.SchedulesEntry.TABLE_NAME, null, valores);
                }

                bd.close();
            } else {
                Log.d("DOWNLOAD", "ERROR EN CODIGO " + codigoRespuesta);
            }
        } catch (Exception e) {
            Log.d("DOWNLOAD", "ERROR " + e.getMessage());
        }
        bd.close();
        return null;
    }

    protected void onPostExecute(Boolean result) {
        progress.dismiss();
    }

    /*
        Agregar un tercer parametro entero cuyo valor sea
        1 -> Edificios
        2 -> Loncherias
        3 -> Departamentos
    * */
    public void GuardarImagen(String nombre, Bitmap base64Img, int es) {
        //File file = new File(context.getFilesDir(), "UBICATEC");

        File file = null;
        if(es == 1) {
             file = new File(context.getFilesDir(), "EDIFICIOS");
        }
        else if(es == 2) {
             file = new File(context.getFilesDir(), "LONCHERIAS");
        }
        else if(es == 3) {
             file = new File(context.getFilesDir(), "DEPARTAMENTOS");
        }

        if(!file.exists())
            file.mkdirs();

        File img = new File(file, nombre);
        try{
            FileOutputStream fOut = new FileOutputStream(img);
            base64Img.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void insertatWeekDays() {
        String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        for (int i = 0; i < dias.length; i++) {
            ContentValues valores = new ContentValues();
            valores.put(WeekDaysContract.WeekDaysEntry.ID, i+1);
            valores.put(WeekDaysContract.WeekDaysEntry.DAY, dias[i]);
            bd.insert(WeekDaysContract.WeekDaysEntry.TABLE_NAME, null, valores);
        }
    }

    public void eliminarDatos() {
        bd.delete(SchedulesContract.SchedulesEntry.TABLE_NAME,null,null);
        bd.delete(FoodbuildingContract.FoodBuildingEntry.TABLE_NAME,null,null);
        bd.delete(DepartmentsContract.DepartmentsEntry.TABLE_NAME,null,null);
        bd.delete(BuildingsContract.BuildingsEntry.TABLE_NAME,null,null);
        bd.delete(WeekDaysContract.WeekDaysEntry.TABLE_NAME,null,null);
    }
}
