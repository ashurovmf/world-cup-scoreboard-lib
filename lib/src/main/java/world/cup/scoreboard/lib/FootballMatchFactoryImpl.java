package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.domain.FootballMatchValidator;

import java.time.ZonedDateTime;

public class FootballMatchFactoryImpl implements FootballMatchFactory {
    private static final FootballMatchValidator matchValidator = new FootballMatchValidator();

    @Override
    public FootballMatch createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime) {
        if (matchValidator.correctName(homeTeamName) || matchValidator.correctName(awayTeamName)) {
            throw new IllegalArgumentException("Team names are incorrect");
        }
        return FootballMatch
                .builder()
                .id(0L)
                .homeTeamName(homeTeamName)
                .awayTeamName(awayTeamName)
                .startTime(startTime)
                .build();
    }

    public FootballMatchValidator getValidator() {
        return matchValidator;
    }
}
