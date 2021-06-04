import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * An example Bagel game.
 */
public class NBADuel {
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_TWO = 2;
    private static final int NAME_LENGTH = 25;
    private static final String PLAYER_ONE_WIN = "PLAYER ONE TAKES THE CHAMPIONSHIP HOME!";
    private static final String PLAYER_TWO_WIN = "PLAYER TWO TAKES THE CHAMPIONSHIP HOME!";
    private static final String TIE_GAME = "SADLY (T.T), NO ONE HAS WON THIS GAME. IT IS A TIE.";
    private static final String THANKS = " THANK YOU FOR PLAYING ";

    private int gameLength;
    private final Scanner userIn = new Scanner(System.in);

    private final HashMap<String, TeamPlayer> playersTeamOne = new HashMap<>();
    private final HashMap<String, TeamPlayer> playersTeamTwo = new HashMap<>();
    private final HashMap<String, TeamPlayer> startingFiveP1 = new HashMap<>();
    private final HashMap<String, TeamPlayer> startingFiveP2 = new HashMap<>();


    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int playerOneScore=0;
    private int playerTwoScore=0;

    public NBADuel() throws IOException {

        this.loadEnvironment("res/Lakers.csv", 1);
        this.loadEnvironment("res/Nets.csv", 2);
        this.gameLength=0;
    }

    public HashMap<String, TeamPlayer> getPlayersTeamOne() {
        return playersTeamOne;
    }

    public HashMap<String, TeamPlayer> getPlayersTeamTwo() {
        return playersTeamTwo;
    }

    public HashMap<String, TeamPlayer> getStartingFiveP1() {
        return startingFiveP1;
    }

    public HashMap<String, TeamPlayer> getStartingFiveP2() {
        return startingFiveP2;
    }

    public void setPlayerOneScore(int score) {
        this.playerOneScore = playerOneScore+score;
    }

    public void setPlayerTwoScore(int score) {
        this.playerTwoScore = playerTwoScore+score;
    }

    /**
     * This method prints out a list of players in a team
     * @param list: input to this method which is of type HashMap
     */
    public void printList(HashMap<String, TeamPlayer> list){
        System.out.println("|JERSEY NUMBER|          NAME           |PRIMARY|SECONDARY|MID-RANGE|THREE-POINT|ENERGY|");
        for(int i=0; i<101; i++){
            System.out.print("-");
        }
        System.out.println();

        for (String key : list.keySet()) {
            TeamPlayer player = list.get(key);

            printBorder();
            System.out.print(player.getJersey());
            if(player.getJersey().length()==1){
                System.out.print("            ");
            } else {
                System.out.print("           ");
            }

            printBorder();
            System.out.print(player.getName());
            for(int i=0; i<(NAME_LENGTH-player.getName().length()); i++){
                System.out.print(" ");
            }

            printBorder();
            System.out.print(player.getFirstPos());
            if(player.getFirstPos().length() == 1){
                System.out.print("      ");
            } else if (player.getFirstPos().length() == 2){
                System.out.print("     ");
            } else {
                System.out.print("    ");
            }

            printBorder();
            System.out.print(player.getSecondPos());
            if(player.getSecondPos().length() == 1){
                System.out.print("        ");
            } else if (player.getSecondPos().length() == 2){
                System.out.print("       ");
            } else {
                System.out.print("      ");
            }

            printBorder();
            System.out.println(player.getMid()+"       "+"|"+player.getThree()+"         "+"|"+player.getStamina()+"    "
                    +"|");

        }
    }

    public void printLineUp(HashMap<String, TeamPlayer> list){
        String[] positions = {"PG", "SG", "SF", "PF", "C"};
        int index = 0;
        System.out.println("|POSITION|JERSEY NUMBER|          NAME           |PRIMARY|SECONDARY|MID-RANGE|THREE-POINT|ENERGY|");
        for(int i=0; i<101; i++){
            System.out.print("-");
        }
        System.out.println();

        TeamPlayer player;

        while(index<5) {
            player = list.get(positions[index]);

            printBorder();
            System.out.print(positions[index]);
            if(positions[index].length()==1){
                System.out.print(":      ");
            } else {
                System.out.print(":     ");
            }

            printBorder();
            System.out.print(player.getJersey());
            if(player.getJersey().length()==1){
                System.out.print("            ");
            } else {
                System.out.print("           ");
            }

            printBorder();
            System.out.print(player.getName());
            for (int i = 0; i < (NAME_LENGTH-player.getName().length());i++){
                System.out.print(" ");
            }

            printBorder();
            System.out.print(player.getFirstPos());
            if(player.getFirstPos().length() == 1){
                System.out.print("      ");
            } else if (player.getFirstPos().length() == 2){
                System.out.print("     ");
            } else {
                System.out.print("    ");
            }

            printBorder();
            System.out.print(player.getSecondPos());
            if(player.getSecondPos().length() == 1){
                System.out.print("        ");
            } else if (player.getSecondPos().length() == 2){
                System.out.print("       ");
            } else {
                System.out.print("      ");
            }

            printBorder();
            System.out.println(player.getMid()+"       "+"|"+player.getThree()+"         "+"|"+player.getStamina()+"    "
                    +"|");

            index++;
        }

    }

