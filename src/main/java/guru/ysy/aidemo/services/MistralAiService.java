package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.GetCapitalRequest;
import guru.ysy.aidemo.model.Question;
import reactor.core.publisher.Flux;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:33
 * @Email: fred.zhen@gmail.com
 */
public interface MistralAiService {

    Flux<Answer> getAnswer(Question question);

    Answer getCapital(GetCapitalRequest request);

    Flux<Answer> getCapitalWithInfo(GetCapitalRequest request);
}
