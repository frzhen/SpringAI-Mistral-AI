package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.GetCapitalRequest;
import guru.ysy.aidemo.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:39
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@SpringBootTest
class MistralAiServiceImplTest {

    @Autowired
    MistralAiService mistralAiService;

    @Test
    void getAnswer() {
        Question newQuestion = new Question("tell me a funny joke about dog");

        System.out.println("Got the answer:");
        System.out.printf("(Question: %s)%n", newQuestion.question());

        Flux<Answer> answerFlux = mistralAiService.getAnswer(newQuestion);
        List<Answer> answerList = answerFlux.collectList().block();
        assertThat(answerList).isNotNull();
        assert answerList != null;
        answerList.forEach(answer -> System.out.print(answer.answer()));
    }

    @Test
    void getCapital() {
        GetCapitalRequest request = new GetCapitalRequest("China");
        System.out.printf("Got the capital answer for: %s%n", request.stateOrCountry());

        Answer answer = mistralAiService.getCapital(request);
        assertThat(answer.answer()).isNotNull();
        assertThat(answer.answer()).isEqualTo("Beijing");
        System.out.println(answer.answer());
    }

    @Test
    void getCapitalWithInfo() {
        GetCapitalRequest request = new GetCapitalRequest("China");
        System.out.printf("Got the capital answer with detail information for: %s%n", request.stateOrCountry());

        Flux<Answer> answerFlux = mistralAiService.getCapitalWithInfo(request);
        List<Answer> answerList = answerFlux.collectList().block();
        assertThat(answerList).isNotNull();
        assert answerList != null;
        answerList.forEach(answer -> System.out.print(answer.answer()));
    }
}
