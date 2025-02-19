package guru.ysy.aidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.ysy.aidemo.model.*;
import guru.ysy.aidemo.services.MistralAiServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/5 21:48
 * @Email: fred.zhen@gmail.com
 */
@Order(31)
@Slf4j
@WebFluxTest(QuestionController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestionControllerMockTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MistralAiServiceImpl mistralAiServiceImpl;

    @Autowired
    ObjectMapper mapper;

    private Question question;

    GetCapitalRequest getCapitalRequest;

    @BeforeEach
    void setUp() {
        question = new Question("tell me a funny joke about dog");
        getCapitalRequest = new GetCapitalRequest("China");
    }

    @Order(1)
    @Test
    @DisplayName("Ask a String Question Test")
    void askQuestion() {
        Answer answer1 = new Answer("Answer 1");
        Answer answer2 = new Answer("Answer 2");
        Answer answer3 = new Answer("Answer 3");

        Flux<Answer> answerFlux = Flux.just(answer1, answer2, answer3);
        given(mistralAiServiceImpl.getAnswer(question)).willReturn(answerFlux);

        webTestClient.post().uri("/ask")
                .body(Mono.just(question), Question.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Answer.class).hasSize(3)
                .value(answers -> assertThat(answers).containsExactlyInAnyOrder(answer1, answer2, answer3));
    }

    @Order(2)
    @Test
    @DisplayName("Test State Or Country Capital Question return JSON with 1 answer")
    void capitalQuestion() {
        GetCapitalResponse capitalAnswer = new GetCapitalResponse("Beijing");
        given(mistralAiServiceImpl.getCapital(getCapitalRequest)).willReturn(capitalAnswer);
        webTestClient.post().uri("/capital")
                .body(Mono.just(getCapitalRequest), GetCapitalRequest.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(GetCapitalResponse.class).hasSize(1)
                .value(answers -> assertThat(answers)
                        .containsExactlyInAnyOrder(capitalAnswer));
    }

    @Order(3)
    @Test
    @DisplayName("Test State Or Country Capital Question with detail info")
    void capitalQuestionWithInfo() {
        GetCapitalWithInfoResponse answer = new GetCapitalWithInfoResponse(
                "Beijing",
                21.2,
                "Asia",
                "Chinese",
                "CNY",
                39.9,
                116.4);
        given(mistralAiServiceImpl.getCapitalWithInfo(getCapitalRequest)).willReturn(answer);

        webTestClient.post().uri("/capitalWithInfo")
                .body(Mono.just(getCapitalRequest), GetCapitalRequest.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(GetCapitalWithInfoResponse.class).hasSize(1)
                .value(response -> assertThat(response).containsExactlyInAnyOrder(answer));
    }
}
