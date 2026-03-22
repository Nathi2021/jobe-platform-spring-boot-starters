package dev.jobe.platform.transformer.autoconfigure;

import dev.jobe.platform.transformer.core.processor.TextTransformer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;

import java.util.*;

@Aspect
public class TextTransformerAspect {

    private final TextTransformer textTransformer;

    public TextTransformerAspect(TextTransformer textTransformer) {
        this.textTransformer = textTransformer;
    }

    @Around("@annotation(transform)")
    public Object transformAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        return textTransformer.transform(result);
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController) || " +
        "@within(org.springframework.stereotype.Controller)")
    public Object transformControllerResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            if (body != null) {
                return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(textTransformer.transform(body));
            }
            return responseEntity;
        }
        return textTransformer.transform(result);
    }
}
