package edu.uade.cookingrecipes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uade.cookingrecipes.entity.embeddable.Practice;
import edu.uade.cookingrecipes.entity.embeddable.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "description", length = 500)
    private String description;

    @Embedded
    @Column(name = "schedule", nullable = false)
    private Schedule schedule;

    @Column(name = "duration", nullable = false)
    private int duration; //numero de clases que dura el curso

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants; //cupos de la clase

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "teacher_name", length = 100, nullable = false)
    private String teacherName;

    @Column(name = "modality", nullable = false)
    private String modality;

    @Column(name = "objectives", length = 1000, nullable = false)
    private String objectives;

    @Column(name = "subjects", length = 1000, nullable = false)
    private String subjects;

    @ElementCollection
    @CollectionTable(name = "course_practices", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "practices")
    private List<Practice> practicesList;

    @Column(name = "tools", length = 1000, nullable = false)
    private String tools;

    @ElementCollection
    @CollectionTable(name = "course_media", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "media_url")
    private List<String> mediaUrl;

    @ElementCollection
    @CollectionTable(name = "course_students", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "student_id")
    private List<Long> students;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
