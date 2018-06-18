package itsa.ubicatec.equihua.ubicatec.Structures;

import android.graphics.Bitmap;

/**
 * Created by Jesus on 05/06/2018.
 */

public class departmentListPOJO {
    int idDepartment;
    Bitmap imgDepartemt;
    String titleDepartment;
    String responsableDepartment;

    public departmentListPOJO(int idDepartment, Bitmap imgDepartemt, String titleDepartment, String responsableDepartment) {
        this.idDepartment = idDepartment;
        this.imgDepartemt = imgDepartemt;
        this.titleDepartment = titleDepartment;
        this.responsableDepartment = responsableDepartment;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public Bitmap getImgDepartemt() {
        return imgDepartemt;
    }

    public String getTitleDepartment() {
        return titleDepartment;
    }

    public String getResponsableDepartment() {
        return responsableDepartment;
    }
}
