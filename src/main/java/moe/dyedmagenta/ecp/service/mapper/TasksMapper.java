package moe.dyedmagenta.ecp.service.mapper;

import moe.dyedmagenta.ecp.domain.*;
import moe.dyedmagenta.ecp.service.dto.TasksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tasks and its DTO TasksDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TasksMapper extends EntityMapper<TasksDTO, Tasks> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    TasksDTO toDto(Tasks tasks); 

    @Mapping(source = "userId", target = "user")
    Tasks toEntity(TasksDTO tasksDTO);

    default Tasks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tasks tasks = new Tasks();
        tasks.setId(id);
        return tasks;
    }
}
