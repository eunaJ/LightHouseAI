package com.mju.lighthouseai.global.naversearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    @Autowired
    public NaverSearchController(NaverSearchService naverSearchService) {
        this.naverSearchService = naverSearchService;
    }

    @GetMapping("/search")
    public List<NaverSearchItem> naver(
        @RequestParam String query
    ) {
        System.out.println(naverSearchService.searchLocal(query));
        return naverSearchService.searchLocal(query);
    }
}
