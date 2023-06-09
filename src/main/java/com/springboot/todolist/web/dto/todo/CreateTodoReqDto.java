package com.springboot.todolist.web.dto.todo;

import com.springboot.todolist.domain.todo.Todo;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateTodoReqDto {
	private boolean importance;
	private String todo;
	
	public Todo toEntity() {
		return Todo.builder()
				.importance_flag(importance ? 1 : 0)
				.todo_content(todo)
				.build();
	}
}
