package com.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String image;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
