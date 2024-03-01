package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.CourseDTO;
import com.victorsaez.resultapi.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO courseToCourseDTO(Course course);
    Course courseDTOtoCourse(CourseDTO courseDto);
}