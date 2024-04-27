package com.example.SimplestCRUDExample.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Projects")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

}
