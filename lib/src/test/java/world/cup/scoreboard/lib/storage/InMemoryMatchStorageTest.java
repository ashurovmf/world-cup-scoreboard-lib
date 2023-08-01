package world.cup.scoreboard.lib.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.FootballMatchFactory;
import world.cup.scoreboard.lib.FootballMatchFactoryImpl;
import world.cup.scoreboard.lib.domain.FootballMatch;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryMatchStorageTest {

    FootballMatchFactory footballMatchFactory;
    Map<Long, FootballMatch> storage;

    @BeforeEach
    void resetState() {
        footballMatchFactory = new FootballMatchFactoryImpl();
        storage = new HashMap<>();
    }


    @Test
    void saveMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);

        //when
        match = matchStorage.saveMatch(match);
        //then
        assertEquals(1, storage.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
    }

    @Test
    void conNotSaveMatchTwice() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        final FootballMatch savedMatch = matchStorage.saveMatch(match);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> matchStorage.saveMatch(savedMatch));
        //then
        assertEquals("Match object is already in storage", exception.getMessage());
    }

    @Test
    void updateMatchSuccessfully() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        match = matchStorage.saveMatch(match);
        match = match.toBuilder()
                .matchScores(new FootballMatch.MatchScores(match.getMatchScores().homeTeamScore(), 1))
                .build();

        //when
        match = matchStorage.updateMatch(match);

        //then
        assertEquals(1, storage.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
        assertEquals(1, storedMatch.getMatchScores().awayTeamScore());
    }

    @Test
    void conNotUpdateUnsavedMatch() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        final FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> matchStorage.updateMatch(match));
        //then
        assertEquals("There is no object in storage", exception.getMessage());
    }

    @Test
    void conNotUpdateMatchWithAnotherID() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        final FootballMatch matchWithIncorrectID = match.toBuilder().id(1000L).build();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> matchStorage.updateMatch(matchWithIncorrectID));
        //then
        assertEquals("There is no object in storage", exception.getMessage());
    }

    @Test
    void getAllMatchesUsingOneMatch() {
        //given
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        FootballMatch match = footballMatchFactory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        match = matchStorage.saveMatch(match);
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
        match = matchStorage.saveMatch(match);
        //when
        List<FootballMatch> allMatches = matchStorage.getAllInProgressMatches();

        //then
        assertEquals(1, storage.size());
        assertEquals(1, allMatches.size());
        FootballMatch storedMatch = storage.get(match.getId());
        assertEquals(homeTeamName, storedMatch.getHomeTeamName());
        assertEquals(awayTeamName, storedMatch.getAwayTeamName());
    }

    @Test
    void getAllInProgressMatchesUsingNoMatch() {
        //given
        MatchStorage matchStorage = new InMemoryMatchStorage(storage);
        //when
        List<FootballMatch> allMatches = matchStorage.getAllInProgressMatches();

        //then
        assertEquals(0, storage.size());
        assertEquals(0, allMatches.size());
    }
}