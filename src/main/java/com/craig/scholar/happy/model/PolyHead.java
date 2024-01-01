package com.craig.scholar.happy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"PolyHead\"")
public class PolyHead {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "poly_head_id", insertable = false, updatable = false)
  private UUID polyHeadId;

  @Column(name = "number_of_blocks")
  private Integer numberOfBlocks;

  @Column(name = "number_of_polys")
  private Integer numberOfPolys;

  @Column(name = "session_id")
  private String sessionId;

  @Column(name = "created_ts", insertable = false, updatable = false)
  private Instant createdTs;

  @OneToMany(mappedBy = "polyHead")
  private Set<Poly> polies;

}
