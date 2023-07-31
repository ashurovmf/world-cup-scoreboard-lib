package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.storage.MatchStorage;

import java.time.ZonedDateTime;
import java.util.List;

public class WorldCupScoreBoard implements ScoreBoard {
    FootballMatchFactory matchFactory;
    MatchStorage storage;

    public WorldCupScoreBoard(FootballMatchFactory matchFactory, MatchStorage storage) {
        this.matchFactory = matchFactory;
        this.storage = storage;
    }

    @Override
    public Long createMatch(String homeTeamName, String awayTeamName) {
        FootballMatch match = matchFactory.createMatch(homeTeamName, awayTeamName, ZonedDateTime.now());
        match = storage.saveMatch(match);
        return match.getId();
    }

    @Override
    public Boolean updateMatch(Long matchId, FootballMatch.MatchScores newMatchScores) {
        try {
            FootballMatch match = storage.fetchMatch(matchId);
            match = match.toBuilder()
                    .matchScores(newMatchScores)
                    .build();
            match = storage.updateMatch(match);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }

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
