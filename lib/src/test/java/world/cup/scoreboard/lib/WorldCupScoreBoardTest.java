package world.cup.scoreboard.lib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.storage.MatchStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldCupScoreBoardTest {

    MatchStorage matchStorage;
    ScoreBoard scoreBoard;

    @BeforeEach
    void cleanStorages() {
        matchStorage = null;
        scoreBoard = new WorldCupScoreBoard(new FootballMatchFactoryImpl(), matchStorage);
    }

    @Test
    void createMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";

        //when
        Long matchId = scoreBoard.createMatch(homeTeamName, awayTeamName);

        //then
        assertNotNull(matchId);
        assertEquals(1, matchStorage.getAllMatches().size());
        FootballMatch match = matchStorage.getAllMatches().get(0);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(0, match.getMatchScores().homeTeamScore());
        assertEquals(0, match.getMatchScores().awayTeamScore());
        assertEquals(0, match.getTotalScores());
    }

    @Test
    void updateMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        Long matchId = scoreBoard.createMatch(homeTeamName, awayTeamName);

        //when
        Boolean successfulUpdate = scoreBoard.updateMatch(matchId, new FootballMatch.MatchScores(0, 1));

        //then
        assertNotNull(successfulUpdate);
        assertTrue(successfulUpdate);
        assertEquals(1, matchStorage.getAllMatches().size());
        FootballMatch match = matchStorage.getAllMatches().get(0);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(0, match.getMatchScores().homeTeamScore());
        assertEquals(1, match.getMatchScores().awayTeamScore());
        assertEquals(1, match.getTotalScores());
    }

    @Test
    void finishMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        Long matchId = scoreBoard.createMatch(homeTeamName, awayTeamName);

        //when
        scoreBoard.finishMatch(matchId);

        //then
        assertEquals(1, matchStorage.getAllMatches().size());
        FootballMatch match = matchStorage.getAllMatches().get(0);
        assertEquals(Boolean.TRUE, match.fetchEndTime().isPresent());
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
    }

    @Test
    void getSummaryOfMatchScoresSuccessfullyForOneMatch() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        scoreBoard.createMatch(homeTeamName, awayTeamName);

        //when
        List<FootballMatch> summaryOfMatchScores = scoreBoard.getSummaryOfMatchScores();

        //then
        assertEquals(1, summaryOfMatchScores.size());
        FootballMatch match = summaryOfMatchScores.get(0);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(0, match.getMatchScores().homeTeamScore());
        assertEquals(0, match.getMatchScores().awayTeamScore());
        assertEquals(0, match.getTotalScores());
    }
}
