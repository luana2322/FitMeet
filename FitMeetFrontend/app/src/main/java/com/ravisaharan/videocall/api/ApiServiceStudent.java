package com.ravisaharan.videocall.api;

import com.ravisaharan.videocall.dto.StudentDto;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.model.StudentNotification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceStudent {
    // CREATE
    @POST("coach")
    Call<Student> createStudent(@Body Student student);

    // READ ALL
    @GET("/getallcoach")
    Call<List<Student>> getStudents();

    @GET("/getstudentbyId")
    Call<Student> getStudentById(@Query("studentId") Long studentId);

    @GET("/getlistnotificationstudent")
    Call<List<StudentNotification>> getListStudentNotification(@Query("student_id") Long student_id);

    // UPDATE
    @PUT("coach/{id}")
    Call<Student> updateStudent(@Path("id") int id, @Body Student student);

    // DELETE
    @DELETE("coach/{id}")
    Call<Void> deleteStudent(@Path("id") int id);

    @POST("/studentlogin")
    Call<Student> loginStudent(@Body StudentDto student);

    @GET("/getliststudentchat")
    Call<List<Student>> getListStudentChat(@Query("coach_id") Long coachId);
}
