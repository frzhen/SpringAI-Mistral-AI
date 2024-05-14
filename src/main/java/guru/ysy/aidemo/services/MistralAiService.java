package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.*;
import reactor.core.publisher.Flux;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:33
 * @Email: fred.zhen@gmail.com
 */
public interface MistralAiService {

    Flux<Answer> getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest request);

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest request);
}
