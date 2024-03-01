package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.CourseDTO;
import com.victorsaez.resultapi.entities.Course;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-01T14:35:45-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDTO courseToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId( course.getId() );
        courseDTO.setName( course.getName() );

        return courseDTO;
    }

    @Override
    public Course courseDTOtoCourse(CourseDTO courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( courseDto.getId() );
        course.setName( courseDto.getName() );

        return course;
    }
}
