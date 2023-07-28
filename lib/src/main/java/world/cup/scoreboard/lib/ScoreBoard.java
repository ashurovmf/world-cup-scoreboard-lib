package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.Match;

import java.util.List;

public interface ScoreBoard {

    Long createMatch(String homeTeamName, String awayTeamName);
    Boolean updateMatch(String homeTeamName, String awayTeamName, Match.MatchScores newMatchScores);

    void finishMatch(String homeTeamName, String awayTeamName);

    void finishMatch(Long matchId);
    List<Match> getSummaryOfMatchScores();
}
