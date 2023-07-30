package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.List;

public class WorldCupScoreBoard implements ScoreBoard {
    @Override
    public Long createMatch(String homeTeamName, String awayTeamName) {
        return null;
    }

    @Override
    public Boolean updateMatch(Long matchId, FootballMatch.MatchScores newMatchScores) {
        return null;
    }

    @Override
    public void finishMatch(String homeTeamName, String awayTeamName) {

    }

    @Override
    public void finishMatch(Long matchId) {

    }

    @Override
    public List<FootballMatch> getSummaryOfMatchScores() {
        return null;
    }
}
