package com.interlinksoftware.repository;

import com.interlinksoftware.repository.entity.ServiceRelationships;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRelationshipsRepository extends JpaRepository<ServiceRelationships, Long> {

  Optional<ServiceRelationships> findByParentAndChild(String parent, String child);
}
