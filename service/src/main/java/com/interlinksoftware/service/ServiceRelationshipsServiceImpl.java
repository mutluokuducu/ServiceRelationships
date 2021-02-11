package com.interlinksoftware.service;

import static com.interlinksoftware.exception.ErrorType.INVALID_PARENT_OR_CHILD;

import com.interlinksoftware.exception.ServiceRelationshipsException;
import com.interlinksoftware.processor.ServiceModelBatchProcessor;
import com.interlinksoftware.repository.ServiceRelationshipsRepository;
import com.interlinksoftware.repository.entity.ServiceRelationships;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRelationshipsServiceImpl implements ServiceRelationshipsService {

  @Autowired
  private ServiceRelationshipsRepository serviceRelationshipsRepository;

  @Autowired
  private ServiceModelBatchProcessor serviceModelBatchProcessor;

  @Override
  public void processServiceRelationships(String pathToFile) {
    serviceModelBatchProcessor.processServiceRelationships(pathToFile);
  }

  @Override
  public List<ServiceRelationships> getAllServiceRelationships() {
    return serviceRelationshipsRepository.findAll();
  }

  @Override
  public Optional<ServiceRelationships> getParentAndChildServiceRelationship(String parent, String child) {

    return Optional.ofNullable(serviceRelationshipsRepository.findByParentAndChild(parent, child)
        .orElseThrow(() -> new ServiceRelationshipsException(INVALID_PARENT_OR_CHILD)));
  }
}
