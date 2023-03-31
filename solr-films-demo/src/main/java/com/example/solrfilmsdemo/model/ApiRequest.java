package com.example.solrfilmsdemo.model;

public record ApiRequest(Integer page, Integer rows, Query query) implements Pageable {
}
