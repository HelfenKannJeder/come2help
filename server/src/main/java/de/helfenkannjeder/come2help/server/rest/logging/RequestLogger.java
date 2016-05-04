package de.helfenkannjeder.come2help.server.rest.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.ObjectMapperFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLogger {

    private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapperForRestEndpoint();

    @Around("execution(public * de.helfenkannjeder.come2help.server.rest.*Controller.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        logMethodParams(joinPoint, signature);

        Object result = joinPoint.proceed();
        logMethodExit(signature, result);

        return result;
    }

    private void logMethodParams(ProceedingJoinPoint joinPoint, CodeSignature signature) {
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        try {
            for (int i = 0; i < parameterNames.length; i++) {
                LogSession.log("request-param-" + parameterNames[i], args[i] != null ? args[i].toString() : "null");
            }
        } catch (Exception ex) {
            LogSession.log("request-param-log-error", "true");
        }
    }

    private void logMethodExit(CodeSignature signature, Object result) {
        if(result != null) {
            try {
                LogSession.log("response", objectMapper.writeValueAsString(result));
            } catch (Exception ex) {
                LogSession.log("response", "response object can not be parsed");
            }
        }
    }
}

