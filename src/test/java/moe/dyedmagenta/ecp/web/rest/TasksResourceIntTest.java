package moe.dyedmagenta.ecp.web.rest;

import moe.dyedmagenta.ecp.EwidencjaCzasuPracyApp;

import moe.dyedmagenta.ecp.domain.Tasks;
import moe.dyedmagenta.ecp.repository.TasksRepository;
import moe.dyedmagenta.ecp.service.TasksService;
import moe.dyedmagenta.ecp.service.dto.TasksDTO;
import moe.dyedmagenta.ecp.service.mapper.TasksMapper;
import moe.dyedmagenta.ecp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TasksResource REST controller.
 *
 * @see TasksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EwidencjaCzasuPracyApp.class)
public class TasksResourceIntTest {

    private static final String DEFAULT_NAME_OF_TASK = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_TASK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTasksMockMvc;

    private Tasks tasks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TasksResource tasksResource = new TasksResource(tasksService);
        this.restTasksMockMvc = MockMvcBuilders.standaloneSetup(tasksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasks createEntity(EntityManager em) {
        Tasks tasks = new Tasks()
            .nameOfTask(DEFAULT_NAME_OF_TASK)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return tasks;
    }

    @Before
    public void initTest() {
        tasks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasks() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate + 1);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getNameOfTask()).isEqualTo(DEFAULT_NAME_OF_TASK);
        assertThat(testTasks.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTasks.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTasksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks with an existing ID
        tasks.setId(1L);
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get all the tasksList
        restTasksMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfTask").value(hasItem(DEFAULT_NAME_OF_TASK.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    public void getTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tasks.getId().intValue()))
            .andExpect(jsonPath("$.nameOfTask").value(DEFAULT_NAME_OF_TASK.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTasks() throws Exception {
        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Update the tasks
        Tasks updatedTasks = tasksRepository.findOne(tasks.getId());
        updatedTasks
            .nameOfTask(UPDATED_NAME_OF_TASK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        TasksDTO tasksDTO = tasksMapper.toDto(updatedTasks);

        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isOk());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getNameOfTask()).isEqualTo(UPDATED_NAME_OF_TASK);
        assertThat(testTasks.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTasks.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTasks() throws Exception {
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);
        int databaseSizeBeforeDelete = tasksRepository.findAll().size();

        // Get the tasks
        restTasksMockMvc.perform(delete("/api/tasks/{id}", tasks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tasks.class);
        Tasks tasks1 = new Tasks();
        tasks1.setId(1L);
        Tasks tasks2 = new Tasks();
        tasks2.setId(tasks1.getId());
        assertThat(tasks1).isEqualTo(tasks2);
        tasks2.setId(2L);
        assertThat(tasks1).isNotEqualTo(tasks2);
        tasks1.setId(null);
        assertThat(tasks1).isNotEqualTo(tasks2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasksDTO.class);
        TasksDTO tasksDTO1 = new TasksDTO();
        tasksDTO1.setId(1L);
        TasksDTO tasksDTO2 = new TasksDTO();
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO2.setId(tasksDTO1.getId());
        assertThat(tasksDTO1).isEqualTo(tasksDTO2);
        tasksDTO2.setId(2L);
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO1.setId(null);
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tasksMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tasksMapper.fromId(null)).isNull();
    }
}
