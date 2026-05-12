package com.sky.service.impl;

import com.sky.service.AiChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AiChatServiceImpl implements AiChatService {

    @Value("${sky.ai-chat.url:http://localhost:8000}")
    private String aiChatUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String chat(String message, String sessionId) {
        try {
            String url = aiChatUrl + "/chat";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("message", message);
            body.put("session_id", sessionId != null ? sessionId : "default");

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("response");
            } else {
                log.error("AI客服响应异常: {}", response.getStatusCode());
                return "抱歉，智能客服暂时不可用，请稍后再试。";
            }
        } catch (Exception e) {
            log.error("调用AI客服失败: {}", e.getMessage(), e);
            return "抱歉，连接智能客服失败，请稍后再试。";
        }
    }

    @Override
    public boolean isHealthy() {
        try {
            String url = aiChatUrl + "/health";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("AI客服健康检查失败: {}", e.getMessage());
            return false;
        }
    }
}