package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("UserShopController")
@RequestMapping("/user/shop")
@Api("店铺管理")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result getStatus() {
        try {
            Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
            log.info("获取店铺状态：{}",status == 1 ? "营业中" : "打烊中");
            return Result.success(status);
        } catch (Exception e) {
            log.warn("获取店铺状态失败，Redis可能未启动: {}", e.getMessage());
            // Redis不可用时默认返回营业中
            return Result.success(1);
        }
    }
}
