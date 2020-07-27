package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
  @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")

  List<Credential> findCredentialsByUserId(int userId);

  @Insert("INSERT INTO CREDENTIALS (url, username, passkey, password, userid) VALUES (#{url}, #{username}, #{passKey}, #{password}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Credential credential);

  @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, passkey = #{passKey}, password = #{password} WHERE credentialid = #{id} AND userid = #{userId}")
  int update(Credential credential);

  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id} AND userid = #{userId}")
  int delete(Credential credential);
}
