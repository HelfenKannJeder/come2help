package de.helfenkannjeder.come2help.server.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.ObjectMapperFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Aspect
@Component
public class RequestLogger {

    private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapperForRestEndpoint();
    private final Logger logger = LoggerFactory.getLogger(RequestLogger.class);

    @Around("execution(public * de.helfenkannjeder.come2help.server.rest.*Controller.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        logger.info(format("REST request for %s with parameters: %s", signature.toShortString(), getParamString(signature, joinPoint.getArgs())));

        Object result = joinPoint.proceed();

        logMethodExit(signature, result);

        return result;
    }

    private void logMethodExit(CodeSignature signature, Object result) {
        String logMessage = format("%s completed", signature.toShortString());
        if(result != null) {
            try {
                logMessage += format(" with response %s", objectMapper.writeValueAsString(result));
            } catch (Exception ex) {
                logMessage += " but result object can not be parsed";
            }
        }

        logger.info(logMessage);
    }

    private String getParamString(CodeSignature signature, Object[] args) {
        String result = "";

        try {
            String[] parameterNames = signature.getParameterNames();
            for (int i = 0; i < parameterNames.length; i++) {
                result += parameterNames[i] + "=" + args[i];
            }
        } catch (Exception ex) {
            result += "can not process parameters";
        }

        return result;
    }
}

