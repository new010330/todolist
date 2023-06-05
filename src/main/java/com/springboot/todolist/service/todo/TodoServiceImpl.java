package com.springboot.todolist.service.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.springboot.todolist.domain.todo.Todo;
import com.springboot.todolist.domain.todo.TodoRepository;
import com.springboot.todolist.web.dto.todo.CreateTodoReqDto;
import com.springboot.todolist.web.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

	private final TodoRepository todoRepository;
	
	@Override
	public boolean createTodo(CreateTodoReqDto createTodoReqDto) throws Exception {
		Todo todoEntity = createTodoReqDto.toEntity();
		
		return todoRepository.save(todoEntity) > 0;
		
	}

	@Override
	public List<TodoListRespDto> getTodoList(int page, int contentCount) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", (page - 1) * contentCount);
		map.put("count", contentCount);
		
		List<Todo> todolist = todoRepository.getTodoListofIndex(map); // (페이지 - 1) * 들고올 갯수
		List<TodoListRespDto> todoListRespDtos = new ArrayList<TodoListRespDto>();
		todolist.forEach(todo -> {
			todoListRespDtos.add(todo.toListDto());
		});
		
		return todoListRespDtos;
	}

	@Override
	public List<TodoListRespDto> getImportanceTodoList(int page, int contentCount) throws Exception {
		
		List<Todo> todoList = todoRepository.getImportanceTodoListOfIndex(createGetTodoListMap(page, contentCount));
		
		return createTodoListRespDtos(todoList);
	}
	
	private Map<String, Object> createGetTodoListMap(int page, int contentCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", (page - 1) * contentCount);
		map.put("count", contentCount); // (페이지 - 1) * 들고올 갯수
		return map;
	}
	
	private List<TodoListRespDto> createTodoListRespDtos(List<Todo> todoList) {
		List<TodoListRespDto> todoListRespDtos = new ArrayList<TodoListRespDto>();
		todoList.forEach(todo -> {
			todoListRespDtos.add(todo.toListDto());
		});
		
		return todoListRespDtos;
	}

	@Override
	public boolean updateTodoComplete(int todoCode) throws Exception {
		
		
		return todoRepository.updateTodoComplete(todoCode) > 0;
	}

	@Override
	public boolean importanceTodoComplete(int todoCode) throws Exception {
		
		return todoRepository.importanceTodoComplete(todoCode) > 0;
	}

}
