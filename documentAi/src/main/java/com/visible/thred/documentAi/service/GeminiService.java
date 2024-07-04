package com.visible.thred.documentAi.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.visible.thred.documentAi.exception.MyResourceNotFoundException;
import com.visible.thred.documentAi.model.Document;
import com.visible.thred.documentAi.repository.DocumentRepository;

@Service
public class GeminiService {

	//words with only alphabets
	private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	VertexAI vertexAI;

	@Autowired
	GenerativeModel generativeModel;

	//service returns synonyms from document
	public List<String> getSynonyms(Long documentid) throws Exception {
		Optional<Document> document = documentRepository.findById(documentid);
		List<String> synonysoutput = null;
		if (document.isPresent()) {
			String fileContent = getFileContent(document.get().getFiledata());
			if (Objects.nonNull(fileContent)) {
				if (Objects.nonNull(fileContent)) {
					List<String> longestWordlist = findLongestWord(fileContent);
					synonysoutput = new ArrayList<String>();
					for (String longestWord : longestWordlist) {

						if (validateLogestWord(longestWord)) {
							synonysoutput.add(GetGeminiApi(longestWord));
						}
					}
				}
				
			}else {
				throw new MyResourceNotFoundException(" Document empty ....");
			}
			
		} else {
			throw new MyResourceNotFoundException(" Document is not available ....");
		}
		return synonysoutput;
	}

	//Approch 1: Using vertext api and model gemini-1.5-flash-001
	private String GetGeminiApi(String longestWord) throws IOException {
		GenerateContentResponse response = generativeModel
				.generateContent("Suggest synonyms for the word: " + longestWord);
		String output = ResponseHandler.getText(response);
		return output;
	}
	
	// get file content as String and validateit
	private String getFileContent(byte[] filedata) {
		String fileContent = null;
		if (Objects.nonNull(filedata)) {
			fileContent = new String(filedata, StandardCharsets.UTF_8);
			if (Objects.nonNull(fileContent)) {
				if (!fileContent.isBlank() && !fileContent.isEmpty()) {
					return fileContent;
				}
			}
		}
		return null;
	}
	
	//validate word 
	private boolean validateLogestWord(String longestWord) {
		boolean isvalid = false;
		if (Objects.nonNull(longestWord)) {
			if (!longestWord.isBlank() && !longestWord.isEmpty()) {
				isvalid = true;
			}
		}

		return isvalid;
	}
	//findLongestWord : document may contain multiple words with same length it returns List of longestwords 
	private List<String> findLongestWord(String content) {
		String[] words = content.split("\\W+");
		List<String> filteredWords = Arrays.stream(words).filter(word -> WORD_PATTERN.matcher(word).matches())
				.collect(Collectors.toList());

		int maxLength = filteredWords.stream().mapToInt(String::length).max().orElse(0);

		return filteredWords.stream().filter(word -> word.length() == maxLength).distinct()
				.collect(Collectors.toList());
	}
	
	//this method uses approch 2: using rest template and gemini project api key...
	/*private String callGeminiApi(String longestWord)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> content = new HashMap<>();
		content.put("text", "Suggest synonyms for the word: " + longestWord);

		Map<String, Object> parts = new HashMap<>();
		parts.put("parts", new Map[] { content });

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("contents", new Map[] { parts });

		HttpEntity<Map<String, Object>> inputentity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, inputentity,
					String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				 return response.getBody();
			} else {
				throw new RuntimeException("Failed: HTTP error code: " + response.getStatusCode());
			}
			
		} catch (RestClientException e) {
			throw new RuntimeException("Failed to access the API: " + e.getMessage(), e);
		}
	
	}
*/
}
