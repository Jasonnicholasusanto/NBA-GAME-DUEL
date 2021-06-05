import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * AN NBA GAME: DUEL
 *
 * INFORMATION:
 * NBA DUEL is a two-player game where players take turns to score points and win the game.
 * This game is strategic, allows players to choose wisely to pass the ball to a highly skilled player to make the shot,
 * swap tired players in the lineup to ones which are energized in the bench.
 * Players also need to choose wisely as to who is going to take the next shot by looking at a player's mid-range and
 * three-point stats as this will affect the outcome of the shot; whether the player makes or misses the shot.
 * This is by calculating probability of the shot.
 *
 * Currently, there are only two teams which is the Los Angeles Lakers and the Brooklyn Nets for players to choose from.
 * If you want to change the teams, feel free to change the TEAM_ONE and TEAM_TWO strings below. Feel free to also add
 * your own teams by just creating a new .csv file within this package (the "res" package).
 *
 * The format for the csv file is as follow:
 *
 * jersey number,full-name (can have space),primary position,secondary position,mid-range shot,three-point shot,stamina
 * Please do not add extra spaces. Just add spaces in the names but no spaces between the commas and the information.
 *
 * Also please ignore the repetitive codes and bad formatting as well as redundancy in some parts.
 * This was a fun project to make for my leisure and the game was created within 3 days including the planning.
 * If you think there could be improvements, please feel free to change it for your own fun. Thanks for understanding!
 *
 * Well that is all for the information of the game. I hope you will enjoy it as much as I did making it. Cheers! :)
 */
public class NBADuel {

    /**
     * The entry point for the program. (Just run this and the game will commence!)
     */
    public static void main(String[] args) throws IOException {
        NBADuel game = new NBADuel();
        game.run();
    }

    // The teams for each player
    private static final String TEAM_ONE = "Lakers";
    private static final String TEAM_TWO = "Nets";

    // Constant texts
    private static final String PLAYER_ONE_WIN = " PLAYER ONE TAKES THE CHAMPIONSHIP HOME! ";
    private static final String PLAYER_TWO_WIN = " PLAYER TWO TAKES THE CHAMPIONSHIP HOME! ";
    private static final String TIE_GAME = " SADLY (T.T), NO ONE HAS WON THIS GAME. IT IS A TIE. ";
    private static final String THANKS = " THANK YOU FOR PLAYING ";
    private static final String END = " END OF REGULATION ";

    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_TWO = 2;
    private static final int NAME_LENGTH = 25;

    private int gameLength;
    private final Scanner userIn = new Scanner(System.in);

    private final HashMap<String, TeamPlayer> playersTeamOne = new HashMap<>();
    private final HashMap<String, TeamPlayer> playersTeamTwo = new HashMap<>();
    private final HashMap<String, TeamPlayer> startingFiveP1 = new HashMap<>();
    private final HashMap<String, TeamPlayer> startingFiveP2 = new HashMap<>();


    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int playerOneScore;
    private int playerTwoScore;

    public NBADuel() throws IOException {

        this.loadEnvironment("res/"+TEAM_ONE+".csv", 1);
        this.loadEnvironment("res/"+TEAM_TWO+".csv", 2);
        this.gameLength=0;
        this.playerOneScore=0;
        this.playerTwoScore=0;
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

    /**
     * This method prints the line up for each player.
     * @param list: Input to this method which is the line up itself.
     */
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
     * Load from the teams input file (a csv file)
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

    /**
     * The backbone of the game, where everything comes into play and runs the program.
     */
    public void run(){
        boolean validity=false;
        int turns=30;

        System.out.println("WELCOME TO NBA DUEL! HAVE FUN!");

        while(!validity) {
            System.out.print("\nENTER YOUR PREFERRED GAME LENGTH BETWEEN 2-30 (NUMBER OF TURNS PER PLAYER): ");
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
                System.out.print("TURN: PLAYER ONE");
                System.out.print("  (GAME SCORE: ");
                System.out.println(playerOneScore+" - "+playerTwoScore+")");
                printEqualSigns();
                playerOne.startingFiveLineUp(this, PLAYER_ONE);

                printEqualSigns();
                System.out.print("TURN: PLAYER TWO");
                System.out.print("  (GAME SCORE: ");
                System.out.println(playerOneScore+" - "+playerTwoScore+")");
                printEqualSigns();
                playerTwo.startingFiveLineUp(this, PLAYER_TWO);

            } else {
                printEqualSigns();
                System.out.println("TURN: PLAYER ONE");
                System.out.print("  (GAME SCORE: ");
                System.out.println(playerOneScore+" - "+playerTwoScore+")");
                printEqualSigns();
                playerOne.options(this, PLAYER_ONE);

                printEqualSigns();
                System.out.println("TURN: PLAYER TWO");
                System.out.print("  (GAME SCORE: ");
                System.out.println(playerOneScore+" - "+playerTwoScore+")");
                printEqualSigns();
                playerTwo.options(this, PLAYER_TWO);

            }
            gameLength++;
        }

        printingText("-", END);
        System.out.println("\n                             THE SCORES WERE: "+playerOneScore+" (player 1) - "+
                playerTwoScore+" (player 2)\n");


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

    /**
     * This method prints a formatted text
     * @param symbol: input to this method which is the symbol to create a page break line
     * @param text: input to this method which is the text to be displayed at the console
     */
    public void printingText(String symbol, String text){
        for(int i=0; i<(101-text.length()); i++){
            System.out.print(symbol);
            if(i==(99-text.length())/2) {
                System.out.print(text);
            }
        }
    }

    /**
     * This method prints out the page break (equal signs)
     */
    public void printEqualSigns(){
        for(int x=0;x<101;x++){
            System.out.print("=");
        }
        System.out.println();
    }

    /**
     * This method prints the table's columns
     */
    public void printBorder(){
        System.out.print("|");
    }
}
