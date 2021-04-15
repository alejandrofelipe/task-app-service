package com.example.task.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.task.jwt.JwtUtil;
import com.example.task.model.Employee;
import com.example.task.model.Task;
import com.example.task.service.EmployeeService;
import com.example.task.service.PriorityService;
import com.example.task.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskResourceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwt;

    @BeforeAll
    public void before() {
        Employee employee = employeeService.list().stream().findFirst().orElse(new Employee());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                employee.getUsername(), employee.getPassword());
        authenticationManager.authenticate(authToken);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(employee.getUsername());
        this.jwt = jwtUtil.generateToken(userDetails);
    }

    @Test
    public void addTask_ReturnNewTask_AndStatusCreated() throws Exception {
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 2);
        Task newTask = new Task().toBuilder()
                .title("test task")
                .description("test description")
                .priorityId(1L)
                .employeeId(1L)
                .deadline(deadline.getTime())
                .build();

        mvc.perform(
                post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask))
                        .header("Authorization", "Bearer " + this.jwt)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(newTask.getTitle()))
                .andExpect(jsonPath("$.description").value(newTask.getDescription()));
    }

    @Test
    public void addTaskWithMissingProperty_ReturnError_AndStatusBadRquest() throws Exception {
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 2);
        Task newTask = new Task().toBuilder()
//                .name("Task test")
                .description("test description")
                .priorityId(1L)
                .employeeId(1L)
                .deadline(deadline.getTime())
                .build();

        mvc.perform(
                post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask))
                        .header("Authorization", "Bearer " + this.jwt)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTaskWithAnExistingId_ReturnError_AndStatusBadRquest() throws Exception {
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 2);
        Task task = taskService.save(new Task().toBuilder()
                .title("test task")
                .description("test description")
                .priorityId(1L)
                .employeeId(1L)
                .deadline(deadline.getTime())
                .build());

        mvc.perform(
                post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .header("Authorization", "Bearer " + this.jwt)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTask_ReturnUpdatedTask_AndStatusOk() throws Exception {
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 2);
        Task task = taskService.save(new Task().toBuilder()
                .title("test task")
                .description("test description")
                .priorityId(1L)
                .employeeId(1L)
                .deadline(deadline.getTime())
                .build());

        String newDescription = "new description";
        task.setDescription(newDescription);

        mvc.perform(
                put("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .header("Authorization", "Bearer " + this.jwt)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.description").value(newDescription));
    }

    @Test
    public void updateTaskWithoutId_ReturnError_AndStatusBadRequest() throws Exception {
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 2);
        Task newTask = new Task().toBuilder()
                .title("Task test")
                .description("test description")
                .priorityId(1L)
                .employeeId(1L)
                .deadline(deadline.getTime())
                .build();

        mvc.perform(
                put("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask))
                        .header("Authorization", "Bearer " + this.jwt)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }


}
