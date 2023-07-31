package world.cup.scoreboard.lib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.storage.InMemoryMatchStorage;
import world.cup.scoreboard.lib.storage.MatchStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorldCupScoreBoardIT {

    MatchStorage matchStorage;
    ScoreBoard scoreBoard;

    @BeforeEach
    void cleanStorages() {
        matchStorage = new InMemoryMatchStorage();
        scoreBoard = new WorldCupScoreBoard(new FootballMatchFactoryImpl(), matchStorage);
    }

    @Test
    void mainTestCaseGetSummaryUsingSixInProgressMatches() throws InterruptedException {
        //given
        List<Long> matchIds = new ArrayList<>();
        matchIds.add(createAndSaveMatch(scoreBoard, "Mexico", "Canada", new FootballMatch.MatchScores(0, 5)));
        matchIds.add(createAndSaveMatch(scoreBoard, "Spain", "Brazil", new FootballMatch.MatchScores(10, 2)));
        matchIds.add(createAndSaveMatch(scoreBoard, "Germany", "France", new FootballMatch.MatchScores(2, 2)));
        matchIds.add(createAndSaveMatch(scoreBoard, "Uruguay", "Italy", new FootballMatch.MatchScores(6, 6)));
        matchIds.add(createAndSaveMatch(scoreBoard, "Argentina", "Australia", new FootballMatch.MatchScores(3, 1)));

        //when
        List<FootballMatch> summaryOfMatchScores = scoreBoard.getSummaryOfMatchScores();

        //then
        assertNotNull(summaryOfMatchScores);
        assertEquals(5, matchIds.size());
        assertEquals(5, summaryOfMatchScores.size());
        //first match
        FootballMatch match = summaryOfMatchScores.get(0);
        assertEquals("Uruguay", match.getHomeTeamName());
        assertEquals("Italy", match.getAwayTeamName());
        assertEquals(12, match.getTotalScores());
        //second match
        match = summaryOfMatchScores.get(1);
        assertEquals("Spain", match.getHomeTeamName());
        assertEquals("Brazil", match.getAwayTeamName());
        assertEquals(12, match.getTotalScores());
        //third match
        match = summaryOfMatchScores.get(2);
        assertEquals("Mexico", match.getHomeTeamName());
        assertEquals("Canada", match.getAwayTeamName());
        assertEquals(5, match.getTotalScores());
        //forth match
        match = summaryOfMatchScores.get(3);
        assertEquals("Argentina", match.getHomeTeamName());
        assertEquals("Australia", match.getAwayTeamName());
        assertEquals(4, match.getTotalScores());
        //fifth match
        match = summaryOfMatchScores.get(4);
        assertEquals("Germany", match.getHomeTeamName());
        assertEquals("France", match.getAwayTeamName());
        assertEquals(4, match.getTotalScores());
    }

    private static Long createAndSaveMatch(ScoreBoard scoreBoard,
                                           String homeTeamName,
                                           String awayTeamName,
                                           FootballMatch.MatchScores matchScores) throws InterruptedException {
        Long matchId = scoreBoard.createMatch(homeTeamName, awayTeamName);
        Thread.sleep(50);
        if (scoreBoard.updateMatch(matchId, matchScores)) {
            return matchId;
        } else {
            throw new RuntimeException("Match for (" + homeTeamName + "," + awayTeamName + ") cannot be updated");
        }
    }
}
