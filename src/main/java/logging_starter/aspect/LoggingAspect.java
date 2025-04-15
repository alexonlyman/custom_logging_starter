package logging_starter.aspect;

import logging_starter.property.LoggingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
/**
 * Aspect for logging method execution details.
 * This aspect intercepts methods annotated with {@link logging_starter.annotation.Logged}
 * and logs various details about their execution.
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties loggingProperties;

    /**
     * Determines if logging should be performed based on the configured logging level.
     *
     * @param level the logging level to check
     * @return true if logging should be performed, false otherwise
     */
    private boolean shouldLog(String level) {

        List<String> levels = List.of("debug", "info", "warn", "error");
        int configuredIndex = levels.indexOf(loggingProperties.getLevel().toLowerCase());
        int currentIndex = levels.indexOf(level.toLowerCase());
        return currentIndex >= configuredIndex;
    }

    /**
     * Pointcut that matches methods annotated with {@link logging_starter.annotation.Logged}.
     */
    @Pointcut("@annotation(logging_starter.annotation.Logged)")
    public void loggingMethodByCustomAnnotation() {
    }

    /**
     * Advice that runs before the methods matched by the pointcut.
     * Logs the method call details if logging is enabled and the level is appropriate.
     *
     * @param joinPoint the join point representing the method call
     */
    @Before("loggingMethodByCustomAnnotation()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        if (!loggingProperties.isEnabled() || shouldLog("info")) return;

        Object[] args = joinPoint.getArgs();
        log.info("Calling method: {} with args: {}", joinPoint.getSignature().getName(), Arrays.toString(args));
    }

    /**
     * Advice that runs after the methods matched by the pointcut return successfully.
     * Logs the method execution result if logging is enabled and the level is appropriate.
     *
     * @param joinPoint the join point representing the method call
     * @param result the result returned by the method
     */
    @AfterReturning(pointcut = "loggingMethodByCustomAnnotation()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (!loggingProperties.isEnabled() || shouldLog("info")) return;

        log.info("Method: {} executed successfully with result: {}", joinPoint.getSignature().getName(), result);
    }

    /**
     * Advice that runs after the methods matched by the pointcut throw an exception.
     * Logs the exception details if logging is enabled and the level is appropriate.
     *
     * @param joinPoint the join point representing the method call
     * @param exception the exception thrown by the method
     */
    @AfterThrowing(pointcut = "loggingMethodByCustomAnnotation()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        if (!loggingProperties.isEnabled() || shouldLog("error")) return;

        log.error("Exception in method: {} - {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    /**
     * Advice that runs around the methods matched by the pointcut.
     * Logs the execution time of the method if logging is enabled and the level is appropriate.
     *
     * @param joinPoint the join point representing the method call
     * @return the result of the method call
     * @throws Throwable if the method call throws an exception
     */
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
