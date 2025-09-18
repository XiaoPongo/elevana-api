package com.elevana_api.service.impl;

import com.elevana_api.repository.ActivityRepository;
import com.elevana_api.repository.ClassroomRepository;
import com.elevana_api.repository.MaterialRepository;
import com.elevana_api.repository.StudentRepository;
import com.elevana_api.service.DoomsdayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoomsdayServiceImpl implements DoomsdayService {

    private static final Logger logger = LoggerFactory.getLogger(DoomsdayServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    @Transactional
    public void nuke() {
        // This is a dangerous operation that will wipe the database.
        // It is included to match the missing file references.
        logger.warn("!!! ENGAGING NUKE PROTOCOL - WIPING ALL DATA !!!");
        
        // Order is important to respect foreign key constraints
        materialRepository.deleteAll();
        activityRepository.deleteAll();
        // Clear join table manually before deleting classrooms and students
        studentRepository.findAll().forEach(student -> student.getClassrooms().clear());
        studentRepository.flush();
        classroomRepository.deleteAll();
        studentRepository.deleteAll();
        
        logger.warn("!!! NUKE PROTOCOL COMPLETE - ALL DATA HAS BEEN DELETED !!!");
    }
}
