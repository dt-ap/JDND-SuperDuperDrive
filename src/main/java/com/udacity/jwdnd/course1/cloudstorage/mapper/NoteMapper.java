package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
  @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
  List<Note> findNotesByUserId(int userId);

  @Insert("INSERT INTO NOTES (title, description, userid) VALUES (#{title}, #{description}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Note note);

  @Update("UPDATE NOTES SET title = #{title}, description = #{description} WHERE noteid = #{id} AND userid = #{userId}")
  int update(Note note);

  @Delete("DELETE FROM NOTES WHERE noteid = #{id} AND userid = #{userId}")
  int delete(Note note);
}
