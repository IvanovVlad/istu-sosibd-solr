package com.example.solrfilmsdemo;

import com.example.solrfilmsdemo.model.ApiRequest;
import com.example.solrfilmsdemo.model.ApiResponse;
import com.example.solrfilmsdemo.model.Film;
import com.example.solrfilmsdemo.model.Query;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SolrService {

    private final SolrClient solrClient;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public void reindex() {
        try {
            solrClient.deleteByQuery("*:*");
            var resource = resourceLoader.getResource("classpath:films.json");
            var films = objectMapper.readValue(resource.getFile(), new TypeReference<List<Film>>() {
            });
            films.parallelStream().forEach(Film::calcFullText);
            solrClient.addBeans(films);
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse getFilmsById(String id) {
        var query = new SolrQuery("id:" + id);
        try {
            var response = solrClient.query(query);
            return new ApiResponse((int) response.getResults().getNumFound(), response.getBeans(Film.class));
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse getFilms(ApiRequest request) {
        var query = new SolrQuery(request.query().toSolrQuery());
        query.setStart(request.getOffset());
        query.setRows(request.rows());
        try {
            var response = solrClient.query(query);
            return new ApiResponse((int) response.getResults().getNumFound(), response.getBeans(Film.class));
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map.Entry<String, Long>> getGenres(Query request) {
        var query = new SolrQuery(request.toSolrQuery());
        query.setRows(0);
        query.setFacet(true);
        query.addFacetField("genre");
        query.setFacetMinCount(1);
        try {
            var response = solrClient.query(query);
            return response.getFacetFields()
                    .get(0)
                    .getValues()
                    .stream()
                    .map(ff -> Map.entry(ff.getName(), ff.getCount()))
                    .toList();
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFilm(Film film) throws SolrServerException, IOException {
            film.calcFullText();
            solrClient.addBean(film);
    }
}
