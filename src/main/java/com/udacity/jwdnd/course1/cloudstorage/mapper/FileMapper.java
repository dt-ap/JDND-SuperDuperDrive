package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {
  @Select("SELECT * FROM FILES WHERE userid = #{userId}")
  @Results(id = "fileMap", value = {
      @Result(property = "id", column = "fileId", id = true),
      @Result(property = "name", column = "filename"),
      @Result(property = "size", column = "fileSize"),
      @Result(property = "data", column = "filedata")
  })
  List<File> findFilesByUserId(Integer userId);

  @Select("SELECT * FROM FILES WHERE fileid = #{id} AND userid = #{userId}")
  @ResultMap("fileMap")
  Optional<File> findFilebyIdAndUserId(Integer id, Integer userId);

  @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{name}, #{contentType}, #{size}, #{userId}, #{data})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(File file);

  @Delete("DELETE FROM FILES WHERE fileid = #{id} AND userid = #{userId}")
  int delete(File file);

}
