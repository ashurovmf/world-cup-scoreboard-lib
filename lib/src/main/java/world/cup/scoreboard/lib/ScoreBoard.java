package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Cover functionality of score board
 */
public interface ScoreBoard {

    Long createMatch(String homeTeamName, String awayTeamName);
    Long createMatch(String homeTeamName, String awayTeamName, ZonedDateTime dateTime);
    Boolean updateMatch(Long matchId, FootballMatch.MatchScores newMatchScores);
    void finishMatch(Long matchId);
    List<FootballMatch> getSummaryOfMatchScores();
}
