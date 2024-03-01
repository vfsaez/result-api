package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.StudentDTO;
import com.victorsaez.resultapi.entities.Student;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-01T14:35:45-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO studentToStudentDTO(Student student) {
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

    @Override
    public Student studentDTOtoStudent(StudentDTO studentDto) {
        if ( studentDto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDto.getId() );
        student.setName( studentDto.getName() );
        student.setFamilyName( studentDto.getFamilyName() );
        student.setEmail( studentDto.getEmail() );
        student.setDateOfBirth( studentDto.getDateOfBirth() );

        return student;
    }
}
