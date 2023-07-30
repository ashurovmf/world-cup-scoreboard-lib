package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.List;

public interface ScoreBoard {

    Long createMatch(String homeTeamName, String awayTeamName);

    Boolean updateMatch(Long matchId, FootballMatch.MatchScores newMatchScores);

    void finishMatch(String homeTeamName, String awayTeamName);

    void finishMatch(Long matchId);
    List<FootballMatch> getSummaryOfMatchScores();
}
