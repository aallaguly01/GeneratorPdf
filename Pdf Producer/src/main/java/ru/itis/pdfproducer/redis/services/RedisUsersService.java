package ru.itis.pdfproducer.redis.services;

import ru.itis.pdfproducer.models.User;

public interface RedisUsersService {
    void addTokenToUser(User user, String token);

    void addAllTokensToBlackList(User user);
}
