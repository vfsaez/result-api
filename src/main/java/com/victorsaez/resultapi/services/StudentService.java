package com.victorsaez.resultapi.services;

import com.victorsaez.resultapi.config.CustomUserDetails;
import com.victorsaez.resultapi.dto.StudentDTO;
import com.victorsaez.resultapi.entities.Student;
import com.victorsaez.resultapi.exceptions.AccessDeniedException;
import com.victorsaez.resultapi.exceptions.AgeRequirementsException;
import com.victorsaez.resultapi.exceptions.StudentNotFoundException;
import com.victorsaez.resultapi.mappers.StudentMapper;
import com.victorsaez.resultapi.repositories.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Service
public class StudentService {

    public StudentRepository repository;

    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LogManager.getLogger(StudentService.class);


    public Page<StudentDTO> findAll(Pageable pageable, UserDetails currentUserDetails) {
        CustomUserDetails customUserDetails = (CustomUserDetails) currentUserDetails;
        Page<Student> students = customUserDetails.isAdmin() ?
                repository.findAll(pageable):
                repository.findAllByProfessorId(customUserDetails.getId(), pageable);
        return students.map(studentMapper::studentToStudentDTO);
    }

    public StudentDTO findById(Long id, UserDetails currentUserDetails) throws StudentNotFoundException {
        CustomUserDetails customCurrentUserDetails = (CustomUserDetails) currentUserDetails;
        return studentMapper.studentToStudentDTO(repository.findById(id).map(student -> {
            if (customCurrentUserDetails.isAdmin() || student.getProfessor().getId().equals(((CustomUserDetails) currentUserDetails).getId())) {
                return student;
            } else {
                throw new AccessDeniedException(id, ((CustomUserDetails) currentUserDetails).getId());
            }
        }).orElseThrow(() -> new StudentNotFoundException(id)));
    }

    public StudentDTO insert(StudentDTO studentDto, UserDetails currentUserDetails) throws AgeRequirementsException {
        CustomUserDetails customCurrentUserDetails = (CustomUserDetails) currentUserDetails;
        Student studentToSave = studentMapper.studentDTOtoStudent(studentDto);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);

        if(studentToSave.getDateOfBirth() == null || studentToSave.getDateOfBirth().after(calendar.getTime())) {
            throw new AgeRequirementsException();
        }

        if (studentDto.getEmail() != null) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<StudentDTO>> violations = validator.validateProperty(studentDto, "email");
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            studentToSave.setEmail(studentDto.getEmail());
        }

        studentToSave.setProfessor(((CustomUserDetails) currentUserDetails).getUser());
        Student savedStudent = repository.save(studentToSave);
        logger.info("user {} Student id {} created", customCurrentUserDetails.getId(), savedStudent.getId());
        return studentMapper.studentToStudentDTO(savedStudent);
    }

    public StudentDTO update(StudentDTO studentDto, UserDetails currentUserDetails) throws AgeRequirementsException {
        CustomUserDetails customCurrentUserDetails = (CustomUserDetails) currentUserDetails;
        Student existingStudent = repository.findById(studentDto.getId())
                .orElseThrow(() -> new StudentNotFoundException(studentDto.getId()));

        existingStudent.setName(studentDto.getName());
        existingStudent.setFamilyName(studentDto.getFamilyName());
        existingStudent.setDateOfBirth(studentDto.getDateOfBirth());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);

        if(existingStudent.getDateOfBirth() == null || existingStudent.getDateOfBirth().after(calendar.getTime())) {
            throw new AgeRequirementsException();
        }

        if (studentDto.getEmail() != null) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<StudentDTO>> violations = validator.validateProperty(studentDto, "email");
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            existingStudent.setEmail(studentDto.getEmail());
        }

        if (!customCurrentUserDetails.isAdmin()
                && !existingStudent.getProfessor().getId().equals(customCurrentUserDetails.getId())) {
            throw new AccessDeniedException(studentDto.getId(), customCurrentUserDetails.getId());
        }

        Student updatedStudent = repository.save(existingStudent);
        logger.info("user {} Student id {} updated", customCurrentUserDetails.getId(), updatedStudent.getId());
        return studentMapper.studentToStudentDTO(updatedStudent);
    }

    public StudentDTO patch(Long id, StudentDTO studentDto, UserDetails currentUserDetails) throws AgeRequirementsException {
        CustomUserDetails customCurrentUserDetails = (CustomUserDetails) currentUserDetails;
        Student existingStudent = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        if (studentDto.getName() != null) {
            existingStudent.setName(studentDto.getName());
        }
        if (studentDto.getFamilyName() != null) {
            existingStudent.setFamilyName(studentDto.getFamilyName());
        }

        if (studentDto.getEmail() != null) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<StudentDTO>> violations = validator.validateProperty(studentDto, "email");
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            existingStudent.setEmail(studentDto.getEmail());
        }

        if (studentDto.getDateOfBirth() != null) {
            existingStudent.setDateOfBirth(studentDto.getDateOfBirth());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);

        if(existingStudent.getDateOfBirth() == null || existingStudent.getDateOfBirth().after(calendar.getTime())) {
            throw new AgeRequirementsException();
        }

        if (!customCurrentUserDetails.isAdmin()
                && !existingStudent.getProfessor().getId().equals(customCurrentUserDetails.getId())) {
            throw new AccessDeniedException(id, customCurrentUserDetails.getId());
        }

        Student updatedStudent = repository.save(existingStudent);
        logger.info("user {} Student id {} patched", customCurrentUserDetails.getId(), updatedStudent.getId());
        return studentMapper.studentToStudentDTO(updatedStudent);
    }

    public void delete(Long id, UserDetails currentUserDetails) {
        CustomUserDetails customCurrentUserDetails = (CustomUserDetails) currentUserDetails;
        StudentDTO studentDto = this.findById(id, currentUserDetails);
        logger.info("user {} Student id {} deleted", customCurrentUserDetails.getId(), studentDto.getId());
        repository.deleteById(id);
    }
}
