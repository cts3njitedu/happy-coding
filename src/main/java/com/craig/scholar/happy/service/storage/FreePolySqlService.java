package com.craig.scholar.happy.service.storage;

import com.craig.scholar.happy.model.FreePoly;
import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.repository.FreePolyRepository;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreePolySqlService {

  @NonNull
  private final FreePolyRepository freePolyRepository;

  public void savePolys(FreePolyDto freePolyDto) {
    freePolyDto.getFreePolysOld()
        .stream()
        .map(EnumerateFreePolyUtil::getMatrix)
        .map(m -> FreePoly.builder()
            .numberOfPolys(freePolyDto.getNumberOfPolys())
            .numberOfBlocks(freePolyDto.getNumberOfBlocks())
            .poly(m)
            .sessionId(freePolyDto.getSessionId())
            .polyGroupId(freePolyDto.getPolysId().toString())
            .build())
        .forEach(freePolyRepository::saveAndFlush);
  }

  public FreePolyDto findByPolyGroupId(String polyGroupId) {
    List<FreePoly> freePolies = freePolyRepository.findByPolyGroupId(polyGroupId);
    if (Objects.nonNull(freePolies) && !freePolies.isEmpty()) {
      FreePoly freePoly = freePolies.getFirst();
      return FreePolyDto.builder()
          .freePolys(freePolies.stream()
              .map(FreePoly::getPoly)
              .collect(Collectors.toList()))
          .polysId(UUID.fromString(freePoly.getPolyGroupId()))
          .sessionId(freePoly.getSessionId())
          .numberOfBlocks(freePoly.getNumberOfBlocks())
          .numberOfPolys(freePoly.getNumberOfPolys())
          .build();
    }
    return null;
  }
}
