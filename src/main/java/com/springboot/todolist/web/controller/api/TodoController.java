package com.springboot.todolist.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.todolist.domain.todo.Todo;
import com.springboot.todolist.domain.todo.TodoRepository;
import com.springboot.todolist.service.todo.TodoService;
import com.springboot.todolist.web.dto.CMRespDto;
import com.springboot.todolist.web.dto.todo.CreateTodoReqDto;
import com.springboot.todolist.web.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;
	
	@PostMapping("/todo")
	public ResponseEntity<?> addContent(@RequestBody CreateTodoReqDto createTodoReqDto) {
		
		try {
//			todoService.createTodo(createTodoReqDto);
			
			if(!todoService.createTodo(createTodoReqDto)) {
				throw new RuntimeException("DataBase Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, "Adding todo failed", createTodoReqDto));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", createTodoReqDto));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getTodoList(@RequestParam int page, int contentCount) {
		List<TodoListRespDto> list = null;
		
		try {
			list = todoService.getTodoList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, page + "page list fail to load", list));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success to load", list));
	}
	
	@GetMapping("/list/importance")
	public ResponseEntity<?> getImportanceTodoList(@RequestParam int page, int contentCount) {
		List<TodoListRespDto> list = null;
		
		try {
			list = todoService.getImportanceTodoList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, page + "page list fail to load", list));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success to load", list));
	}
	
	@PutMapping("/complete/todo/{todoCode}")
	public ResponseEntity<?> setCompleteTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.updateTodoComplete(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, "update success to load", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "update success to load", status));
	}
	
	@PutMapping("/importance/todo/{todoCode}")
	public ResponseEntity<?> setImportanceTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.importanceTodoComplete(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new CMRespDto<>(-1, "update success to load", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "update success to load", status));
	}
	
	@PutMapping("/content/{todoContent}")
	public ResponseEntity<?> setContentTodo(@PathVariable int todoCode) {
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "update success to load", null));
	}
}
