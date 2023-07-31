package world.cup.scoreboard.lib;

import world.cup.scoreboard.lib.domain.FootballMatch;
import world.cup.scoreboard.lib.domain.FootballMatchValidator;

import java.time.ZonedDateTime;

public class FootballMatchFactoryImpl implements FootballMatchFactory {
    //TODO It should be provided externally, but keep it simple
    private static final FootballMatchValidator matchValidator = new FootballMatchValidator();

    @Override
    public FootballMatch createMatch(String homeTeamName, String awayTeamName, ZonedDateTime startTime) {
        if (!getValidator().isCorrectName(homeTeamName) || !getValidator().isCorrectName(awayTeamName)) {
            throw new IllegalArgumentException("Team names are incorrect");
        }
        return FootballMatch
                .builder()
                .id(0L)
                .homeTeamName(homeTeamName)
                .awayTeamName(awayTeamName)
                .startTime(startTime)
                .matchScores(new FootballMatch.MatchScores(0,0))
                .build();
    }

    public FootballMatchValidator getValidator() {
        return matchValidator;
    }

    /**
     * Finish a match using the end date time
     * @param endTime end time of match
     */
    @Override
    public FootballMatch finishMatchWithDateTime(FootballMatch match, ZonedDateTime endTime) {
        if (match == null) {
            throw new IllegalArgumentException("Match can not be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time can not be null");
        }
        if (getValidator().isCorrectEndTime(match.getStartTime(), endTime)) {
            return match.toBuilder().endTime(endTime).build();
        } else {
            throw new IllegalArgumentException("End time value (" + endTime+ ") is incorrect. Or match has been already finished");
        }
    }
}
