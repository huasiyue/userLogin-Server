package com.example.userloginserver.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password; // 在实际应用中，建议对密码进行加密存储
}
