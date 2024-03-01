package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.CourseDTO;
import com.victorsaez.resultapi.dto.ResultDTO;
import com.victorsaez.resultapi.dto.StudentDTO;
import com.victorsaez.resultapi.entities.Course;
import com.victorsaez.resultapi.entities.Result;
import com.victorsaez.resultapi.entities.Student;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-01T14:35:45-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
public class ResultMapperImpl implements ResultMapper {

    @Override
    public ResultDTO resultToResultDTO(Result result) {
        if ( result == null ) {
            return null;
        }

        ResultDTO resultDTO = new ResultDTO();

        resultDTO.setId( result.getId() );
        resultDTO.setGrade( result.getGrade() );
        resultDTO.setStudent( studentToStudentDTO( result.getStudent() ) );
        resultDTO.setCourse( courseToCourseDTO( result.getCourse() ) );

        return resultDTO;
    }

    @Override
    public Result resultDTOtoResult(ResultDTO resultDto) {
        if ( resultDto == null ) {
            return null;
        }

        Result result = new Result();

        result.setId( resultDto.getId() );
        result.setGrade( resultDto.getGrade() );
        result.setStudent( studentDTOToStudent( resultDto.getStudent() ) );
        result.setCourse( courseDTOToCourse( resultDto.getCourse() ) );

        return result;
    }

    protected StudentDTO studentToStudentDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId( student.getId() );
        studentDTO.setName( student.getName() );
        studentDTO.setFamilyName( student.getFamilyName() );
        studentDTO.setEmail( student.getEmail() );
        studentDTO.setDateOfBirth( student.getDateOfBirth() );

        return studentDTO;
    }

    protected CourseDTO courseToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId( course.getId() );
        courseDTO.setName( course.getName() );

        return courseDTO;
    }

    protected Student studentDTOToStudent(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDTO.getId() );
        student.setName( studentDTO.getName() );
        student.setFamilyName( studentDTO.getFamilyName() );
        student.setEmail( studentDTO.getEmail() );
        student.setDateOfBirth( studentDTO.getDateOfBirth() );

        return student;
    }

    protected Course courseDTOToCourse(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( courseDTO.getId() );
        course.setName( courseDTO.getName() );

        return course;
    }
}
