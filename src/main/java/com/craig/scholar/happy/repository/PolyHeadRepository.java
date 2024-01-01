package com.craig.scholar.happy.repository;

import com.craig.scholar.happy.model.PolyHead;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolyHeadRepository extends JpaRepository<PolyHead, UUID> {

}
