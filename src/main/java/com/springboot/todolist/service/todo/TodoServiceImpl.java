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
import com.springboot.todolist.web.dto.todo.UpdateTodoReqDto;

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
	public List<TodoListRespDto> getTodoList(String type, int page, int contentCount) throws Exception {
		createGetTodoListMap(type, page, contentCount);
		
		List<Todo> todolist = todoRepository.getTodoList(createGetTodoListMap(type, page, contentCount)); // (페이지 - 1) * 들고올 갯수
		
		
		return createTodoListRespDtos(todolist);
	}

//	@Override
//	public List<TodoListRespDto> getImportanceTodoList(int page, int contentCount) throws Exception {
//		
//		List<Todo> todoList = todoRepository.getImportanceTodoListOfIndex(createGetTodoListMap(page, contentCount));
//		
//		return createTodoListRespDtos(todoList);
//	}
	
	private Map<String, Object> createGetTodoListMap(String type, int page, int contentCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("index", (page - 1) * contentCount); // (페이지 - 1) * 들고올 갯수
		map.put("count", contentCount);
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

	@Override
	public boolean updateTodo(UpdateTodoReqDto updateTodoReqDto) throws Exception {
		
		return todoRepository.updateTodoByTodoCode(updateTodoReqDto.toEntity()) > 0;
	}

	@Override
	public boolean removeTodo(int todoCode) throws Exception {
		
		return todoRepository.remove(todoCode) > 0;
	}

}
