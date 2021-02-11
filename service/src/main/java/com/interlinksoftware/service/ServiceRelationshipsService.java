package com.interlinksoftware.service;

import com.interlinksoftware.repository.entity.ServiceRelationships;
import java.util.List;
import java.util.Optional;

public interface ServiceRelationshipsService {

  List<ServiceRelationships> getAllServiceRelationships();

  void processServiceRelationships(String pathToFile);

  Optional<ServiceRelationships> getParentAndChildServiceRelationship(String parent, String child);

}
