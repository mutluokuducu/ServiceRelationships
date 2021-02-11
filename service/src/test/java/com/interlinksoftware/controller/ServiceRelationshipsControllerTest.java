package com.interlinksoftware.controller;

import static com.interlinksoftware.constants.RestApiUrls.CSV_FILE_PATH;
import static com.interlinksoftware.constants.RestApiUrls.GET_LIST;
import static com.interlinksoftware.constants.RestApiUrls.GET_PARENT_CHILD_LIST;
import static com.interlinksoftware.constants.RestApiUrls.READ_CSV_FILE;
import static com.interlinksoftware.exception.ErrorType.INVALID_PARENT_OR_CHILD;
import static com.interlinksoftware.util.ObjectFactory.CHILD_ID;
import static com.interlinksoftware.util.ObjectFactory.PARENT_ID;
import static com.interlinksoftware.util.ObjectFactory.buildServiceModel;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.interlinksoftware.exception.GlobalExceptionHandler;
import com.interlinksoftware.exception.ServiceRelationshipsException;
import com.interlinksoftware.repository.entity.ServiceRelationships;
import com.interlinksoftware.service.ServiceRelationshipsService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ServiceRelationshipsControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ServiceRelationshipsService serviceRelationshipsService;

  @InjectMocks
  private ServiceRelationshipsController serviceRelationshipsController;

  @BeforeEach
  public void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(serviceRelationshipsController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void csvFileReadAndSaveToDatabaseResponseOk() throws Exception {
    this.mockMvc
        .perform(
            post(READ_CSV_FILE, CSV_FILE_PATH)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk());
  }


  @Test
  void shouldReturnParentAndChild() throws Exception {
    when(serviceRelationshipsService.getParentAndChildServiceRelationship(PARENT_ID, CHILD_ID))
        .thenReturn(Optional.of(buildServiceModel()));

    this.mockMvc
        .perform(
            get(GET_PARENT_CHILD_LIST, PARENT_ID, CHILD_ID)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
  }

  @Test
  void shouldReturnNotFindParentAndChild_WhenParentIdOrChildIdIsNotMatch() throws Exception {

    doThrow(new ServiceRelationshipsException(INVALID_PARENT_OR_CHILD))
        .when(serviceRelationshipsService).getParentAndChildServiceRelationship("PARENT_ID", "CHILD_ID");

    this.mockMvc
        .perform(
            get(GET_PARENT_CHILD_LIST, "PARENT_ID", "CHILD_ID")
                .contentType(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnAllList() throws Exception {
    List<ServiceRelationships> list = Collections.singletonList(buildServiceModel());
    when(serviceRelationshipsService.getAllServiceRelationships())
        .thenReturn(list);

    this.mockMvc
        .perform(
            get(GET_LIST)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
  }
}
