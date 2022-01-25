package ru.itis.pdfproducer.redis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    public void add(String token) {
        blacklistRepository.save(token);
    }

    @Override
    public boolean exists(String token) {
        return blacklistRepository.exists(token);
    }
}
