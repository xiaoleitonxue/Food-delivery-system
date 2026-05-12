package com.sky.service;

public interface AiChatService {
    String chat(String message, String sessionId);

    boolean isHealthy();
}