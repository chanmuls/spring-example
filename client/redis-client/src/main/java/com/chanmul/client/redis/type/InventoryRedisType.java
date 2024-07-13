package com.chanmul.client.redis.type;

import com.chanmul.core.exception.ApiException;
import com.chanmul.core.type.EnumType;
import com.chanmul.core.type.ServiceErrorType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InventoryRedisType implements EnumType {
  INVENTORY("재고 보정 대상")
	;

  private final String description;

  @Override
  public String getValue() {
    return this.name();
  }

  public static String getHashKey(Long productId, int shardSize) {
    if (productId == null) {
      throw new ApiException(ServiceErrorType.INVALID_PARAMETER);
    }

		return String.format("%s:%d", INVENTORY.getValue(), Math.toIntExact(productId / shardSize));
  }

  public static String getHashKey(int position) {
		return String.format("%s:%d", INVENTORY.getValue(), position);
  }
}
