package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.EnumerateFreePolyominoes;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polyominoes")
@Slf4j
@RequiredArgsConstructor
public class FreePolyminoesController {

    @NonNull
    private final EnumerateFreePolyominoes enumerateFreePolyominoes;

    @GetMapping("/enumerate/{n}")
    public @ResponseBody FreePolyominoesResponse enumerate(@PathVariable Integer n) {
        log.info("Enumerate polyominoes with {} blocks", n);
        return FreePolyominoesResponse.builder()
            .polys(enumerateFreePolyominoes.enumerateFreePolyominoesV5(n))
                .build();
    }

}
