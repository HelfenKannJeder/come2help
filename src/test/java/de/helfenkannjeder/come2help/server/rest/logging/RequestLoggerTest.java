package de.helfenkannjeder.come2help.server.rest.logging;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.ObjectMapperFactory;
import de.helfenkannjeder.come2help.server.cucumber.util.AbilityObjectMother;
import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.AbilitiesController;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityResponseDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestLoggerTest {
    private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapperForRestEndpoint();

    private AbilitiesService mockAbilitiesService;
    private AbilitiesController proxyController;

    @Before
    public void setUp() throws Exception {
        mockAbilitiesService = mock(AbilitiesService.class);
        RequestLogger requestLogger = new RequestLogger();
        AbilitiesController abilitiesController = new AbilitiesController(mockAbilitiesService);

        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(abilitiesController);
        proxyFactory.addAspect(requestLogger);
        proxyController = proxyFactory.getProxy();

        LogSession.start();
    }

    @After
    public void tearDown() throws Exception {
        LogSession.close();
    }

    @Test
    public void requestParametersShouldBeLogged() throws Exception {
        Ability abilityToAnswerWith = AbilityDto.createAbility(AbilityObjectMother.anyValidAbility().setId(1L));
        when(mockAbilitiesService.findById(anyLong())).thenReturn(abilityToAnswerWith);

        proxyController.getAbilityById(abilityToAnswerWith.getId());
        String logMessage = LogSession.getLogMessage();

        assertThat(logMessage, containsString("request-param-id=" + abilityToAnswerWith.getId()));
    }

    @Test
    public void methodResultShouldBeLogged() throws Exception {
        List<Ability> abilitiesToAnswerWith = Collections.singletonList(AbilityDto.createAbility(AbilityObjectMother.anyValidAbility()));
        when(mockAbilitiesService.findAllOrderByName()).thenReturn(abilitiesToAnswerWith);

        proxyController.getAbilities();
        String logMessage = LogSession.getLogMessage();

        String expectedResponseString = objectMapper.writeValueAsString(abilitiesToAnswerWith.stream().map(AbilityResponseDto::createFullDto).collect(Collectors.toList()));
        assertThat(logMessage, containsString("response=" + expectedResponseString));
    }
}