package com.craig.scholar.happy.service.storage;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.repository.FreePolyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FreePolyStorageService {

  @NonNull
  private final FreePolyRepository freePolyRepository;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final CollectionType collectionType = objectMapper.getTypeFactory()
      .constructCollectionType(Collection.class, int[].class);

  @SneakyThrows
  public void saveFreePolys(Collection<int[]> polys, int numberOfBlocks, UUID polysId) {
    log.info("Saving polys with {} blocks. Polys Id: {}", numberOfBlocks, polysId);
    freePolyRepository.save(FreePolyDto.builder()
        .polysId(polysId)
        .numberOfBlocks(numberOfBlocks)
        .poly(objectMapper.writeValueAsString(polys))
        .build());
  }

  public Optional<FreePolyDto> getFreePolys(UUID polysId) {
    log.info("Retrieving for Polys Id: {}", polysId);
    return freePolyRepository.findById(polysId)
        .map(freePolyDto -> {
          try {
            return FreePolyDto.builder()
                .polysId(freePolyDto.getPolysId())
                .numberOfBlocks(freePolyDto.getNumberOfBlocks())
                .polys(objectMapper.readValue(freePolyDto.getPoly(), collectionType))
                .build();
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void deleteFreePolys(UUID polysId) {
    log.info("Delete polys for polys id: {}", polysId);
    freePolyRepository.deleteById(polysId);
  }

  public void deleteAll() {
    freePolyRepository.deleteAll();
  }
}
