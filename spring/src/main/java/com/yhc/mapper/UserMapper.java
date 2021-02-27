package com.Kinghao.mapper;

import com.Kinghao.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * mapper
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * @param username
     * @return
     */
    @Select(value = "select u.email, u.password from Users as u where u.email=#{email}")
    @Results
            ({@Result(property = "email",column = "email"),
              @Result(property = "password",column = "password")})
    User findUserByEmail(@Param("email") String username);

    /**
     * Register  add one user record
     * @param user
     * @return
     */
    @Insert("insert into Users values(NULL,#{email},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(User user);

    /**
     * Login
     * @param user
     * @return
     */
    @Select("select * from Users as u where u.email = #{email}")
    User login(User user);


}
