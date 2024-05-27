package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
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

    private final MistralAiChatClient chatClient;

    @Override
    public Flux<Answer> getAnswer(Question question) {
        Flux<ChatResponse> response = chatClient.stream(new Prompt(question.question()));
        return response
                .map(chatResponse -> new Answer(chatResponse.getResult().getOutput().getContent()));
    }


    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest request) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry",
                request.stateOrCountry(),
                "format",
                format));
        ChatResponse response = chatClient.call(prompt);

        return parser.parse(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest request) {
        BeanOutputParser<GetCapitalWithInfoResponse> parser = new BeanOutputParser<>(
                GetCapitalWithInfoResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry",
                request.stateOrCountry(),
                "format",
                format));
        ChatResponse response = chatClient.call(prompt);
        return parser.parse(response.getResult().getOutput().getContent().replaceAll("`*\n", "\n"));
    }

}
