package com.craig.scholar.happy.service.storage;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.Poly;
import com.craig.scholar.happy.model.PolyHead;
import com.craig.scholar.happy.repository.FreePolyRepository;
import com.craig.scholar.happy.repository.PolyHeadRepository;
import com.craig.scholar.happy.repository.PolyRepository;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreePolySqlService {

  @NonNull
  private final FreePolyRepository freePolyRepository;

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
    freePolyDto.getFreePolysOld().stream()
        .map(poly -> Poly.builder()
            .poly(EnumerateFreePolyUtil.getMatrix(poly))
            .polyHead(polyHead)
            .build())
        .forEach(polyRepository::saveAndFlush);
    return polyHead;
  }

  public FreePolyDto findByPolyGroupId(String polyGroupId) {
    List<Poly> polies = polyRepository.findAllByPolyHeadPolyHeadId(UUID.fromString(polyGroupId),
        Limit.of(5), ScrollPosition.offset(5));
    return polyHeadRepository.findById(UUID.fromString(polyGroupId))
        .map(polyHead -> FreePolyDto.builder()
            .polysId(polyHead.getPolyHeadId())
            .numberOfPolys(polyHead.getNumberOfPolys())
            .numberOfBlocks(polyHead.getNumberOfBlocks())
            .sessionId(polyHead.getSessionId())
            .freePolys(polyHead.getPolies().stream()
                .map(Poly::getPoly)
                .collect(Collectors.toList()))
            .build())
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("No entries found for %s", polyGroupId)));
  }
}
