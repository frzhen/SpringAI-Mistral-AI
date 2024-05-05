package guru.ysy.aidemo.controllers;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.Question;
import guru.ysy.aidemo.services.MistralAiService;
import lombok.RequiredArgsConstructor;
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
}
