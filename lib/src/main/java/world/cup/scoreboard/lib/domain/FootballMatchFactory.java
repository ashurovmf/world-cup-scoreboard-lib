package world.cup.scoreboard.lib.domain;

import java.time.ZonedDateTime;

public interface FootballMatchFactory {

    FootballMatch createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime);
}
