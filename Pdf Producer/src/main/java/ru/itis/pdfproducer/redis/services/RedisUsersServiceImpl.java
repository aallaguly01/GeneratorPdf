package ru.itis.pdfproducer.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.pdfproducer.models.User;
import ru.itis.pdfproducer.redis.models.RedisUser;
import ru.itis.pdfproducer.redis.repositories.JwtBlacklistService;
import ru.itis.pdfproducer.redis.repositories.RedisUsersRepository;
import ru.itis.pdfproducer.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RedisUsersServiceImpl implements RedisUsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtBlacklistService blacklistService;

    @Autowired
    private RedisUsersRepository redisUsersRepository;

    @Override
    public void addTokenToUser(User user, String token) {
        String redisId = user.getRedisId();

        RedisUser redisUser;
        if (redisId == null || redisId.length() == 0) {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(Collections.singletonList(token))
                    .build();
        } else {
            redisUser = redisUsersRepository.findById(redisId).orElseThrow(IllegalArgumentException::new);
            if (redisUser.getTokens() == null) {
                redisUser.setTokens(new ArrayList<>());
            }
            redisUser.getTokens().add(token);
        }
        redisUsersRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        usersRepository.save(user);
    }

    @Override
    public void addAllTokensToBlackList(User user) {
        if (user.getRedisId() != null) {
            RedisUser redisUser = redisUsersRepository.findById(user.getRedisId())
                    .orElseThrow(IllegalArgumentException::new);

            List<String> tokens = redisUser.getTokens();
            for (String token : tokens) {
                blacklistService.add(token);
            }
            redisUser.getTokens().clear();
            redisUsersRepository.save(redisUser);
        }
    }
}
