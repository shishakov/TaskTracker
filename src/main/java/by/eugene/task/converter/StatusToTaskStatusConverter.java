package by.eugene.task.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import by.eugene.task.model.TaskStatus;
import by.eugene.task.service.TaskStatusService;

/**
 * A converter class used in views to map id's to actual taskStatus objects.
 */
@Component
public class StatusToTaskStatusConverter implements Converter<Object, TaskStatus>{

    static final Logger logger = LoggerFactory.getLogger(StatusToTaskStatusConverter.class);

    @Autowired
    TaskStatusService taskStatusService;

    /**
     * Gets TaskStatus by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public TaskStatus convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        TaskStatus status= taskStatusService.findById(id);
        logger.info("Status : {}",status);
        return status;
    }

}