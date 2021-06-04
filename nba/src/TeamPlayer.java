import java.util.Random;

public class TeamPlayer {
    private String jersey;
    private String name;
    private String firstPos;
    private String secondPos;
    private int mid;
    private int three;
    private int stamina;

    public TeamPlayer(String jersey, String name, String firstPos, String secondPos, int mid, int three, int stamina){
        this.jersey = jersey;
        this.name=name;
        this.firstPos=firstPos;
        this.secondPos = secondPos;
        this.mid = mid;
        this.three = three;
        this.stamina = stamina;
    }

    public String getJersey() {
        return jersey;
    }

    public String getName() {
        return name;
    }

    public String getFirstPos() {
        return firstPos;
    }

    public String getSecondPos() {
        return secondPos;
    }

    public int getMid() {
        return mid;
    }

    public int getThree() {
        return three;
    }

    public int getStamina() {
        return stamina;
    }

    public void midRange(){
        this.stamina-=5;
    }

    public void threePoint(){
        this.stamina-=7;
    }


}
