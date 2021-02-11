package com.interlinksoftware.repository.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "service_relationships")
public class ServiceRelationships {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "parent", nullable = false, length = 50)
  private String parent;

  @Column(name = "child", nullable = false, length = 50)
  private String child;

  @Column(name = "label", nullable = false, length = 50)
  private String label;

  @Column(name = "impact", nullable = false)
  private float impact;

}
