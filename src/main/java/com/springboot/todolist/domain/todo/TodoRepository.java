package com.springboot.todolist.domain.todo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoRepository {
	public int save(Todo todo) throws Exception;
	
	public List<Todo> getTodoList(Map<String, Object> map) throws Exception;
	
//	public List<Todo> getImportanceTodoListOfIndex(Map<String, Object> map) throws Exception;
	
	public int updateTodoComplete(int todo_code) throws Exception;
	public int importanceTodoComplete(int todo_code) throws Exception;
	
	public int updateTodoByTodoCode(Todo todo) throws Exception;
	
	public int remove(int todo_code) throws Exception;
}
