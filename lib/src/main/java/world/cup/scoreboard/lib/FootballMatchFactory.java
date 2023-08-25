package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.time.ZonedDateTime;

/**
 * Keep a business logic in match state flow (from match creation to finishing)
 */
public interface FootballMatchFactory {

    FootballMatch createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime);

    FootballMatch updateMatchWithWithScores(FootballMatch match, FootballMatch.MatchScores matchScores);

    FootballMatch finishMatchWithDateTime(FootballMatch match, ZonedDateTime endTime);
}
