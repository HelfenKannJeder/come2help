package de.helfenkannjeder.come2help.server.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.ObjectMapperFactory;
import de.helfenkannjeder.come2help.server.cucumber.util.AbilityObjectMother;
import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityResponseDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestLoggerTest {
    private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapperForRestEndpoint();

    private AbilitiesService mockAbilitiesService;
    private Appender mockAppender;
    private AbilitiesController proxyController;
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setUp() throws Exception {
        mockAbilitiesService = mock(AbilitiesService.class);
        mockAppender = mock(Appender.class);
        RequestLogger requestLogger = new RequestLogger();
        AbilitiesController abilitiesController = new AbilitiesController(mockAbilitiesService);

        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(abilitiesController);
        proxyFactory.addAspect(requestLogger);
        proxyController = proxyFactory.getProxy();

        mockAppender = mock(Appender.class);
        captorLoggingEvent = ArgumentCaptor.forClass(LoggingEvent.class);

        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @After
    public void tearDown() throws Exception {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void requestWithoutParametersIsLogged() throws Exception {
        List<Ability> abilitiesToAnswerWith = Collections.singletonList(AbilityDto.createAbility(AbilityObjectMother.anyValidAbility()));
        when(mockAbilitiesService.findAllOrderByName()).thenReturn(abilitiesToAnswerWith);

        proxyController.getAbilities();

        verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture());
        final List<LoggingEvent> loggingEvent = captorLoggingEvent.getAllValues();

        assertThat(loggingEvent.get(0).getLevel(), is(Level.INFO));
        assertThat(loggingEvent.get(0).getFormattedMessage(),
                is("REST request for AbilitiesController.getAbilities() with parameters: "));
        String expectedResponseString = objectMapper.writeValueAsString(abilitiesToAnswerWith.stream().map(AbilityResponseDto::createFullDto).collect(Collectors.toList()));
        assertThat(loggingEvent.get(1).getFormattedMessage(),
                is("AbilitiesController.getAbilities() completed with response " + expectedResponseString));
    }

    @Test
    public void requestParametersShouldBeLogged() throws Exception {
        Ability abilityToAnswerWith = AbilityDto.createAbility(AbilityObjectMother.anyValidAbility());
        when(mockAbilitiesService.findById(anyLong())).thenReturn(abilityToAnswerWith);

        proxyController.getAbilityById(abilityToAnswerWith.getId());

        verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture());
        final List<LoggingEvent> loggingEvent = captorLoggingEvent.getAllValues();
        assertThat(loggingEvent.get(0).getFormattedMessage(),
                is("REST request for AbilitiesController.getAbilityById(..) with parameters: id=null"));
    }
}