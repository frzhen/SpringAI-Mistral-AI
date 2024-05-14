package guru.ysy.aidemo.controllers;

import guru.ysy.aidemo.model.*;
import guru.ysy.aidemo.services.MistralAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Question Controller", description = "Endpoints for asking questions to Mistral AI inference Endpoint")
public class QuestionController {

    private final MistralAiService mistralAiService;

    @Operation(summary = "Ask a question to Mistral AI")
    @PostMapping("/ask")
    public Mono<ResponseEntity<List<Answer>>> askQuestion(@RequestBody Question question) {
        return mistralAiService.getAnswer(question)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Ask the name of state or country's capital to Mistral AI")
    @PostMapping("/capital")
    public GetCapitalResponse capitalQuestion(@RequestBody GetCapitalRequest request) {
        return mistralAiService.getCapital(request);
    }

    @Operation(summary = "Ask the name of state or country's capital with detail info to Mistral AI")
    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse capitalQuestionWithInfo(@RequestBody GetCapitalRequest request) {
        return mistralAiService.getCapitalWithInfo(request);
    }
}
