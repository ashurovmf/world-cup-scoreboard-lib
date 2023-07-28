package world.cup.scoreboard.lib.domain;

import java.time.ZonedDateTime;

public interface MatchFactory {

    public Match createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime);
}
