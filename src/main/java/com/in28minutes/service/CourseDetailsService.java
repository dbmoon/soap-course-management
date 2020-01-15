package com.in28minutes.service;

import com.in28minutes.soap.webservices.soapcoursemanagement.bean.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDetailsService {

    private static List<Course> courses = new ArrayList<>();

    public enum Status{
        FAILURE, SUCCESS;
    }

    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        courses.add(course1);

        Course course2 = new Course(2, "Maven", "5 Steps");
        courses.add(course2);

        Course course3 = new Course(3, "Angular", "8 Steps");
        courses.add(course3);

        Course course4 = new Course(4, "Amazon Web Services", "22 Steps");
        courses.add(course4);
    }

    public Course findbyId(int id){
        for(Course course: courses){
            if(id == course.getId())
                return course;
        }
        return null;
    }

    public List<Course> findAll(){
        return courses;
    }

    public Status deleteById(int id){
        Iterator<Course> iterator = courses.iterator();
        while(iterator.hasNext()){
            Course course = iterator.next();
            if(course.getId() == id){
                courses.remove(course);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

    //public Course addN
}
