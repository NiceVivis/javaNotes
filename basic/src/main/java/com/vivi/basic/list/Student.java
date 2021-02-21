package com.vivi.basic.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String id;

    private String grade;

    private Integer score;

    private Integer sortNum;

    private User user;

    private List<List<Student>> student;

    private String userId;
}
