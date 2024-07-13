package com.chanmul.client.redis.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryRedisDto {
  private Long productId;
  private Long productVariantId;
  @Setter
  private int quantityOfChange;
  @Setter
  private int quantity;

  public String getFieldKey() {
    return String.format("%d:%d", productId, productVariantId);
  }
}
