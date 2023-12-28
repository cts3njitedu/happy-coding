package com.craig.scholar.happy.repository;

import com.craig.scholar.happy.model.FreePolyDto;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePolyRepository extends CrudRepository<FreePolyDto, UUID> {

}
