package com.example.solrfilmsdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Film {
    @Field
    private String id;
    public void setId(String id) {
        this.id = id.replaceFirst("/.*/", "");
    }
    @Field
    private String name;
    @JsonProperty("directed_by")
    @Field("directed_by")
    private List<String> directedBy;
    @Field
    private List<String> genre;
    @JsonProperty("initial_release_date")
    @Field("initial_release_date")
    private Date initialReleaseDate;
    @JsonIgnore
    @Field("full_text")
    private String fullText;

    public void calcFullText() {
        this.fullText = String.format("%s %s %s", name, listToFullTextPart(directedBy), listToFullTextPart(genre));
    }

    private String listToFullTextPart(List<String> l) {
        return l == null || l.isEmpty() ? "" : String.join(" ", l);
    }
}
