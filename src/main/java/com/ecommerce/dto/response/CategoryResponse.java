package com.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponse implements Serializable {

    private static final long  serialVersionUID = 1L;

    private Long id;
    private String categoryName;
    private String description;
    private String image;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
