package com.craig.scholar.happy.service.storage;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.Poly;
import com.craig.scholar.happy.model.PolyHead;
import com.craig.scholar.happy.repository.PolyHeadRepository;
import com.craig.scholar.happy.repository.PolyRepository;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreePolySqlService {

  @NonNull
  private final PolyHeadRepository polyHeadRepository;

  @NonNull
  private final PolyRepository polyRepository;

  public PolyHead savePolys(FreePolyDto freePolyDto) {
    PolyHead polyHead = polyHeadRepository.saveAndFlush(PolyHead.builder()
        .numberOfPolys(freePolyDto.getNumberOfPolys())
        .numberOfBlocks(freePolyDto.getNumberOfBlocks())
        .sessionId(freePolyDto.getSessionId())
        .build());
    log.info("Successfully saved poly head with poly head id {}", polyHead.getPolyHeadId());
    freePolyDto.getFreePolysOld().stream()
        .map(poly -> Poly.builder()
            .poly(EnumerateFreePolyUtil.getMatrix(poly))
            .polyHead(polyHead)
            .build())
        .forEach(polyRepository::saveAndFlush);
    log.info("Successfully saved polies for poly head id: {}", polyHead.getPolyHeadId());
    return polyHead;
  }

  public FreePolyDto findByPolyHeadId(String polyHeadId, Integer polysPerPage, Integer page) {
    List<Poly> polies = polyRepository.findAllByPolyHeadPolyHeadId(UUID.fromString(polyHeadId),
        Limit.of(polysPerPage), ScrollPosition.offset((long) (page - 1) * polysPerPage));
    if (Objects.nonNull(polies) && !polies.isEmpty()) {
      PolyHead polyHead = polies.getFirst().getPolyHead();
      return FreePolyDto.builder()
          .polysId(polyHead.getPolyHeadId())
          .numberOfPolys(polyHead.getNumberOfPolys())
          .numberOfBlocks(polyHead.getNumberOfBlocks())
          .sessionId(polyHead.getSessionId())
          .freePolys(polies.stream()
              .map(Poly::getPoly)
              .collect(Collectors.toList()))
          .build();
    }
    throw new IllegalArgumentException(
        String.format("No entries found for %s", polyHeadId));
  }

  public void deletePolysBySessionId(String sessionId) {
    polyHeadRepository.deleteBySessionId(sessionId);
  }
}
