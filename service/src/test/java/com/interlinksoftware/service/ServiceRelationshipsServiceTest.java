package com.interlinksoftware.service;

import static com.interlinksoftware.exception.ErrorType.INVALID_PARENT_OR_CHILD;
import static com.interlinksoftware.util.ObjectFactory.CHILD_ID;
import static com.interlinksoftware.util.ObjectFactory.IMPACT;
import static com.interlinksoftware.util.ObjectFactory.LABEL;
import static com.interlinksoftware.util.ObjectFactory.PARENT_ID;
import static com.interlinksoftware.util.ObjectFactory.buildServiceModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.interlinksoftware.exception.ServiceRelationshipsException;
import com.interlinksoftware.processor.ServiceModelBatchProcessor;
import com.interlinksoftware.repository.ServiceRelationshipsRepository;
import com.interlinksoftware.repository.entity.ServiceRelationships;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceRelationshipsServiceTest {

  @Mock
  private ServiceRelationshipsRepository serviceRelationshipsRepository;

  @Mock
  private ServiceModelBatchProcessor serviceModelBatchProcessor;

  @InjectMocks
  private ServiceRelationshipsServiceImpl serviceModelService;

  @Test
  void shouldModelServiceGetAllList() {

    List<ServiceRelationships> list = Collections.singletonList(buildServiceModel());
    when(serviceRelationshipsRepository.findAll()).thenReturn(list);

    List<ServiceRelationships> serviceRelationshipsList = serviceModelService.getAllServiceRelationships();

    assertThat(serviceRelationshipsList).isNotNull();
    assertThat(serviceRelationshipsList.get(0).getParent())
        .isEqualTo(PARENT_ID);
    assertThat(serviceRelationshipsList.get(0).getChild())
        .isEqualTo(CHILD_ID);
    assertThat(serviceRelationshipsList.get(0).getLabel())
        .isEqualTo(LABEL);
    assertThat(serviceRelationshipsList.get(0).getImpact())
        .isEqualTo(IMPACT);
  }

  @Test
  void shouldModelServiceGetParentAndChild() {

    when(serviceRelationshipsRepository.findByParentAndChild(PARENT_ID, CHILD_ID))
        .thenReturn(Optional.of(buildServiceModel()));

    Optional<ServiceRelationships> optionalServiceRelationships = serviceModelService
        .getParentAndChildServiceRelationship(PARENT_ID, CHILD_ID);

    assertThat(optionalServiceRelationships).isNotNull();
    assertThat(optionalServiceRelationships.get().getParent())
        .isEqualTo(PARENT_ID);
    assertThat(optionalServiceRelationships.get().getChild())
        .isEqualTo(CHILD_ID);
    assertThat(optionalServiceRelationships.get().getLabel())
        .isEqualTo(LABEL);
    assertThat(optionalServiceRelationships.get().getImpact())
        .isEqualTo(IMPACT);

  }

  @Test
  void shouldReturnNotFounException_WhenModelServiceGetParentAndChildIdIsNotMatch() {
    when(serviceRelationshipsRepository.findByParentAndChild(PARENT_ID, CHILD_ID))
        .thenReturn(Optional.empty());

    ServiceRelationshipsException exceptionThrown =
        assertThrows(
            ServiceRelationshipsException.class,
            () ->
                serviceModelService.getParentAndChildServiceRelationship(PARENT_ID, CHILD_ID));

    assertThat(exceptionThrown.getErrorType()).isEqualTo(INVALID_PARENT_OR_CHILD);
  }

}
