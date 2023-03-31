package com.example.solrfilmsdemo.model;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public record Query(String text, List<String> genre) {

    private List<String> joinQueryParts(String fieldName, String src) {
        return joinQueryParts(fieldName, List.of(src.split(" ")));
    }

    private List<String> joinQueryParts(String fieldName, List<String> src) {
        return src.stream().map(s -> String.format(fieldName + ":%s", s)).toList();
    }

    public String toSolrQuery() {
        var queries = new ArrayList<String>();
        if (!Strings.isEmpty(text)) {
            queries.addAll(joinQueryParts("full_text", text));
        } else {
            queries.add("full_text:*");
        }
        if (genre != null) {
            queries.addAll(joinQueryParts("genre", genre));
        }
        return String.join(" && ", queries);
    }
}
