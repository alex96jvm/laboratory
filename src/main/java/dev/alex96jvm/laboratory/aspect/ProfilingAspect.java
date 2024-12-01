package dev.alex96jvm.laboratory.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class ProfilingAspect {

    private final Map<String, Long> executionCount = new ConcurrentHashMap<>();
    private final Map<String, Long> executionTime = new ConcurrentHashMap<>();

    @Around("@within(org.springframework.stereotype.Service) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object profileMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        executionCount.merge(methodName, 1L, Long::sum);
        executionTime.merge(methodName, duration, Long::sum);
        return result;
    }

    @Scheduled(fixedRate = 60000)
    public void printStats() {
        System.out.println("Method execution statistics:");
        executionCount.forEach((method, count) -> {
            long totalTime = executionTime.getOrDefault(method, 0L);
            System.out.printf("Method: %s, Executions: %d, Total Time: %d ms%n", method, count, totalTime);
        });
    }
}
