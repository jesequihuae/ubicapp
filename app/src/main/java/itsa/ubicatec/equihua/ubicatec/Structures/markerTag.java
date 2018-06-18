package itsa.ubicatec.equihua.ubicatec.Structures;

/**
 * Created by Jesus on 05/06/2018.
 */

public class markerTag {
    public int IDMarker;
    public boolean isBuilding;
    public String img;
    public String information;

    public markerTag(int IDMarker, boolean isBuilding, String img, String information) {
        this.IDMarker = IDMarker;
        this.isBuilding = isBuilding;
        this.img = img;
        this.information = information;
    }

    public int getIDMarker() {
        return IDMarker;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public String getImg() {
        return img;
    }

    public String getInformation() {
        return information;
    }
}
