package guru.ysy.aidemo.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/13 16:46
 * @Email: fred.zhen@gmail.com
 */
public record GetCapitalResponse(
        @JsonPropertyDescription("This is the city name")
        String answer) {
}
