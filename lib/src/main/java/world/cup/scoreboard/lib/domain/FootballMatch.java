package world.cup.scoreboard.lib.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Match functionality to contains information about play
 */
@Data
@AllArgsConstructor
@Builder
public class FootballMatch {

    /**
     * Container to hold scores in format 0-0
     * @param homeTeamScore Integer value for home team
     * @param awayTeamScore Integer value for away team
     */
    public record MatchScores(Integer homeTeamScore, Integer awayTeamScore) {}
    private final Long id;
    private final String homeTeamName;
    private final String awayTeamName;
    private final ZonedDateTime startTime;

    /**
     * Container with current match scores
     */
    private MatchScores matchScores;

    private ZonedDateTime endTime;

    FootballMatch(Long id, String homeTeamName, String awayTeamName, ZonedDateTime startTime, MatchScores matchScores) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.startTime = startTime;
        this.matchScores = matchScores;
    }

    /**
     * Finish a match using the end date time
     * @param endTime end time of match
     */
    public void finishMatchWithDateTime(ZonedDateTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time can not be null");
        }
        if (this.endTime == null && endTime.isAfter(startTime)) {
            this.endTime = endTime;
        } else {
            throw new IllegalArgumentException("End time value (" + endTime+ ") is incorrect. Or match has been already finished");
        }
    }

    /**
     * Total match score
     * @return Calculated total score using current {@MatchScores}
     */
    public Integer getTotalScores(){
        return matchScores.homeTeamScore + matchScores.awayTeamScore;
    }

    public Optional<ZonedDateTime> fetchEndTime(){
        return Optional.ofNullable(endTime);
    }
}
