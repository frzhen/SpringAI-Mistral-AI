package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.GetCapitalRequest;
import guru.ysy.aidemo.model.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:34
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MistralAiServiceImpl implements MistralAiService {

    private final MistralAiChatClient chatClient;
    @Override
    public Flux<ChatResponse> getAnswer(String question) {

        return chatClient.stream(new Prompt(question));
    }

    @Override
    public Flux<Answer> getAnswer(Question question) {
        Flux<ChatResponse> response = chatClient.stream(new Prompt(question.question()));
        return response
                .map(chatResponse -> new Answer(chatResponse.getResult().getOutput().getContent()));
    }

    @Override
    public Flux<Answer> getCapital(GetCapitalRequest request) {
        PromptTemplate promptTemplate = new PromptTemplate("What is the capital of "
                                                           + request.stateOrCountry() + "?");
        Prompt prompt = promptTemplate.create();
        log.info("Got the prompt: {}", prompt);
        Flux<ChatResponse> response = chatClient.stream(prompt);
        return response
                .map(chatResponse -> new Answer(chatResponse.getResult().getOutput().getContent()));
    }
}
