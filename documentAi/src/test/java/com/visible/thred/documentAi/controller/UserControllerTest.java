package com.visible.thred.documentAi.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.visible.thred.documentAi.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUsersWithoutUploads_invalidDateFormat() throws Exception {
        mockMvc.perform(get("/v1/users/without-doc-uploads")
                .param("fromdate", "invalid-date")
                .param("todate", "invalid-date"))
                .andExpect(status().isExpectationFailed())
                .andExpect(content().json("{\"code\":\"BAD_REQUEST\","
                		+ "\"message\":\"Invalid date format. Please use 'dd-MM-yyyy HH:mm'\","	
                		+ "\"exception\":\"Text 'invalid-date' could not be parsed at index 0\"}"
                		+ ""));
    }
}