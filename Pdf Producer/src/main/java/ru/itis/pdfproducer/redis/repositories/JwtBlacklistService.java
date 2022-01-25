package ru.itis.pdfproducer.redis.repositories;

public interface JwtBlacklistService {
    void add(String token);

    boolean exists(String token);
}
