import org.lwjgl.system.CallbackI;

import java.util.Random;
import java.util.Scanner;

public class Player {
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_TWO = 2;
    private static final int MID_SCORE = 2;
    private static final int THREE_SCORE = 3;
    private static final String MID_RANGE = "2";
    private static final String THREE_POINT = "3";
    private static final int PG = 0;
    private static final int SG = 1;
    private static final int SF = 2;
    private static final int PF = 3;
    private static final int C = 4;

    private final Scanner userInput = new Scanner(System.in);
    private final Random random = new Random();
    private String benched;
    private String played;
    private TeamPlayer playerWithBall;
    private String playerWithBallPos;
    private boolean choiceValidity;

    public void options(NBADuel game, int player) {

        this.choiceValidity=false;

        System.out.println("YOUR CURRENT LINE UP:");
        printDashedLines();
        if(player==PLAYER_ONE) {
            game.printLineUp(game.getStartingFiveP1());
            this.playerWithBallPos = ball(game, player);
            this.playerWithBall = game.getStartingFiveP1().get(playerWithBallPos);
        } else {
            game.printLineUp(game.getStartingFiveP2());
            this.playerWithBallPos = ball(game, player);
            this.playerWithBall = game.getStartingFiveP2().get(playerWithBallPos);
        }

        while(!choiceValidity) {
            printDashedLines();
            System.out.println("\nThe ball is currently with "+playerWithBall.getName());
            printDashedLines();
            System.out.println("+ PLAYER'S CHOICES: CHOOSE ONE");
            System.out.println("Swap a player: Press 1");
            System.out.println("Pass ball:     Press 2");
            System.out.println("Shoot ball:    Press 3");
            System.out.println();
            System.out.print("Enter your choice here: ");

            String input = userInput.nextLine();

            switch (input) {
                case "1" -> {
                    this.swapPlayer(game, player);
                    this.choiceValidity = true;
                }
                case "2" -> this.passBall(game, player);
                case "3" -> this.shootBall(game, player);
                default -> {
                    System.out.println("* Invalid input: Please enter numbers between 1-3.");
                    System.out.println();
                }
            }
        }
        System.out.println();
        printEqualSigns();
        System.out.println("END OF TURN");
        printEqualSigns();
        System.out.println();
    }

    public void passBall(NBADuel game, int player){
        boolean invalidity=true;

        System.out.println("+ SELECT THE PLAYER CURRENTLY IN YOUR LINE UP TO GET THE BALL.");
        System.out.print("ENTER YOUR CHOICE HERE (PLAYER LINE UP POSITION): ");

        while(invalidity) {
            String in = userInput.nextLine();
            if (player == 1) {
                if (game.getStartingFiveP1().containsKey(in)) {
                    if(game.getStartingFiveP1().get(in)!=playerWithBall){
                        this.playerWithBall = game.getStartingFiveP1().get(in);
                        this.playerWithBallPos = in;
                        invalidity=false;
                    } else {
                        System.out.println("* ERROR: Player position entered, currently possesses the ball.");
                        System.out.print("PLEASE ENTER ANOTHER PLAYER'S POSITION IN YOUR LINEUP WITHOUT THE BALL: ");
                    }
                } else {
                    System.out.println("* INVALID INPUT: Please enter a valid position.");
                    System.out.print("INPUT PLAYER POSITION CURRENTLY IN LINE UP HERE: ");
                }
            } else {
                if(game.getStartingFiveP2().containsKey(in)){
                    if(game.getStartingFiveP2().get(in)!=playerWithBall){
                        this.playerWithBall = game.getStartingFiveP2().get(in);
                        this.playerWithBallPos = in;
                        invalidity=false;
                    } else {
                        System.out.println("* ERROR: Player position entered, currently possesses the ball.");
                        System.out.print("PLEASE ENTER ANOTHER PLAYER'S POSITION IN YOUR LINEUP WITHOUT THE BALL: ");
                    }
                } else {
                    System.out.println("* INVALID INPUT: Please enter a valid position.");
                }
            }
        }


    }

