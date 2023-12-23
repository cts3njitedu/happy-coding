package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceImpl;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poly")
@Slf4j
@RequiredArgsConstructor
public class FreePolyController {

    @NonNull
    private final EnumerateFreePolyServiceImpl enumerateFreePolyService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/enumerate/{n}")
    public @ResponseBody FreePolyominoesResponse enumerate(@PathVariable Integer n) {
        log.info("Enumerate polyominoes with {} blocks", n);
        Collection<int[][]> matrices = EnumerateFreePolyUtil.getMatrices(
            enumerateFreePolyService.enumerate(n));
        return FreePolyominoesResponse.builder()
            .numberOfBlocks(n)
            .polys(matrices)
            .numberOfPolys(matrices.size())
            .build();
    }

}
