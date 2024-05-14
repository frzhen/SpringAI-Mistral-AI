package guru.ysy.aidemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/13 17:17
 * @Email: fred.zhen@gmail.com
 */
public record GetCapitalWithInfoResponse(
        @JsonPropertyDescription("This is the city name")
        String capital,

        @JsonProperty("population-in-mil")
        @JsonPropertyDescription("This is the population in millions")
        Double populationInMillions,

        @JsonPropertyDescription("This is the region of the city")
        String region,

        @JsonPropertyDescription("This is the language spoken in the city")
        String language,

        @JsonPropertyDescription("This is the currency used in the city")
        String currency,

        @JsonProperty("lat")
        @JsonPropertyDescription("This is the latitude of the city")
        Double latitude,

        @JsonProperty("lng")
        @JsonPropertyDescription("This is the longitude of the city")
        Double longitude
) {
}
