package com.craig.scholar.happy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"Poly\"")
public class Poly {

  @Id
  @Column(name = "poly_id", insertable = false, updatable = false)
  private UUID polyId;

  @JoinColumn(name = "poly_head_id", nullable = false)
  private PolyHead polyHead;

  @Column(name = "poly")
  @JdbcTypeCode(SqlTypes.JAVA_OBJECT)
  private int[][] poly;
}
