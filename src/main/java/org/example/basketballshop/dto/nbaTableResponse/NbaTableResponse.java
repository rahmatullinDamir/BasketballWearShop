package org.example.basketballshop.dto.nbaTableResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NbaTableResponse {
    @JsonProperty("Season")
    private long season;

    @JsonProperty("SeasonType")
    private long seasonType;

    @JsonProperty("TeamID")
    private long teamId;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Conference")
    private Conference conference;

    @JsonProperty("Division")
    private String division;

    @JsonProperty("Wins")
    private long wins;

    @JsonProperty("Losses")
    private long losses;

    @JsonProperty("Percentage")
    private double percentage;

    @JsonProperty("ConferenceWins")
    private long conferenceWins;

    @JsonProperty("ConferenceLosses")
    private long conferenceLosses;

    @JsonProperty("DivisionWins")
    private long divisionWins;

    @JsonProperty("DivisionLosses")
    private long divisionLosses;

    @JsonProperty("HomeWins")
    private long homeWins;

    @JsonProperty("HomeLosses")
    private long homeLosses;

    @JsonProperty("AwayWins")
    private long awayWins;

    @JsonProperty("AwayLosses")
    private long awayLosses;

    @JsonProperty("LastTenWins")
    private long lastTenWins;

    @JsonProperty("LastTenLosses")
    private long lastTenLosses;

    @JsonProperty("PointsPerGameFor")
    private double pointsPerGameFor;

    @JsonProperty("PointsPerGameAgainst")
    private double pointsPerGameAgainst;

    @JsonProperty("Streak")
    private long streak;

    @JsonProperty("GamesBack")
    private long gamesBack;

    @JsonProperty("StreakDescription")
    private String streakDescription;

    @JsonProperty("GlobalTeamID")
    private long globalTeamId;

    @JsonProperty("ConferenceRank")
    private long conferenceRank;

    @JsonProperty("DivisionRank")
    private long divisionRank;
}