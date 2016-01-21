package no.sonat.battleships;

/**
 * Created by lars on 16.06.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String token = args[0];
        BattleshipRobot robot = new BattleshipRobot(token);
        robot.initiate();
    }
}
