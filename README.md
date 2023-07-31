# world-cup-scoreboard-lib
Live Football World Cup Scoreboard library for SportRadar that shows 
all the ongoing matches and their scores.
The scoreboard supports the following operations:
1. Start a new match, assuming initial score 0 – 0 and adding it the scoreboard.
   This should capture following parameters:
   a. Home team
   b. Away team
2. Update score. This should receive a pair of absolute scores: home team score and away
   team score.
3. Finish match currently in progress. This removes a match from the scoreboard.
4. Get a summary of matches in progress ordered by their total score. The matches with the
   same total score will be returned ordered by the most recently started match in the
   scoreboard.

## Assumptions
1. ORM aspect is reduced and simulated via MatchStorage. 
Id generation is simple and put into MatchStorage.
2. Error handling is keeping simple: no transactions on persisting level,
thread-saving is done by basic java synchronize mechanism.
3. Finished matches are kept in storage for statistic purposes. We can easily flush them.
4. Behaviour test (provided test case with summary) is moved to IT-like test 
to save time, it should be done by another framework like JBehaviour or Selenium. 


## Output for provided test case
You can see the example in WorldCupScoreBoardIT.
