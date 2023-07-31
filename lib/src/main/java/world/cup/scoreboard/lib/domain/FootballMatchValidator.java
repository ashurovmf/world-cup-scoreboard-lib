package world.cup.scoreboard.lib.domain;

public class FootballMatchValidator {

    public boolean correctName(String name) {
        return name.matches("^\\w{3,}$");
    }
}
