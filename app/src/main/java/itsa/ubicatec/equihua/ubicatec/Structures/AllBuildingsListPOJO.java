package itsa.ubicatec.equihua.ubicatec.Structures;

/**
 * Created by Jesus on 05/06/2018.
 */

public class AllBuildingsListPOJO {

    int idBuilding;
    boolean isBuilding;
    String nameBuilding;
    String informationBuilding = "";

    public AllBuildingsListPOJO(int idBuilding, boolean isBuilding, String nameBuilding, String informationBuilding) {
        this.idBuilding = idBuilding;
        this.isBuilding = isBuilding;
        this.nameBuilding = nameBuilding;
        this.informationBuilding = informationBuilding;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public String getInformationBuilding() {
        return informationBuilding;
    }
}
