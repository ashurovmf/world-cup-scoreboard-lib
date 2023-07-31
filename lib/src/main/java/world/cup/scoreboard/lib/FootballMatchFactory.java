package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.time.ZonedDateTime;

public interface FootballMatchFactory {

    FootballMatch createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime);
}
