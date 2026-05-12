package com.sky.controller.user;

import com.sky.dto.ChatRequestDTO;
import com.sky.result.Result;
import com.sky.service.AiChatService;
import com.sky.vo.ChatResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/ai-chat")
@Api(tags = "智能客服接口")
@Slf4j
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping("/chat")
    @ApiOperation("智能客服对话")
    public Result<ChatResponseVO> chat(@RequestBody ChatRequestDTO chatRequest) {
        log.info("用户请求智能客服: {}", chatRequest.getMessage());

        String sessionId = chatRequest.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = "user_default_" + System.currentTimeMillis();
        }

        String response = aiChatService.chat(chatRequest.getMessage(), sessionId);

        ChatResponseVO chatResponse = ChatResponseVO.builder()
                .response(response)
                .sessionId(sessionId)
                .build();

        return Result.success(chatResponse);
    }

    @GetMapping("/health")
    @ApiOperation("智能客服健康检查")
    public Result<String> healthCheck() {
        boolean healthy = aiChatService.isHealthy();
        if (healthy) {
            return Result.success("智能客服运行正常");
        } else {
            return Result.error("智能客服暂时不可用");
        }
    }
}