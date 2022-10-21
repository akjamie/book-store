package org.akj.springboot.user.mapper;

import org.akj.springboot.user.domain.entity.User;
import org.akj.springboot.user.vo.UserRegistrationRequest;
import org.akj.springboot.user.vo.UserRegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface UserMapstructMapper {
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "aliasName", target = "aliasName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "authType", target = "authType")
    User convert(UserRegistrationRequest request);

    UserRegistrationResponse convert(User user);
}
