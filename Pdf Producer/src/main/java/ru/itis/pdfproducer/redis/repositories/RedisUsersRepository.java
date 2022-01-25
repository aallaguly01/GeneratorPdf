package ru.itis.pdfproducer.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.pdfproducer.redis.models.RedisUser;

public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {
}

