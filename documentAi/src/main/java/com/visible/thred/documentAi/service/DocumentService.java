package com.visible.thred.documentAi.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.visible.thred.documentAi.exception.MyResourceNotFoundException;
import com.visible.thred.documentAi.model.Document;
import com.visible.thred.documentAi.model.User;
import com.visible.thred.documentAi.repository.DocumentRepository;
import com.visible.thred.documentAi.repository.UserRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private UserRepository userRepository;

	public Map<String, Long> getWordFrequency(Long documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new MyResourceNotFoundException("Document not found"));
		String fileContent;
		if (null != document.getFiledata()) {
			fileContent = new String(document.getFiledata(), StandardCharsets.UTF_8);
		} else {
			throw new MyResourceNotFoundException("Document is emprty!!");
		}
		// Exclude specified words "the", "Me", "You", "I", "Of", "And", "A", "We"
		List<String> excludeWordslist = List.of("The", "Me", "You", "I", "Of", "And",
				"A", "We",  "in", "to", "your", "for", "is", "are", "were", "an").stream()
				.map(String::toLowerCase).collect(Collectors.toList());
		String[] words = fileContent.split("\\W+");

		Map<String, Long> map = Arrays.stream(words).map(String::toLowerCase).filter(word -> !excludeWordslist.contains(word))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		Map<String, Long> result = map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return result;
	}

	public Document createDocument(MultipartFile file, long userid) throws Throwable {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new RuntimeException("User not found with user id " + userid));
		String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
		String[] words = fileContent.toString().split("\\W+");
		int wordcount = words.length;
		Document document = new Document(fileName, wordcount, LocalDateTime.now(), file.getBytes(), user);
		return documentRepository.save(document);
	}

	public Document updateDocument(MultipartFile file, Long documentId) throws Throwable {
		Optional<Document> optionalDoc = documentRepository.findById(documentId);
		Document originalDoc=null;
		if (optionalDoc.isPresent()) {

			originalDoc = optionalDoc.get();

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			originalDoc.setName(fileName);

			if (Objects.nonNull(file.getBytes())) {
				String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
				String[] words = fileContent.toString().split("\\W+");
				int wordcount = words.length;
				originalDoc.setFiledata(file.getBytes());
				originalDoc.setWordCount(wordcount);
			}
			originalDoc.setUpdatedTime(LocalDateTime.now());
			documentRepository.save(originalDoc);
			
			return originalDoc;
		}
		return originalDoc;
	}

	public Boolean deleteDocument(Long id) throws Exception {
		boolean isdelted = false;
		Optional<Document> optionalDoc=documentRepository.findById(id);
		if(Objects.nonNull(optionalDoc.get()))
		{
			documentRepository.deleteById(id);
			isdelted = true;
		}		
		return isdelted;
	}

}
