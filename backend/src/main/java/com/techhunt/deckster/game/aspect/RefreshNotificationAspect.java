package com.techhunt.deckster.game.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RefreshNotificationAspect {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Around("@annotation(com.techhunt.deckster.game.aspect.RefreshNotification)")
    public void refreshNotification(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        simpMessagingTemplate.convertAndSend("/public", "");
    }
}
