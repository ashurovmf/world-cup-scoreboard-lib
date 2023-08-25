package world.cup.scoreboard.lib.domain;

import java.time.ZonedDateTime;

/**
 * Validator for match fields.
 * TODO Should be split to independent classes, but I keep it here
 */
public class FootballMatchValidator {

    public boolean isCorrectName(String name) {
        return name != null && name.matches("^\\w{3,}$");
    }

    public boolean isCorrectEndTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        return startTime != null && endTime != null && endTime.isAfter(startTime);
    }

    public boolean isCorrectMatchScores(FootballMatch.MatchScores matchScores) {
        return matchScores != null && matchScores.homeTeamScore() >= 0 && matchScores.awayTeamScore() >= 0;
    }
}
