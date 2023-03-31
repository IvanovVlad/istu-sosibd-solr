package com.example.solrfilmsdemo.model;

public interface Pageable {
    Integer page();

    Integer rows();

    default Integer getOffset() {
        return (page() - 1) * rows();
    }
}
