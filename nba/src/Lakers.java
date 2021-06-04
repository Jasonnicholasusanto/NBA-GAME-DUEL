public enum Lakers {

    PG("T.Horton-Tucker","D.Schroder"),
    SG("W.Matthews", "B.McLemore", "A.Caruso", "K.Caldwell-Pope"),
    SF("L.James", "J.Dudley"),
    PF("M.Harrel", "M.Morris", "K.Kuzma", "A.Davis"),
    C("A.Gasol", "A.Drummond");

    private String[] name;

    private Lakers(String... name){
        this.name = name;
    }

}
