package com.example.solrfilmsdemo;

import com.example.solrfilmsdemo.model.ApiRequest;
import com.example.solrfilmsdemo.model.ApiResponse;
import com.example.solrfilmsdemo.model.Film;
import com.example.solrfilmsdemo.model.Query;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController("api/")
public class ApiControllers {

    private final SolrService solrService;

    @PostMapping("reindex/")
    void reindexFilms() {
        solrService.reindex();
    }

    @GetMapping("film/{id}")
    ApiResponse getFilmsById(@PathVariable String id) {
        return solrService.getFilmsById(id);
    }

    @PostMapping("film")
    ApiResponse getFilms(@RequestBody ApiRequest request) {
        return solrService.getFilms(request);
    }

    @PostMapping("genres")
    List<Map.Entry<String, Long>> getGenres(@RequestBody Query request) {
        return solrService.getGenres(request);
    }

    @PostMapping("film/add")
    void addFilm(@RequestBody Film film) throws SolrServerException, IOException {
        solrService.addFilm(film);
    }
}
