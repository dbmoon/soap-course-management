package com.in28minutes.soap.webservices.soapcoursemanagement.soap;

import com.in28minutes.courses.*;
import com.in28minutes.service.CourseDetailsService;
import com.in28minutes.soap.webservices.soapcoursemanagement.bean.Course;
import com.in28minutes.soap.webservices.soapcoursemanagement.soap.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    //http://in28minutes.com/courses - namespace
    //GetCourseDetailsRequest - operation

    @Autowired
    private CourseDetailsService service;

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest
            (@RequestPayload GetCourseDetailsRequest request){

        Course course = service.findbyId(request.getId());
        if(course == null)
            throw new CourseNotFoundException("Invalid Course Id: " + request.getId());
        GetCourseDetailsResponse response = mapCourseDetails(course);
        return response;
    }

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest
            (@RequestPayload GetAllCourseDetailsRequest request){

        List<Course> courses = service.findAll();
        GetAllCourseDetailsResponse response = mapAllCourseDetails(courses);
        return response;
    }

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest
            (@RequestPayload DeleteCourseDetailsRequest request){

        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(service.deleteById(request.getId())));
        return response;
    }

    private CourseDetails mapCourse(Course course){
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setDescription(course.getDescription());
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        return courseDetails;
    }

    private GetCourseDetailsResponse mapCourseDetails(Course course){
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses){
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for(Course course: courses){
            CourseDetails mapCourse = mapCourse(course);
            response.getCourseDetails().add(mapCourse);
        }
        return response;
    }

    private Status mapStatus(CourseDetailsService.Status status){
        if(status == CourseDetailsService.Status.FAILURE)
            return Status.FAILURE;
        return Status.SUCCESS;
    }
}
