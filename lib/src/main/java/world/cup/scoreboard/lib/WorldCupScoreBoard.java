package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.storage.MatchStorage;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of score board for football matches
 */
public class WorldCupScoreBoard implements ScoreBoard {
    FootballMatchFactory matchFactory;
    MatchStorage storage;

    public WorldCupScoreBoard(FootballMatchFactory matchFactory, MatchStorage storage) {
        this.matchFactory = matchFactory;
        this.storage = storage;
    }

    @Override
    public synchronized Long createMatch(String homeTeamName, String awayTeamName) {
        FootballMatch match = matchFactory.createMatch(homeTeamName, awayTeamName, ZonedDateTime.now());
        match = storage.saveMatch(match);
        return match.getId();
    }

    @Override
    public synchronized Boolean updateMatch(Long matchId, FootballMatch.MatchScores newMatchScores) {
        try {
            FootballMatch match = storage.fetchMatch(matchId);
            match = match.toBuilder()
                    .matchScores(newMatchScores)
                    .build();
            storage.updateMatch(match);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }

    }

    @Override
    public synchronized void finishMatch(Long matchId) {
        FootballMatch match = storage.fetchMatch(matchId);
        match = matchFactory.finishMatchWithDateTime(match, ZonedDateTime.now());
        storage.updateMatch(match);
    }

    @Override
    public synchronized List<FootballMatch> getSummaryOfMatchScores() {
        List<FootballMatch> allInProgressMatches = storage.getAllInProgressMatches();
        allInProgressMatches.sort(Comparator
                .comparingInt(FootballMatch::getTotalScores)
                .thenComparing(FootballMatch::getStartTime).reversed());
        return allInProgressMatches;
    }
}