    /**
     * This method randomizes the ball possession of a player every turn.
     * @param game: input to this method which is the game itself.
     * @param player: input to this method which is the player user number.
     * @return String: which is the player's position in the lineup.
     */
    public String ball(NBADuel game, int player){

        int pos = random.nextInt(4);

        if(player==PLAYER_ONE){
            if(pos==PG){
                return "PG";
            } else if (pos==SG){
                return "SG";
            } else if (pos==SF){
                return "SF";
            } else if (pos==PF){
                return "PF";
            } else {
                return "C";
            }
        } else {
            if(pos==PG){
                return "PG";
            } else if (pos==SG){
                return "SG";
            } else if (pos==SF){
                return "SF";
            } else if (pos==PF){
                return "PF";
            } else {
                return "C";
            }
        }
    }

    public void shootBall(NBADuel game, int player){
        boolean invalidity=true;

        while(invalidity) {
            if (player == PLAYER_ONE) {
                if (game.getStartingFiveP1().get(playerWithBallPos).getStamina() >= 5) {
                    System.out.println();
                    System.out.println(playerWithBall.getName() + " is going to shoot the ball.");
                    System.out.println();
                    System.out.println("+ PLEASE CHOOSE THE SHOT RANGE +");
                    System.out.println("MID-RANGE (player energy - 5): Press 2");
                    System.out.println("THREE-POINT (player energy - 7): Press 3\n");

                    System.out.print("ENTER YOUR CHOICE HERE (2 OR 3): ");
                    String shotChoice = userInput.nextLine();

                    if (shotChoice.equals(MID_RANGE)) {
                        game.getStartingFiveP1().get(playerWithBallPos).midRange();
                        invalidity=false;
                        this.choiceValidity=true;
                        String name = game.getStartingFiveP1().get(playerWithBallPos).getName();
                        int n = random.nextInt(3);
                        System.out.println();

                        // To check if the player scored
                        if(scoreProbability(game.getStartingFiveP1().get(playerWithBallPos).getMid())){
                            game.setPlayerOneScore(MID_SCORE);
                            switch (n) {
                                case (0) -> System.out.println(name + " WITH THE DUNK!!!");
                                case (1) -> System.out.println(name + " WITH THE OPEN LAYUP!");
                                case (2) -> System.out.println(name + " DRIVES THROUGH THE TIGHT D AND SCORES THE 2!");
                                default -> System.out.println(name + " SHOOTS THE MID-RANGE SHOT AND MAKES IT!");
                            }
                        } else {
                            switch (n) {
                                case (0) -> System.out.println(name + " MISSES THE EASY TWO");
                                case (1) -> System.out.println(name + " DID NOT MAKE IT, OWH NOOO, TIGHT D!");
                                case (2) -> System.out.println(name + " DRIVES THROUGH THE TIGHT D AND GETS REJECTED!");
                                default -> System.out.println(name + " BLOCKED HARD!!");
                            }
                        }
                    } else if (shotChoice.equals(THREE_POINT)) {
                        if (game.getStartingFiveP1().get(playerWithBallPos).getStamina() >= 7) {
                            game.getStartingFiveP1().get(playerWithBallPos).threePoint();
                            invalidity=false;
                            this.choiceValidity=true;
                            String name = game.getStartingFiveP1().get(playerWithBallPos).getName();
                            int n = random.nextInt(3);
                            game.getStartingFiveP1().get(playerWithBallPos).threePoint();
                            System.out.println();

                            // To check if the player scored
                            if(scoreProbability(game.getStartingFiveP1().get(playerWithBallPos).getThree())){
                                game.setPlayerOneScore(THREE_SCORE);
                                switch (n) {
                                    case (0) -> System.out.println(name + " NOTHING BUT THE NET! SWISH!");
                                    case (1) -> System.out.println(name + " WITH THE CLEAN THREE!");
                                    case (2) -> System.out.println(name + " SHOOTS! AND RECEIVES THE THREE!!");
                                    default -> System.out.println(name + " WITH NO HESITATION, AND CASHES IT IN!");
                                }
                            } else{
                                switch (n) {
                                    case (0) -> System.out.println(name + " TRIES, BUT FAILS THE THREE.");
                                    case (1) -> System.out.println(name + " SHOOTS, AND THE BALL FLIES MILES AWAY.");
                                    case (2) -> System.out.println(name + " RELEASES, AND HE WISHES! MEH FAR OFF!");
                                    default -> System.out.println(name + " WILL HAVE BETTER LUCK NEXT TIME..");
                                }
                            }
                        } else {
                            System.out.println(game.getStartingFiveP1().get(playerWithBallPos).getName() +
                                    " does not have enough energy to make the three.");
                            System.out.println("Please pass the ball to another player to make the three or shoot a mid-range shot.");
                            invalidity=false;
                            this.choiceValidity=false;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* Invalid input: Please enter either 2 (MID-RANGE) or 3 (THREE-POINT).");
                    }
                } else {
                    System.out.println(game.getStartingFiveP1().get(playerWithBallPos).getName() + " does not have enough energy to make a shot.\n");
                    System.out.println(("Please pass the ball to another player to make the shot or swap players."));
                    invalidity=false;
                    this.choiceValidity=false;
                }
            } else {
                if (game.getStartingFiveP2().get(playerWithBallPos).getStamina() >= 5) {
                    System.out.println();
                    System.out.println(playerWithBall.getName() + " is going to shoot the ball.");
                    System.out.println();
                    System.out.println("+ PLEASE CHOOSE THE SHOT RANGE +");
                    System.out.println("MID-RANGE (player energy - 5): Press 2");
                    System.out.println("THREE-POINT (player energy - 7): Press 3\n");

                    System.out.print("ENTER YOUR CHOICE HERE (2 OR 3): ");
                    String shotChoice = userInput.nextLine();

                    if (shotChoice.equals(MID_RANGE)) {
                        game.getStartingFiveP2().get(playerWithBallPos).midRange();
                        invalidity=false;
                        this.choiceValidity=true;
                        String name = game.getStartingFiveP2().get(playerWithBallPos).getName();
                        int n = random.nextInt(3);
                        System.out.println();

                        // To check if the player scored
                        if(scoreProbability(game.getStartingFiveP2().get(playerWithBallPos).getMid())){
                            game.setPlayerTwoScore(MID_SCORE);
                            switch (n) {
                                case (0) -> System.out.println(name + " WITH THE DUNK!!!");
                                case (1) -> System.out.println(name + " WITH THE OPEN LAYUP!");
                                case (2) -> System.out.println(name + " DRIVES THROUGH THE TIGHT D AND SCORES THE 2!");
                                default -> System.out.println(name + " SHOOTS THE MID-RANGE SHOT AND MAKES IT!");
                            }
                        } else {
                            switch (n) {
                                case (0) -> System.out.println(name + " MISSES THE EASY TWO");
                                case (1) -> System.out.println(name + " DID NOT MAKE IT, OWH NOOO, TIGHT D!");
                                case (2) -> System.out.println(name + " DRIVES THROUGH THE TIGHT D AND GETS REJECTED!");
                                default -> System.out.println(name + " BLOCKED HARD!!");
                            }
                        }
                    } else if (shotChoice.equals(THREE_POINT)) {
                        if (game.getStartingFiveP2().get(playerWithBallPos).getStamina() >= 7) {
                            game.getStartingFiveP2().get(playerWithBallPos).threePoint();
                            invalidity=false;
                            this.choiceValidity=true;
                            String name = game.getStartingFiveP2().get(playerWithBallPos).getName();
                            int n = random.nextInt(3);
                            game.getStartingFiveP2().get(playerWithBallPos).threePoint();
                            System.out.println();

                            // To check if the player scored
                            if(scoreProbability(game.getStartingFiveP2().get(playerWithBallPos).getThree())){
                                game.setPlayerTwoScore(THREE_SCORE);
                                switch (n) {
                                    case (0) -> System.out.println(name + " MAKES THE THREE!! SWISH!");
                                    case (1) -> System.out.println(name + " WITH THE CLEAN THREE!");
                                    case (2) -> System.out.println(name + " SHOOTS! AND RECEIVES THE THREE!!");
                                    default -> System.out.println(name + " WITH NO HESITATION, AND CASHES IT!");
                                }
                            } else{
                                switch (n) {
                                    case (0) -> System.out.println(name + " TRIES, BUT FAILS THE THREE.");
                                    case (1) -> System.out.println(name + " SHOOTS, AND THE BALL FLIES MILES AWAY.");
                                    case (2) -> System.out.println(name + " RELEASES, AND HE WISHES! MEH FAR OFF!");
                                    default -> System.out.println(name + " WILL HAVE BETTER LUCK NEXT TIME..");
                                }
                            }
                        } else {
                            System.out.println(game.getStartingFiveP2().get(playerWithBallPos).getName() +
                                    " does not have enough energy to make the three.");
                            System.out.println("Please pass the ball to another player to make the three or shoot a mid-range shot.");
                            invalidity=false;
                            this.choiceValidity=false;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* Invalid input: Please enter either 2 (MID-RANGE) or 3 (THREE-POINT).");
                    }
                } else {
                    System.out.println(game.getStartingFiveP2().get(playerWithBallPos).getName() + " does not have enough energy to make a shot.\n");
                    System.out.println(("Please pass the ball to another player to make the shot or swap players."));
                    invalidity=false;
                    this.choiceValidity=false;
                }
            }
        }
    }

    /**
     * This method swaps a player from the current line up with one in the bench
     * @param game: input to this method which is the game itself
     * @param player: input to this method which is the player number
     */
    public void swapPlayer(NBADuel game, int player){
        boolean invalidityOne = true;
        boolean invalidityTwo;

        while(invalidityOne) {
            if (player == PLAYER_ONE) {
                System.out.println();
                printDashedLines();
                System.out.println("PLAYERS CURRENTLY ON COURT:");
                printDashedLines();
                game.printLineUp(game.getStartingFiveP1());

                System.out.println();
                System.out.print("+ SELECT A PLAYER YOU WANT TO BENCH BY ENTERING THEIR POSITION: ");
                String court = userInput.nextLine();

                if(game.getStartingFiveP1().containsKey(court)) {
                    if (game.countPlayers(game.getPlayersTeamOne(),court)) {
                        this.benched = game.getStartingFiveP1().get(court).getName();
                        System.out.println("YOU HAVE CHOSEN THIS PLAYER TO BENCH: " + benched);
                        System.out.println();

                        printDashedLines();
                        System.out.println("PLAYERS CURRENTLY ON BENCH:");
                        printDashedLines();
                        game.printList(game.getPlayersTeamOne());

                        invalidityOne = false;
                        invalidityTwo = true;

                        while (invalidityTwo) {
                            System.out.println();
                            System.out.print("+ SELECT A PLAYER YOU WANT TO PLAY BY ENTERING THEIR JERSEY NUMBER: ");
                            String bench = userInput.nextLine();
                            if (game.getPlayersTeamOne().containsKey(bench)) {
                                if (game.getPlayersTeamOne().get(bench).getFirstPos().equals(court)) {
                                    this.played = game.getPlayersTeamOne().get(bench).getName();
                                    // Adding the player from the court back to the bench
                                    game.getPlayersTeamOne().put(game.getStartingFiveP1().get(court).getJersey(),
                                            game.getStartingFiveP1().get(court));

                                    // Deleting the player from the line up
                                    game.getStartingFiveP1().remove(court);

                                    // Adding the player from the bench to the line up
                                    game.getStartingFiveP1().put(game.getPlayersTeamOne().get(bench).getFirstPos(),
                                            game.getPlayersTeamOne().get(bench));

                                    // Deleting the player from the bench
                                    game.getPlayersTeamOne().remove(bench);
                                    System.out.println();
                                    System.out.println("You have swapped " + benched + " with " + played + ".");
                                    invalidityTwo = false;

                                } else if (game.getPlayersTeamOne().get(bench).getSecondPos().equals(court)) {
                                    this.played = game.getPlayersTeamOne().get(bench).getName();
                                    // Adding the player from the court back to the bench
                                    game.getPlayersTeamOne().put(game.getStartingFiveP1().get(court).getJersey(),
                                            game.getStartingFiveP1().get(court));

                                    // Deleting the player from the line up
                                    game.getStartingFiveP1().remove(court);

                                    // Adding the player from the bench to the line up
                                    game.getStartingFiveP1().put(game.getPlayersTeamOne().get(bench).getSecondPos(),
                                            game.getPlayersTeamOne().get(bench));

                                    // Deleting the player from the bench
                                    game.getPlayersTeamOne().remove(bench);
                                    System.out.println();
                                    System.out.println("You have swapped " + benched + " with " + played + ".");
                                    invalidityTwo = false;

                                } else {
                                    System.out.println("* Player cannot play as a " + court + ": Please enter another player.");
                                }
                            } else {
                                System.out.println("* Invalid input: Please enter a valid position.");
                            }
                        }
                    } else{
                        System.out.println("* Error: There are no players left in bench to substitute this position. " +
                                "Please select another position to swap.");
                    }
                } else {
                    System.out.println("* Invalid input: Please enter a valid player position.s");
                }

            } else if (player == PLAYER_TWO) {
                System.out.println();
                printDashedLines();
                System.out.println("PLAYERS CURRENTLY ON COURT:");
                printDashedLines();
                game.printLineUp(game.getStartingFiveP2());

                System.out.println();
                System.out.print("+ SELECT A PLAYER YOU WANT TO BENCH BY ENTERING THEIR POSITION: ");
                String court = userInput.nextLine();

                if (game.getStartingFiveP2().containsKey(court)) {
                    if (game.countPlayers(game.getPlayersTeamTwo(), court)) {
                        this.benched = game.getStartingFiveP2().get(court).getName();
                        System.out.println("YOU HAVE CHOSEN THIS PLAYER TO BENCH: " + benched);
                        System.out.println();

                        printDashedLines();
                        System.out.println("PLAYERS CURRENTLY ON BENCH:");
                        printDashedLines();
                        game.printList(game.getPlayersTeamTwo());

                        invalidityOne = false;
                        invalidityTwo = true;

                        while (invalidityTwo) {
                            System.out.println();
                            System.out.print("+ SELECT A PLAYER YOU WANT TO PLAY BY ENTERING THEIR JERSEY NUMBER: ");
                            String bench = userInput.nextLine();
                            if (game.getPlayersTeamTwo().containsKey(bench)) {
                                if (game.getPlayersTeamTwo().get(bench).getFirstPos().equals(court)) {
                                    this.played = game.getPlayersTeamTwo().get(bench).getName();
                                    // Adding the player from the court back to the bench
                                    game.getPlayersTeamTwo().put(game.getStartingFiveP2().get(court).getJersey(),
                                            game.getStartingFiveP2().get(court));

                                    // Deleting the player from the line up
                                    game.getStartingFiveP2().remove(court);

                                    // Adding the player from the bench to the line up
                                    game.getStartingFiveP2().put(game.getPlayersTeamTwo().get(bench).getFirstPos(),
                                            game.getPlayersTeamTwo().get(bench));

                                    // Deleting the player from the bench
                                    game.getPlayersTeamTwo().remove(bench);
                                    System.out.println();
                                    System.out.println("You have swapped " + benched + " with " + played + ".");
                                    invalidityTwo = false;

                                } else if (game.getPlayersTeamTwo().get(bench).getSecondPos().equals(court)) {
                                    this.played = game.getPlayersTeamTwo().get(bench).getName();
                                    // Adding the player from the court back to the bench
                                    game.getPlayersTeamTwo().put(game.getStartingFiveP2().get(court).getJersey(),
                                            game.getStartingFiveP2().get(court));

                                    // Deleting the player from the line up
                                    game.getStartingFiveP2().remove(court);

                                    // Adding the player from the bench to the line up
                                    game.getStartingFiveP2().put(game.getPlayersTeamTwo().get(bench).getSecondPos(),
                                            game.getPlayersTeamTwo().get(bench));

                                    // Deleting the player from the bench
                                    game.getPlayersTeamTwo().remove(bench);
                                    System.out.println();
                                    System.out.println("You have swapped " + benched + " with " + played + ".");
                                    invalidityTwo = false;

                                } else {
                                    System.out.println("* Player cannot play as a " + court + ": Please enter another player.");
                                }
                            } else {
                                System.out.println("* Invalid input: Please enter a valid position.");
                            }
                        }
                    } else {
                        System.out.println("* Error: There are no players left in bench to substitute this position. " +
                                "Please select another position to swap.");
                    }
                } else {
                    System.out.println("* Invalid input: Please enter a valid player position.s");
                }
            }
        }



    }

    public void startingFiveLineUp(NBADuel game, int player){

        if(player == PLAYER_ONE) {

            System.out.println("LIST OF PLAYERS IN YOUR TEAM:");
            game.printList(game.getPlayersTeamOne());
            System.out.println();
            printDashedLines();
            System.out.println("+ PLAYER 1 PLEASE CHOOSE YOUR STARTING 5 PLAYERS BY JERSEY NUMBER");

            adjustStarting(game,"PG", player);
            adjustStarting(game,"SG", player);
            adjustStarting(game,"SF", player);
            adjustStarting(game,"PF", player);
            adjustStarting(game,"C", player);

            System.out.println();
            printDashedLines();
            System.out.println("PLAYER 1 STARTING FIVE LINEUP:");
            printDashedLines();

            game.printLineUp(game.getStartingFiveP1());

            printDashedLines();
            System.out.println("PLAYER 1 BENCH:");
            printDashedLines();
            game.printList(game.getPlayersTeamOne());
            System.out.println();

        } else if (player == PLAYER_TWO){

            System.out.println("LIST OF PLAYERS IN YOUR TEAM:");
            game.printList(game.getPlayersTeamTwo());
            System.out.println();
            printDashedLines();
            System.out.println("+ PLAYER 2 PLEASE CHOOSE YOUR STARTING 5 PLAYERS BY JERSEY NUMBER");

            adjustStarting(game,"PG", player);
            adjustStarting(game,"SG", player);
            adjustStarting(game,"SF", player);
            adjustStarting(game,"PF", player);
            adjustStarting(game,"C", player);

            System.out.println();
            printDashedLines();
            System.out.println("PLAYER 2 STARTING FIVE LINEUP:");
            printDashedLines();

            game.printLineUp(game.getStartingFiveP2());

            printDashedLines();
            System.out.println("PLAYER 2 BENCH:");
            printDashedLines();

            game.printList(game.getPlayersTeamTwo());
            System.out.println();
        }
    }

    public void printDashedLines(){
        for(int x=0;x<101;x++){
            System.out.print("-");
        }
        System.out.println();
    }

    public void printEqualSigns(){
        for(int x=0;x<101;x++){
            System.out.print("=");
        }
        System.out.println();
    }

    public void adjustStarting(NBADuel game, String pos, int player){
        boolean invalidity = true;

        while(invalidity) {
            System.out.print(pos + ": ");
            String jerseyNum = userInput.nextLine();

            if (player == PLAYER_ONE) {
                if (game.getPlayersTeamOne().containsKey(jerseyNum)) {
                    if (game.getPlayersTeamOne().get(jerseyNum).getFirstPos().equals(pos)) {
                        game.getStartingFiveP1().put(game.getPlayersTeamOne().get(jerseyNum).getFirstPos(),
                                game.getPlayersTeamOne().get(jerseyNum));
                        game.getPlayersTeamOne().remove(jerseyNum);
                        invalidity = false;
                    } else if (game.getPlayersTeamOne().get(jerseyNum).getSecondPos().equals(pos)){
                        game.getStartingFiveP1().put(game.getPlayersTeamOne().get(jerseyNum).getSecondPos(),
                                game.getPlayersTeamOne().get(jerseyNum));
                        game.getPlayersTeamOne().remove(jerseyNum);
                        invalidity=false;
                    } else {
                        System.out.println("* Player cannot play as a Center: Please enter another player.");
                    }
                } else {
                    System.out.println("* Invalid input: Please enter a valid Jersey Number.");
                }
            } else if (player==PLAYER_TWO) {
                if (game.getPlayersTeamTwo().containsKey(jerseyNum)) {
                    if (game.getPlayersTeamTwo().get(jerseyNum).getFirstPos().equals(pos)) {
                        game.getStartingFiveP2().put(game.getPlayersTeamTwo().get(jerseyNum).getFirstPos(),
                                game.getPlayersTeamTwo().get(jerseyNum));
                        game.getPlayersTeamTwo().remove(jerseyNum);
                        invalidity = false;
                    } else if (game.getPlayersTeamTwo().get(jerseyNum).getSecondPos().equals(pos)) {
                        game.getStartingFiveP2().put(game.getPlayersTeamTwo().get(jerseyNum).getSecondPos(),
                                game.getPlayersTeamTwo().get(jerseyNum));
                        game.getPlayersTeamTwo().remove(jerseyNum);
                        invalidity = false;
                    } else {
                        System.out.println("* Player cannot play as " + pos + ": Please enter another player.");
                    }
                } else {
                    System.out.println("* Invalid input: Please enter a valid Jersey Number.");
                }
            }
        }
    }

    public boolean scoreProbability(int rate){
        int i=0;

        double x = random.nextInt(100);
        double y;
        boolean sign;

        while(rate>=x){
            x = random.nextInt(100);
            y = random.nextInt(5);
            sign = random.nextBoolean();
            if(sign){
                x=x+y;
            } else {
                x=x-y;
            }
            i++;
        }

        return i >= 2;
    }

}
