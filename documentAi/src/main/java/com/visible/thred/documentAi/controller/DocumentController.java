package com.visible.thred.documentAi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.visible.thred.documentAi.model.Document;
import com.visible.thred.documentAi.response.Response;
import com.visible.thred.documentAi.service.DocumentService;
import com.visible.thred.documentAi.service.GeminiService;

@RestController
@RequestMapping("v1/documents")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private GeminiService geminiService;

	@GetMapping("/wordfrequency/{documentId}")
	public ResponseEntity<Map<String, Object>> getWordFrequencyFormDoc(@PathVariable Long documentId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Map<String, Long>> result = Optional.of(documentService.getWordFrequency(documentId));
			if (result.isPresent()) {				
				response.put("message", "successful..");
				response.put("data", result.get());
				response.put("code", HttpStatus.OK);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "data not available..");
				response.put("code", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			
			response.put("message", "failed.." );
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);			
		}
	}

	@PostMapping("/synonyms")
	public ResponseEntity<Map<String, Object>> getSynonymsForLongestWord(@RequestParam Long documentid) {
		Map<String, Object> response = new HashMap<>();

		Optional<List<String>> result;
		try {
			result = Optional.of(geminiService.getSynonyms(documentid));

			if (result.isPresent()) {
				response.put("message", "successful..");
				response.put("data", result.get());
				response.put("code", HttpStatus.OK);
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				response.put("message", "data not available..");
				response.put("code", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.put("message", "failed.." );
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);	
		}
	}

	@PostMapping("/createDoc")
	public Response<Document> createDocument(@RequestParam MultipartFile file, @RequestParam Long userid)
			throws Throwable {
		Response<Document> response = new Response<Document>();
		try {
			Optional<Document> result = Optional.of(documentService.createDocument(file, userid));
			if (result.isPresent()) {
				response.setstatus(HttpStatus.CREATED);
				response.setMessage("Doccument created  successfully: " + file.getOriginalFilename());
				response.setData(result.get());
				return response;
			} else {
				response.setstatus(HttpStatus.BAD_REQUEST);
				response.setMessage("document creation failed:" + file.getOriginalFilename());
				response.setData(null);
				return response;
			}
		} catch (Exception e) {
			response.setstatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("document creation failed:" + file.getOriginalFilename());
			response.setData(null);
			return response;
		}
	}

	@PutMapping("/updateDoc")
	public Response<Document> updateDocument(@RequestParam MultipartFile file, @RequestParam Long id) throws Throwable {
		Response<Document> response = new Response<Document>();
		try {
			Optional<Document> result = Optional.of(documentService.updateDocument(file, id));
			if (result.isPresent()) {
				response.setstatus(HttpStatus.OK);
				response.setMessage("Doccument updated successfully: " + file.getOriginalFilename());
				response.setData(result.get());
				return response;
			} else {
				response.setstatus(HttpStatus.BAD_REQUEST);
				response.setMessage("document updation failed:" + file.getOriginalFilename());
				response.setData(null);
				return response;
			}
		} catch (Exception e) {
			response.setstatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("document creation failed:" + file.getOriginalFilename());
			response.setData(null);
			return response;
		}
	}

	@DeleteMapping("/deleteDoc/{id}")
	public Response<Boolean> deleteDocument(@PathVariable Long id) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			Optional<Boolean> result = Optional.of(documentService.deleteDocument(id));
			if (result.isPresent()) {
				response.setstatus(HttpStatus.OK);
				response.setMessage("Doccument deleted successfully: ");
				response.setData(Boolean.TRUE);
				return response;
			} else {
				response.setstatus(HttpStatus.BAD_REQUEST);
				response.setMessage("document deletion failed:");
				response.setData(Boolean.FALSE);
				return response;
			}
		} catch (Exception e) {
			response.setstatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("document creation failed:");
			response.setData(Boolean.FALSE);
			return response;
		}
	}
}