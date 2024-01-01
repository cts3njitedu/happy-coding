package com.craig.scholar.happy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "poly_id", insertable = false, updatable = false)
  private UUID polyId;

  @ManyToOne
  @JoinColumn(name = "poly_head_id", referencedColumnName = "poly_head_id")
  private PolyHead polyHead;

  @Column(name = "poly")
  @JdbcTypeCode(SqlTypes.JAVA_OBJECT)
  private int[][] poly;
}
