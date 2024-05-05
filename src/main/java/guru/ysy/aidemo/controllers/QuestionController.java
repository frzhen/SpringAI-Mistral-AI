package guru.ysy.aidemo.controllers;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.GetCapitalRequest;
import guru.ysy.aidemo.model.Question;
import guru.ysy.aidemo.services.MistralAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/5 21:24
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final MistralAiService mistralAiService;

    @PostMapping("/ask")
    public Mono<ResponseEntity<List<Answer>>> askQuestion(@RequestBody Question question) {
        return mistralAiService.getAnswer(question)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping("/capital")
    public Mono<ResponseEntity<List<Answer>>> capitalQuestion(@RequestBody GetCapitalRequest request) {
        log.info("Got the request: {}", request);
        return mistralAiService.getCapital(request)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
