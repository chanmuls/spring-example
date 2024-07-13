package com.chanmul.core.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface GenericMapper<T, S> {
  @Named("toTarget")
  T toTarget(S s);

  @Named("toSource")
  S toSource(T t);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTarget(T target, @MappingTarget S source);
}