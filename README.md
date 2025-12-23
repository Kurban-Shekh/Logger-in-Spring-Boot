# Logging in Spring Boot

## What is a Logger?

A **logger** is a component used in an application to record runtime information such as:

- Application flow (startup, shutdown, method execution)
- Debugging details (variable values, decision points)
- Warnings (unexpected but recoverable situations)
- Errors and exceptions

Instead of using `System.out.println()`, modern applications use logging frameworks that provide structured, configurable, and performant logging mechanisms.

In Spring Boot, logging is a critical cross-cutting concern used for observability and troubleshooting.

---

## Why Do We Need Logging in Spring Boot?

Logging is essential in Spring Boot applications for the following reasons:

### Debugging and Troubleshooting
Logs help developers understand what happened before an issue occurred, especially in production environments where debugging is not possible.

### Monitoring and Auditing
Logs provide an audit trail of:
- API requests and responses
- User actions
- System events

This is especially important in microservices and distributed systems.

### Production Diagnostics
In production:
- Logs are often the only source of truth
- Centralized logging tools (ELK, Splunk, CloudWatch) rely on application logs

### Performance and Stability Analysis
Logs help identify:
- Slow API calls
- Resource bottlenecks
- Repeated failures and error patterns

---

## Logging in Spring Boot (Default Setup)

Spring Boot uses **Logback** as the default logging framework through **SLF4J**.

- **SLF4J** → Logging API (abstraction layer)
- **Logback** → Logging implementation

This separation allows switching logging implementations without changing application code.

---

## Example: Using Logger in Spring Boot

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public String getUsers() {
        logger.info("Fetching users");
        logger.debug("This is a debug message");
        return "Users fetched";
    }
}
```
---

## Log Levels

| Level | Description                         |
|-------|-------------------------------------|
| TRACE | Very detailed logs (rarely enabled) |
| DEBUG | Detailed debugging information      |
| INFO  | General application flow            |
| WARN  | Potential issues                    |
| ERROR | Errors and exceptions               |

---

## Logback Configuration (`logback.xml`)

Create the file at:

```text
src/main/resources/logback.xml
```

### Simple Console and File Logging Configuration
```xml
<configuration>

    <!-- Log file location -->
    <property name="LOG_PATH" value="logs"/>
    <property name="LOG_FILE" value="${LOG_PATH}/application.log"/>

    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- File Appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}</file>

        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/application-%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>

        <encoder>
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- Root Logger -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>

```
