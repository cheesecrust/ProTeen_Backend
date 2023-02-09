package com.ProTeen.backend.controller;

import com.ProTeen.backend.community.dto.BoardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/board";

    private String makeBody(String title, String author, String content) throws Exception{
        //when
        /**
         * Object를 JSON으로 변환
         * */
        String body = mapper.writeValueAsString(
                BoardDTO.Total.builder().author(author).content(content).title(title).build()
        );
        return body;
    }

    @Test
    @DisplayName("저장 테스트")
    void create_test() throws Exception {

        String body = makeBody("first post", "정연수", "안뇽");
        //then
        mvc.perform(post(BASE_URL + "/post")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("저장에 성공했습니다."));
    }

    @Test
    @DisplayName("제목 부제 테스트")
    void create_test_title_null() throws Exception {

        String body = makeBody(null, "정연수", "안뇽");
        //then
        mvc.perform(post(BASE_URL + "/post")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("저자 부재 테스트")
    void create_test_author_null() throws Exception {

        String body = makeBody("hi", null, "안뇽");
        //then
        mvc.perform(post(BASE_URL + "/post")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("읽기 테스트")
    void read_test() throws Exception {
        //given
        //then
        mvc.perform(get(BASE_URL + "/read"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("글 상세 읽기")
    void read_detail() throws Exception {

        String body = makeBody("first post", "정연수", "안뇽");

        mvc.perform(post(BASE_URL + "/post")
                .content(body) //HTTP Body에 데이터를 담는다
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
        );

        mvc.perform(get(BASE_URL + "/read/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("글 수정하기")
    void update_post() throws Exception {

        String body = makeBody("first post", "정연수", "안뇽");

        mvc.perform(post(BASE_URL + "/post")
                .content(body) //HTTP Body에 데이터를 담는다
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
        );

        String update_body = makeBody("first post modify", "정연수", "hi");

        mvc.perform(put(BASE_URL + "/put/1")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("수정에 성공하였습니다."));
    }

    @Test
    @DisplayName("글 삭제하기")
    void delete_post() throws Exception {

        String body = makeBody("first post", "정연수", "안뇽");

        mvc.perform(post(BASE_URL + "/post")
                .content(body) //HTTP Body에 데이터를 담는다
                .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
        );


        mvc.perform(delete(BASE_URL + "/delete/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(content().string("삭제 성공하였습니다."));
    }


}
