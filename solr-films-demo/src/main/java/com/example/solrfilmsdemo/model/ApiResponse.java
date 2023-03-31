package com.example.solrfilmsdemo.model;

import java.util.List;

public record ApiResponse(Integer count, List<Film> films) {
}
