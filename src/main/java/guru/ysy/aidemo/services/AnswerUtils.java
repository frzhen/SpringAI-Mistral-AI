package guru.ysy.aidemo.services;


import guru.ysy.aidemo.model.Answer;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/9 03:55
 * @Email: fred.zhen@gmail.com
 */

public class AnswerUtils {

    private AnswerUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static Answer stringCleanUp(Answer answer) {
        return new Answer(answer.answer().replaceAll("`+", ""));
    }
}
