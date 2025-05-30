package org.example.basketballshop.DTO.NbaTableResponse;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


@lombok.Data
public class NbaTableResponse {
    @lombok.Getter(onMethod_ = {@JsonProperty("Season")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Season")})
    private long season;
    @lombok.Getter(onMethod_ = {@JsonProperty("SeasonType")})
    @lombok.Setter(onMethod_ = {@JsonProperty("SeasonType")})
    private long seasonType;
    @lombok.Getter(onMethod_ = {@JsonProperty("TeamID")})
    @lombok.Setter(onMethod_ = {@JsonProperty("TeamID")})
    private long teamId;
    @lombok.Getter(onMethod_ = {@JsonProperty("Key")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Key")})
    private String key;
    @lombok.Getter(onMethod_ = {@JsonProperty("City")})
    @lombok.Setter(onMethod_ = {@JsonProperty("City")})
    private String city;
    @lombok.Getter(onMethod_ = {@JsonProperty("Name")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Name")})
    private String name;
    @lombok.Getter(onMethod_ = {@JsonProperty("Conference")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Conference")})
    private Conference conference;
    @lombok.Getter(onMethod_ = {@JsonProperty("Division")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Division")})
    private String division;
    @lombok.Getter(onMethod_ = {@JsonProperty("Wins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Wins")})
    private long wins;
    @lombok.Getter(onMethod_ = {@JsonProperty("Losses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Losses")})
    private long losses;
    @lombok.Getter(onMethod_ = {@JsonProperty("Percentage")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Percentage")})
    private double percentage;
    @lombok.Getter(onMethod_ = {@JsonProperty("ConferenceWins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("ConferenceWins")})
    private long conferenceWins;
    @lombok.Getter(onMethod_ = {@JsonProperty("ConferenceLosses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("ConferenceLosses")})
    private long conferenceLosses;
    @lombok.Getter(onMethod_ = {@JsonProperty("DivisionWins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("DivisionWins")})
    private long divisionWins;
    @lombok.Getter(onMethod_ = {@JsonProperty("DivisionLosses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("DivisionLosses")})
    private long divisionLosses;
    @lombok.Getter(onMethod_ = {@JsonProperty("HomeWins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("HomeWins")})
    private long homeWins;
    @lombok.Getter(onMethod_ = {@JsonProperty("HomeLosses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("HomeLosses")})
    private long homeLosses;
    @lombok.Getter(onMethod_ = {@JsonProperty("AwayWins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("AwayWins")})
    private long awayWins;
    @lombok.Getter(onMethod_ = {@JsonProperty("AwayLosses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("AwayLosses")})
    private long awayLosses;
    @lombok.Getter(onMethod_ = {@JsonProperty("LastTenWins")})
    @lombok.Setter(onMethod_ = {@JsonProperty("LastTenWins")})
    private long lastTenWins;
    @lombok.Getter(onMethod_ = {@JsonProperty("LastTenLosses")})
    @lombok.Setter(onMethod_ = {@JsonProperty("LastTenLosses")})
    private long lastTenLosses;
    @lombok.Getter(onMethod_ = {@JsonProperty("PointsPerGameFor")})
    @lombok.Setter(onMethod_ = {@JsonProperty("PointsPerGameFor")})
    private double pointsPerGameFor;
    @lombok.Getter(onMethod_ = {@JsonProperty("PointsPerGameAgainst")})
    @lombok.Setter(onMethod_ = {@JsonProperty("PointsPerGameAgainst")})
    private double pointsPerGameAgainst;
    @lombok.Getter(onMethod_ = {@JsonProperty("Streak")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Streak")})
    private long streak;
    @lombok.Getter(onMethod_ = {@JsonProperty("GamesBack")})
    @lombok.Setter(onMethod_ = {@JsonProperty("GamesBack")})
    private long gamesBack;
    @lombok.Getter(onMethod_ = {@JsonProperty("StreakDescription")})
    @lombok.Setter(onMethod_ = {@JsonProperty("StreakDescription")})
    private String streakDescription;
    @lombok.Getter(onMethod_ = {@JsonProperty("GlobalTeamID")})
    @lombok.Setter(onMethod_ = {@JsonProperty("GlobalTeamID")})
    private long globalTeamId;
    @lombok.Getter(onMethod_ = {@JsonProperty("ConferenceRank")})
    @lombok.Setter(onMethod_ = {@JsonProperty("ConferenceRank")})
    private long conferenceRank;
    @lombok.Getter(onMethod_ = {@JsonProperty("DivisionRank")})
    @lombok.Setter(onMethod_ = {@JsonProperty("DivisionRank")})
    private long divisionRank;
}

// Conference.java
