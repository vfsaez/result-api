package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.StudentDTO;
import com.victorsaez.resultapi.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO studentToStudentDTO(Student student);
    Student studentDTOtoStudent(StudentDTO studentDto);
}