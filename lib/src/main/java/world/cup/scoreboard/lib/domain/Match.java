package world.cup.scoreboard.lib.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Match functionality to contains information about play
 */
public interface Match {

    /**
     * Container to hold scores in format 0-0
     * @param homeTeamScore Integer value for home team
     * @param awayTeamScore Integer value for away team
     */
    record MatchScores(Integer homeTeamScore, Integer awayTeamScore) {}

    Long id();
    String getHomeTeamName();
    String getAwayTeamName();

    /**
     * Container with current match scores
     * @return {@MatchScores}
     */
    MatchScores getMatchScores();

    /**
     * Total match score
     * @return Calculated total score using current {@MatchScores}
     */
    Integer getTotalScores();
    ZonedDateTime getStartTime();
    Optional<ZonedDateTime> getEndTime();

    /**
     * Finish a match using the end date time
     * @param endTime end time of match
     */
    void finishMatchWithDateTime(ZonedDateTime endTime);
}
