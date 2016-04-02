package de.helfenkannjeder.come2help.server.rest.logging;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class RestLogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger("requestLog");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LogSession.start();
        LogSession.log("HTTP-Method", request.getMethod());
        LogSession.log("request-url", request.getRequestURI());

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            LogSession.log("HTTP-Status-Code", String.valueOf(response.getStatus()));

            Throwable exceptionToLog = LogSession.getExceptionToLog();
            if(HttpStatus.valueOf(response.getStatus()).is5xxServerError() || exceptionToLog != null) {
                if(exceptionToLog != null) {
                    logger.error(LogSession.getLogMessage(), exceptionToLog);
                }
                else {
                    logger.error(LogSession.getLogMessage());
                }
            }
            else {
                logger.info(LogSession.getLogMessage());
            }

            LogSession.close();
        }
    }

    @Override
    public void destroy() {

    }
}
