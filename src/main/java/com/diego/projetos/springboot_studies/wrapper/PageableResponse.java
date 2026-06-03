package com.diego.projetos.springboot_studies.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class PageableResponse<T> extends PageImpl<T> {
    private boolean first;
    private boolean last;
    private int totalPages;
    private int numberOfElements;

    @JsonCreator(mode = Mode.PROPERTIES)
    public PageableResponse(@JsonProperty("content") List<T> content,
                            @JsonProperty("page") JsonNode page) {
        super(
                content,
                PageRequest.of(
                        page.get("number").asInt(),
                        page.get("size").asInt()
                ),
                page.get("totalElements").asLong()
        );
    }
}
