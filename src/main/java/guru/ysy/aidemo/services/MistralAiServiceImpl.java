package guru.ysy.aidemo.services;

import guru.ysy.aidemo.model.Answer;
import guru.ysy.aidemo.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/4 20:34
 * @Email: fred.zhen@gmail.com
 */
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
        return response.map(chatResponse -> new Answer(chatResponse.getResult().getOutput().getContent()));
    }
}
