package com.springboot.todolist.web.dto.todo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class TodoListRespDto {
	private int todoCode;
	private String todo;
	private boolean todoComplete;
	private boolean importance;
	
	private int totalCount;
	private int incompleteCount;
	
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
