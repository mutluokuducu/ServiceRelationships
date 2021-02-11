package com.interlinksoftware.processor;

import com.interlinksoftware.repository.entity.ServiceRelationships;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ServiceModelProcessor implements
    ItemProcessor<ServiceRelationships, ServiceRelationships> {

  @Override
  public ServiceRelationships process(ServiceRelationships item) {
    log.info("********** BATCH JOB STARTED " + item);
    return item;
  }
}
