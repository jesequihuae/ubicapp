package itsa.ubicatec.equihua.ubicatec;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by Jesus on 26/03/2018.
 */

public class isInternetAvailable {

    Context context;

    /* if(internetConnection.isConnectedMobile())
            Toast.makeText(this, "DATOS MOVILES", Toast.LENGTH_SHORT).show();

        if(internetConnection.isConnectedWifi())
            Toast.makeText(this, "WIFI", Toast.LENGTH_SHORT).show(); */

    /* CONSTRUCTOR */
    public isInternetAvailable(Context context){
        this.context = context;
    }

    public boolean validaConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isConnectedMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /* MUESTRA MENSAJE DE NO CONEXION A INTERNET */
    public void mostrarAlerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle("Conexión a Internet");
        alerta.setMessage("No hay conexión a internet.\nNo hay acceso al mapa");
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alerta.show();
    }

}
