package com.craig.scholar.happy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.scheduling.annotation.EnableAsync;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"FreePoly\"")
public class FreePoly {

  @Id
  @SequenceGenerator(name = "POLYID_GENERATOR", sequenceName = "\"FreePoly_poly_id_seq\"", allocationSize = 1)
  @GeneratedValue(generator = "POLYID_GENERATOR", strategy = GenerationType.AUTO)
  @Column(name = "poly_id")
  private BigInteger polyId;

  @Column(name = "poly")
  @JdbcTypeCode(SqlTypes.JAVA_OBJECT)
  private int[][] poly;

  @Column(name = "number_of_blocks")
  private Integer numberOfBlocks;

  @Column(name = "number_of_polys")
  private Integer numberOfPolys;

  @Column(name = "poly_group_id")
  private String polyGroupId;

  @Column(name = "session_id")
  private String sessionId;

  @Column(name = "created_ts", insertable = false, updatable = false)
  private Instant createdTs;
}
