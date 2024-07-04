package com.visible.thred.documentAi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

@Component
@Configuration(proxyBeanMethods = false)
public class GeminiConfiguration {

	@Value("${gemini.project.id}")
	private String projectId;

	@Value("${gemini.project.location}")
	private String location;

	@Value("${gemini.project.modelName}")
	private String modelName;

	@Bean
	public VertexAI vertexAI() {
		return new VertexAI(projectId, location);
	}

	@Bean
	public GenerativeModel generativeModel(VertexAI vertexAI) {
		return new GenerativeModel(modelName, vertexAI);
	}
}
