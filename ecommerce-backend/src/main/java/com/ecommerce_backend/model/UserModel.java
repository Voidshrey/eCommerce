package com.ecommerce_backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
        private Long id;
        private String username;
        private String email;
        private LocalDateTime createdAt;
}
