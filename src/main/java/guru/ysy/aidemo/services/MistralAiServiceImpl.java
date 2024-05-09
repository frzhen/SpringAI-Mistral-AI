package guru.ysy.aidemo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.GetCapitalRequest;
import guru.ysy.aidemo.model.Question;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:34
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MistralAiServiceImpl implements MistralAiService {

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPromptTemplate;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPromptTemplate;

    private final MistralAiChatClient chatClient;

    private final ObjectMapper mapper;

    @Override
    public Flux<Answer> getAnswer(Question question) {
        Flux<ChatResponse> response = chatClient.stream(new Prompt(question.question()));
        return response
                .map(chatResponse -> new Answer(chatResponse.getResult().getOutput().getContent()));
    }

    @Override
    public Answer getCapital(GetCapitalRequest request) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
        ChatResponse response = chatClient.call(prompt);

        String responseString;
        try {
            JsonNode jsonNode = mapper.readTree(response.getResult().getOutput().getContent());
            responseString = jsonNode.get("answer").asText();
        } catch (JsonProcessingException e) {
            log.error("Mistral AI response format error", e);
            responseString = "Mistral AI response format error";
        }
        return new Answer(responseString);
    }

    @Override
    public Flux<Answer> getCapitalWithInfo(GetCapitalRequest request) {
        return getCapitalFlux(request, getCapitalWithInfoPromptTemplate);
    }

    @NotNull
    private Flux<Answer> getCapitalFlux(GetCapitalRequest request, Resource getCapitalTemplate) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
        Flux<ChatResponse> response = chatClient.stream(prompt);
        return response
                .map(chatResponse ->
                        AnswerUtils.stringCleanUp(new Answer(chatResponse
                                .getResult()
                                .getOutput()
                                .getContent())));
    }
}
