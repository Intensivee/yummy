package com.orange.mainservice.component;

import com.orange.mainservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static com.orange.mainservice.utils.DummyConstants.DUMMY_LONG;
import static com.orange.mainservice.utils.DummyConstants.DUMMY_STRING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComponentServiceTest {

    @Autowired
    private ComponentService componentService;

    @MockBean
    private ComponentRepository componentRepository;

    @Test
    @DisplayName("get by id - when exists - should return component")
    void getById_whenExists_shouldReturnComponent() {
        // given
        Component expectedComponent = new Component(
                DUMMY_LONG, DUMMY_STRING, true, true, Collections.emptySet()
        );
        when(componentRepository.findById(expectedComponent.getId()))
                .thenReturn(Optional.of(expectedComponent));

        // when
        Component actualComponent = componentService.getById(expectedComponent.getId());

        // then
        assertThat(actualComponent).isSameAs(expectedComponent);
    }

    @Test
    @DisplayName("get by id - when not exists - should throw exception")
    void getById_whenNotExists_shouldThrowException() {
        // given
        when(componentRepository.findById(DUMMY_LONG)).thenReturn(Optional.empty());
        String expectedErrorMessage = "Resource: Component with id = '" + DUMMY_LONG + "' not found.";

        // when
        // then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> componentService.getResponseById(DUMMY_LONG)
        );
        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }
}
