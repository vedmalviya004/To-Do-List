package com.ved.controller;

import com.ved.entities.TodoEntity;
import com.ved.repo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping({"","/","home"})
    public String showTodoPage(Model model){
      model.addAttribute("todos", todoRepository.findAll());
      return "index";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title){
        TodoEntity todoEntity = TodoEntity.builder()
                .title(title)
                .completed(false)
                .build();
        todoRepository.save(todoEntity);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateTodo(@PathVariable long id){
        TodoEntity exitingTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found "+id));
        exitingTodo.setCompleted(true);
        todoRepository.save(exitingTodo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable long id){
        TodoEntity exitingTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found "+id));
        todoRepository.delete(exitingTodo);
        return "redirect:/";
    }

}
