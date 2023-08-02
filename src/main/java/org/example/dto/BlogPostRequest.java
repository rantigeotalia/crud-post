package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostRequest implements Serializable {
        private Long id;
        private String title;
        private String body;
        private String author;
        private Date createdDate;
        private Date modifiedDate;
}
