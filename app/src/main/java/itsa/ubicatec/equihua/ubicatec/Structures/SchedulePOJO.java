package itsa.ubicatec.equihua.ubicatec.Structures;

/**
 * Created by Jesus on 05/06/2018.
 */

public class SchedulePOJO {

    String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    String dia;
    String horaInicio;
    String horaFinal;

    public SchedulePOJO(int dia, String horaInicio, String horaFinal) {
        this.dia = dias[dia-1];
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public String getDia() {
        return dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }
}
