package com.interlinksoftware.controller;

import static com.interlinksoftware.constants.RestApiUrls.CSV_FILE_PATH;
import static com.interlinksoftware.constants.RestApiUrls.GET_LIST;
import static com.interlinksoftware.constants.RestApiUrls.GET_PARENT_CHILD_LIST;
import static com.interlinksoftware.constants.RestApiUrls.READ_CSV_FILE;

import com.interlinksoftware.repository.entity.ServiceRelationships;
import com.interlinksoftware.service.ServiceRelationshipsService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServiceRelationshipsController {

  @Autowired
  private ServiceRelationshipsService serviceRelationshipsService;

  @ApiOperation(
      value = "Service model read csv file",
      nickname = "Service model read csv file",
      response = ResponseEntity.class)
  @PostMapping(
      value = READ_CSV_FILE)
  public ResponseEntity<Void> fasterPayment(
      @RequestParam(defaultValue = CSV_FILE_PATH) String filePath) {

    serviceRelationshipsService.processServiceRelationships(filePath);

    return ResponseEntity.ok().build();
  }

  @ApiOperation(
      value = "Service model get list",
      nickname = "Service model get list",
      response = ResponseEntity.class)
  @GetMapping(value = GET_LIST)
  public ResponseEntity<List<ServiceRelationships>> getAllList() {

    List<ServiceRelationships> list = serviceRelationshipsService.getAllServiceRelationships();
    return ResponseEntity.ok().body(list);
  }

  @ApiOperation(
      value = "Service model get parent child",
      nickname = "Service model get parent child",
      response = ResponseEntity.class)
  @GetMapping(value = GET_PARENT_CHILD_LIST)
  public ResponseEntity<Optional<ServiceRelationships>> getParentAndChildList(
      @PathVariable("parent") @NotBlank String parent,
      @PathVariable("child") @NotBlank String child) {

    Optional<ServiceRelationships> list = serviceRelationshipsService
        .getParentAndChildServiceRelationship(parent, child);

    return ResponseEntity.ok().body(list);
  }
}
