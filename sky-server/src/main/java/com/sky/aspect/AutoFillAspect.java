package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，用于公共字段自动填充
 * 在数据库操作（插入/更新）时自动填充 createTime、updateTime、createUser、updateUser 字段
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 定义切入点
     * 拦截 com.sky.mapper 包下所有类的方法，且方法上有 @AutoFill 注解
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 前置通知：在目标方法执行前进行公共字段填充
     * @param joinPoint 连接点，包含方法签名和参数信息
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段填充");

        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法上的 @AutoFill 注解，判断是插入还是更新操作
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 获取方法参数（通常是实体对象）
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        // 第一个参数即为要操作的实体对象
        Object entity = args[0];

        // 获取当前时间和当前登录用户ID
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 根据操作类型填充不同的字段
        if (operationType == OperationType.INSERT) {
            // 插入操作：填充创建时间、更新时间、创建人、更新人
            Method setCreateTime = entity.getClass().getMethod("setCreateTime", LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            Method setCreateUser = entity.getClass().getMethod("setCreateUser", Long.class);
            Method setUpdateUser = entity.getClass().getMethod("setUpdateUser", Long.class);

            setCreateTime.invoke(entity, now);
            setUpdateTime.invoke(entity, now);
            setCreateUser.invoke(entity, currentId);
            setUpdateUser.invoke(entity, currentId);
        } else if (operationType == OperationType.UPDATE) {
            // 更新操作：只填充更新时间和更新人
            Method setUpdateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getMethod("setUpdateUser", Long.class);

            setUpdateTime.invoke(entity, now);
            setUpdateUser.invoke(entity, currentId);
        }
    }
}
