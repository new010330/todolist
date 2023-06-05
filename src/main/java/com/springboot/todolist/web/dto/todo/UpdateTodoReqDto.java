package com.springboot.todolist.web.dto.todo;

import com.springboot.todolist.domain.todo.Todo;

import lombok.Data;

@Data
public class UpdateTodoReqDto {
	private String todoContent;
	
	public Todo toEntity() {
		return Todo.builder()
				.todo_content(todoContent)
				.build();
	}
}
