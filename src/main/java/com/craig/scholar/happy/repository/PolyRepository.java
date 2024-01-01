package com.craig.scholar.happy.repository;

import com.craig.scholar.happy.model.Poly;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolyRepository extends JpaRepository<Poly, UUID> {

  List<Poly> findAllByPolyHeadPolyHeadId(UUID polyHeadId, Limit limit,
      OffsetScrollPosition offsetScrollPosition);
}
