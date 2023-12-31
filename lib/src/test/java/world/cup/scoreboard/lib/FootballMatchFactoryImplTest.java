package world.cup.scoreboard.lib;

import org.junit.jupiter.api.Test;
import world.cup.scoreboard.lib.domain.FootballMatch;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FootballMatchFactoryImplTest {

    @Test
    void createMatchSuccessfully() {
        //given
        FootballMatchFactory factory = new FootballMatchFactoryImpl();
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        //when
        FootballMatch match = factory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        //then
        assertNotNull(match);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(zonedDateTime, match.getStartTime());
    }

    @Test
    void canNotCreateMatchWithShortNames() {
        //given
        FootballMatchFactory factory = new FootballMatchFactoryImpl();
        String homeTeamName = "Ho";
        String awayTeamName = "Aw";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createMatch(homeTeamName, awayTeamName, zonedDateTime));
        //then
        assertEquals("Team names are incorrect", exception.getMessage());
    }

    @Test
    void finishMatchSuccessfully() {
        //given
        FootballMatchFactory factory = new FootballMatchFactoryImpl();
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        FootballMatch match = factory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        ZonedDateTime endDateTime = zonedDateTime.plusSeconds(1);
        //when
        match = factory.finishMatchWithDateTime(match, endDateTime);
        //then
        assertNotNull(match);
        assertEquals(homeTeamName, match.getHomeTeamName());
        assertEquals(awayTeamName, match.getAwayTeamName());
        assertEquals(zonedDateTime, match.getStartTime());
        assertEquals(endDateTime, match.getEndTime());
    }

    @Test
    void conNotFinishMatchWithDateTimeInPast() {
        //given
        FootballMatchFactory factory = new FootballMatchFactoryImpl();
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        FootballMatch match = factory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        ZonedDateTime endDateTime = zonedDateTime.minusSeconds(1);
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.finishMatchWithDateTime(match, endDateTime));
        //then
        assertEquals("End time value is incorrect", exception.getMessage());
    }

    @Test
    void conNotFinishMatchAlreadyFinished() {
        //given
        FootballMatchFactory factory = new FootballMatchFactoryImpl();
        String homeTeamName = "HomeTeam";
        String awayTeamName = "AwayTeam";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        FootballMatch match = factory.createMatch(homeTeamName, awayTeamName, zonedDateTime);
        ZonedDateTime endDateTime = zonedDateTime.plusSeconds(1);
        final FootballMatch savedMatch = factory.finishMatchWithDateTime(match, endDateTime);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.finishMatchWithDateTime(savedMatch, endDateTime));
        //then
        assertEquals("Match has been already finished", exception.getMessage());
    }

}