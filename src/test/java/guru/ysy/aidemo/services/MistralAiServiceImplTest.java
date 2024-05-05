package guru.ysy.aidemo.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

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

        System.out.println("Got the answer:");
        Flux<ChatResponse> answer = mistralAiService.getAnswer("tell me a funny joke about dog");
        answer.doOnNext(value-> System.out.print(value.getResult().getOutput().getContent())).blockLast();
    }
}