    public boolean countPlayers(HashMap<String, TeamPlayer> list, String pos){
        int count=0;

        for (String key : list.keySet()) {
            TeamPlayer player = list.get(key);
            if(player.getFirstPos().equals(pos)||player.getSecondPos().equals(pos)){
                count++;
            }
        }

        return count > 0;
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename, int num){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");

                String jersey = info[0];
                String name = info[1];
                String firstPos = info[2];
                String secondPos = info[3];
                String mid = info[4];
                String three = info[5];
                String stamina = info[6];

                if(num == PLAYER_ONE) {
                    TeamPlayer player = new TeamPlayer(jersey, name, firstPos, secondPos,
                            Integer.parseInt(mid), Integer.parseInt(three), Integer.parseInt(stamina));
                    playersTeamOne.put(player.getJersey(), player);
                } else if(num == PLAYER_TWO) {
                    TeamPlayer player = new TeamPlayer(jersey, name, firstPos, secondPos,
                            Integer.parseInt(mid), Integer.parseInt(three), Integer.parseInt(stamina));
                    playersTeamTwo.put(player.getJersey(), player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void printEqualSigns(){
        for(int x=0;x<101;x++){
            System.out.print("=");
        }
        System.out.println();
    }

    public void printBorder(){
        System.out.print("|");
    }

    public void run(){
        boolean validity=false;
        int turns=30;

        while(!validity) {
            System.out.print("ENTER YOUR PREFERRED GAME LENGTH BETWEEN 2-30 (NUMBER OF TURNS PER PLAYER): ");
            String len = userIn.nextLine();

            try
            {
                turns = Integer.parseInt(len);
                if(turns<=30 && turns>=2) {
                    System.out.println("THE GAME WILL COMMENCE WITH "+turns+" TURNS FOR EACH PLAYER.");
                    printEqualSigns();
                    validity=true;
                } else {
                    System.out.println("* INVALID INPUT: Number of turns must be between 2-30.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("* INVALID INPUT: "+len+" is not a valid integer.");
            }
        }

        while(gameLength<=turns) {
            if(gameLength==1) {
                printEqualSigns();
            }
            System.out.println("                                   ROUND: " + gameLength + " OUT OF " + turns);
            System.out.print("\n                                   GAME SCORE: ");
            System.out.println(playerOneScore+" - "+playerTwoScore);

            if(gameLength == 0){

                printEqualSigns();
                System.out.println("TURN: PLAYER ONE");
                printEqualSigns();
                playerOne.startingFiveLineUp(this, PLAYER_ONE);

                //printEqualSigns();
                //System.out.println("TURN: PLAYER TWO");
                //printEqualSigns();
                //playerTwo.startingFiveLineUp(this, PLAYER_TWO);

            } else {
                printEqualSigns();
                System.out.println("TURN: PLAYER ONE");
                printEqualSigns();
                playerOne.options(this, PLAYER_ONE);

            }
            gameLength++;
        }

        System.out.println("\n----------------------------------------- END OF REGULATION " +
                "-----------------------------------------");
        System.out.println("\n                                       THE SCORES WERE: "+playerOneScore+" - "+
                playerTwoScore+"\n");


        if(playerOneScore>playerTwoScore){
            printingText("~", PLAYER_ONE_WIN);
            System.out.println();
        } else if (playerOneScore<playerTwoScore){
            printingText("~", PLAYER_TWO_WIN);
            System.out.println();
        } else {
            printingText("~", TIE_GAME);
            System.out.println();
        }

        printingText("*", THANKS);
        System.out.println();

        printingText(" ", "CREATED BY JASON NICHOLAS SUSANTO. FINISHED AT 4TH JUNE 2021.");
        System.out.println();

    }

    public void printingText(String symbol, String text){
        for(int i=0; i<(99-text.length()); i++){
            System.out.print(symbol);
            if(i==(99-text.length())/2) {
                System.out.print(text);
            }
        }
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        NBADuel game = new NBADuel();
        game.run();
    }
}
