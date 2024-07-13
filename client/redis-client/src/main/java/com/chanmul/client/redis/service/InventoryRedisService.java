package com.chanmul.client.redis.service;
/**
 * http://redisgate.kr/redis/command/hset.php
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import com.chanmul.client.redis.model.InventoryRedisDto;
import com.chanmul.client.redis.type.InventoryRedisType;
import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryRedisService {
	private final RedisTemplate<String, String> redisTemplate;
	@Value("${spring.data.redis.shard.size}")
	private int shardSize;

	public void save(InventoryRedisDto dto) {
		String hashKey = InventoryRedisType.getHashKey(dto.getProductId(), shardSize);
		redisTemplate.opsForHash()
				.put(hashKey, dto.getFieldKey(), String.valueOf(dto.getQuantity()));
	}

	public boolean saveIfAbsent(InventoryRedisDto dto) {
		String hashKey = InventoryRedisType.getHashKey(dto.getProductId(), shardSize);
		return Boolean.TRUE.equals(redisTemplate.opsForHash()
				.putIfAbsent(hashKey, dto.getFieldKey(), String.valueOf(dto.getQuantity())));
	}

	public void saveAll(List<InventoryRedisDto> dtos) {
		if (dtos.isEmpty()) {
			return;
		}

		Map<String, Map<String, String>> hash = new HashMap<>();
		Lists.partition(new ArrayList<>(dtos), 1000)
			.forEach(chunk -> {
				chunk.forEach(dto -> {
					String hashKey = InventoryRedisType.getHashKey(dto.getProductId(), shardSize);
					if (!hash.containsKey(hashKey)) {
						hash.put(hashKey, new HashMap<>());
					}

					hash.get(hashKey).put(dto.getFieldKey(), String.valueOf(dto.getQuantity()));
				});

				hash.forEach((key, map) -> {
					redisTemplate.opsForHash().putAll(key, map);
					map.clear();
				});

				hash.clear();
			});
	}

	public InventoryRedisDto findByField(InventoryRedisDto dto) {
		if (Objects.isNull(dto)) {
			return null;
		}

		String hashKey = InventoryRedisType.getHashKey(dto.getProductId(), shardSize);
		Object data = redisTemplate.opsForHash()
				.get(hashKey, dto.getFieldKey());

		if (Objects.isNull(data)) {
			return null;
		}

		return InventoryRedisDto.builder()
				.productId(dto.getProductId())
				.productVariantId(dto.getProductVariantId())
				.quantity(Integer.parseInt(String.valueOf(data)))
				.build();
	}

	public InventoryRedisDto increment(InventoryRedisDto dto) {
		if (Objects.isNull(dto)) {
			return null;
		}

		String hashKey = InventoryRedisType.getHashKey(dto.getProductId(), shardSize);
		int quantity = redisTemplate.opsForHash()
				.increment(hashKey, dto.getFieldKey(), dto.getQuantityOfChange())
				.intValue();

		return InventoryRedisDto.builder()
				.productId(dto.getProductId())
				.productVariantId(dto.getProductVariantId())
				.quantityOfChange(dto.getQuantityOfChange())
				.quantity(quantity)
				.build();
	}

	public Map<String, Integer> scanByPosition(int position, String fieldPattern) {
		return redisTemplate.execute((RedisCallback<Map<String, Integer>>)connection -> {
      Map<String, Integer> hscan = new HashMap<>();
      Cursor<Map.Entry<byte[], byte[]>> cursor = null;
      try {
        cursor = connection.hashCommands()
            .hScan(InventoryRedisType.getHashKey(position).getBytes(),
                ScanOptions.scanOptions().match(fieldPattern + "*")
                    .count(1000)
                    .build());

        while (cursor.hasNext()) {
					Map.Entry<byte[], byte[]> next = cursor.next();
					hscan.put(new String(next.getKey()), Integer.parseInt(new String(next.getValue())));
        }
      } catch (Exception e) {
        log.error("hscan error {}", e.getMessage(), e);

				if (Objects.nonNull(cursor) && !cursor.isClosed()) {
					cursor.close();
				}

				throw e;
      } finally {
        if (Objects.nonNull(cursor) && !cursor.isClosed()) {
          cursor.close();
        }
      }

      return hscan;
    });
	}

	public int delete(List<InventoryRedisDto> dtos) {
		if (dtos.isEmpty()) {
			return 0;
		}

		return dtos.stream()
				.collect(Collectors.groupingBy(dto ->
								InventoryRedisType.getHashKey(dto.getProductId(), shardSize),
								Collectors.mapping(InventoryRedisDto::getFieldKey, Collectors.toSet())))
				.entrySet().stream()
				.mapToInt(entry -> redisTemplate.opsForHash()
						.delete(entry.getKey(), entry.getValue().toArray())
						.intValue())
				.sum();
	}
}
