package com.visible.thred.documentAi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.visible.thred.documentAi.service.DocumentService;
import com.visible.thred.documentAi.service.GeminiService;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DocumentService documentService;

	@MockBean
	private GeminiService geminiService;

	@InjectMocks
	private DocumentController documentController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetWordFrequencyFormDoc_Success() throws Exception {
		Map<String, Long> wordFrequency = new HashMap<>();
		wordFrequency.put("test", 2L);
		wordFrequency.put("word", 3L);

		when(documentService.getWordFrequency(1L)).thenReturn(wordFrequency);

		mockMvc.perform(get("/v1/documents/wordfrequency/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("successful..")).andExpect(jsonPath("$.data.test").value(2L))
				.andExpect(jsonPath("$.data.word").value(3L));
	}

	@Test
	public void testGetSynonymsForLongestWord_Success() throws Exception {
		when(geminiService.getSynonyms(1L)).thenReturn(Arrays.asList("synonym1", "synonym2"));

		mockMvc.perform(post("/v1/documents/synonyms").param("documentid", "2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("successful.."));
	}

	@Test
	public void testGetSynonymsForLongestWord_Exception() throws Exception {
		when(geminiService.getSynonyms(1L)).thenThrow(new RuntimeException("Service error"));

		mockMvc.perform(post("/v1/documents/synonyms").param("documentid", "1"))
				.andExpect(status().isExpectationFailed()).andExpect(jsonPath("$.message").value("failed.."));
	}

}
