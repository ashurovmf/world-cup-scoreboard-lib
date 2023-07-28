package world.cup.scoreboard.lib;

import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.domain.Match;
import world.cup.scoreboard.lib.storage.MatchStorage;

import static org.junit.jupiter.api.Assertions.*;

class WorldCupScoreBoardTest {

    MatchStorage matchStorage;
    ScoreBoard scoreBoard;

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
        Match match = matchStorage.getAllMatches().get(0);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(0, match.getMatchScores().homeTeamScore());
        assertEquals(0, match.getMatchScores().awayTeamScore());
        assertEquals(0, match.getTotalScores());
    }
}
