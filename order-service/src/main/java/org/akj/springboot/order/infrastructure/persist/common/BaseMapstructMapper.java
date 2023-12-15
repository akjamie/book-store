package org.akj.springboot.order.infrastructure.persist.common;

import org.akj.springboot.common.domain.BaseEntity;
import org.akj.springboot.order.domain.Base;

public interface BaseMapstructMapper {
	Base baseEntityToBaseDo(BaseEntity entity);

	BaseEntity baseDoToBaseEntity(Base base);

}
