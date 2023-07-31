package world.cup.scoreboard.lib.storage;

import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.FootballMatchFactory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryMatchStorageTest {

    FootballMatchFactory footballMatchFactory;
    Map<Long, FootballMatch> storage;


    @Test
    void saveMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);

        //when
        matchStorage.saveMatch(match);
        //then
        assertEquals(1, storage.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
    }

    @Test
    void updateMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        matchStorage.saveMatch(match);
        match = match.toBuilder()
                .matchScores(new FootballMatch.MatchScores(match.getMatchScores().homeTeamScore(), 1))
                .build();

        //when
        matchStorage.updateMatch(match);

        //then
        assertEquals(1, storage.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
        assertEquals(1, storedMatch.getMatchScores().awayTeamScore());
    }

    @Test
    void getAllMatchesUsingOneMatch() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        matchStorage.saveMatch(match);
        //when
        List<FootballMatch> allMatches = matchStorage.getAllMatches();

        //then
        assertEquals(1, storage.size());
        assertEquals(1, allMatches.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
    }

    @Test
    void getAllInProgressMatchesUsingOneMatch() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        matchStorage.saveMatch(match);
        //when
        List<FootballMatch> allMatches = matchStorage.getAllInProgressMatches();

        //then
        assertEquals(1, storage.size());
        assertEquals(1, allMatches.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
    }
}