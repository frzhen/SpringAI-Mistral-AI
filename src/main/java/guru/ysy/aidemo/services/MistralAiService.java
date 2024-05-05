package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.Question;
import org.springframework.ai.chat.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:33
 * @Email: fred.zhen@gmail.com
 */
public interface MistralAiService {

    Flux<ChatResponse> getAnswer(String question);

    Flux<Answer> getAnswer(Question question);
}
