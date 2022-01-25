package ru.itis.pdfproducer.redis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("user")
public class RedisUser {
    @Id
    private String id;
    private List<String> tokens;
    private Long userId;
}
