package logging_starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Annotation for methods that require logging.
 * This annotation can be used to mark methods whose execution
 * should be logged.
 *
 * <p>Example usage:
 * <pre>{@code
 * @Logged
 * public void someMethod() {
 *     // Method code
 * }
 * }</pre>
 *
 * <p>The annotation is retained at runtime, allowing it to be used
 * for various purposes, such as Aspect-Oriented Programming (AOP) for logging.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logged {

}
