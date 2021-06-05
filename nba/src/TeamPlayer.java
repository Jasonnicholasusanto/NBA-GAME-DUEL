/**
 * Class for the players within an NBA team
 */
public class TeamPlayer {
    private final String jersey;
    private final String name;
    private final String firstPos;
    private final String secondPos;
    private final int mid;
    private final int three;
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
