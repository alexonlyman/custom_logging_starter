package logging_starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties loggingProperties;


    private boolean shouldLog(String level) {

        List<String> levels = List.of("debug", "info", "warn", "error");
        int configuredIndex = levels.indexOf(loggingProperties.getLevel().toLowerCase());
        int currentIndex = levels.indexOf(level.toLowerCase());
        return currentIndex >= configuredIndex;
    }

    @Pointcut("@annotation(logging_starter.Logged)")
    public void loggingMethodByCustomAnnotation() {
    }

    @Before("loggingMethodByCustomAnnotation()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        if (!loggingProperties.isEnabled() || shouldLog("info")) return;

        Object[] args = joinPoint.getArgs();
        log.info("Calling method: {} with args: {}", joinPoint.getSignature().getName(), Arrays.toString(args));
    }

    @AfterReturning(pointcut = "loggingMethodByCustomAnnotation()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (!loggingProperties.isEnabled() || shouldLog("info")) return;

        log.info("Method: {} executed successfully with result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "loggingMethodByCustomAnnotation()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        if (!loggingProperties.isEnabled() || shouldLog("error")) return;

        log.error("Exception in method: {} - {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    @Around("loggingMethodByCustomAnnotation()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!loggingProperties.isEnabled() || shouldLog("debug")) return joinPoint.proceed();

        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            log.debug("Method: {} executed in {} ms", joinPoint.getSignature().getName(), (end - start));
        }
    }

}
