package com.interlinksoftware.util;

import com.interlinksoftware.repository.entity.ServiceRelationships;

public class ObjectFactory {

  public static final String PARENT_ID = "StressTest";
  public static final String CHILD_ID = "G";
  public static final String LABEL = "G";
  public static final float IMPACT = 25;


  public static ServiceRelationships buildServiceModel() {
    return ServiceRelationships.builder()
        .parent(PARENT_ID)
        .child(CHILD_ID)
        .label(LABEL)
        .impact(IMPACT)
        .build();
  }
}
