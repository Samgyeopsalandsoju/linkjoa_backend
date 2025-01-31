package com.samso.linkjoa.infrastructure.redis;

import com.samso.linkjoa.core.Utility.Encryptor;
import com.samso.linkjoa.domain.Authentication.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public void saveDate(String key, String value, long offset){
        redisTemplate.opsForValue().set(key, value, offset);
    };

    public void saveHashData(String key, Map<String, String> data, long offset){
        redisTemplate.opsForHash().putAll(key, data);
        long timeStamp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(offset);
        redisTemplate.expireAt(key, new Date(timeStamp));
    }

    public String getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

//    public Optional<Authentication> findAuthVerification(String key){
//        String storedData = redisTemplate.opsForValue().get(key);
//        if(StringUtils.hasText(storedData)){
//            String[] value = storedData.split("|");
//            return Optional.of(new Authentication(Encryptor.twoWayDecrypt(value[0])
//                    , Integer.parseInt(Encryptor.twoWayDecrypt(value[1]))));
//        }
//        return Optional.empty();
//    }

    public Optional<Map<Object, Object>> getHashData(String authKey) {
        return Optional.ofNullable(redisTemplate.opsForHash().entries(authKey));

    }
}
