package com.skillsphere.config;

import com.skillsphere.entity.*;
import com.skillsphere.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final CourseModuleRepository moduleRepository;
    private final LearningMaterialRepository materialRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final MaterialProgressRepository progressRepository;
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizOptionRepository optionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CertificateRepository certificateRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, CourseRepository courseRepository, CourseModuleRepository moduleRepository, LearningMaterialRepository materialRepository, EnrollmentRepository enrollmentRepository, MaterialProgressRepository progressRepository, QuizRepository quizRepository, QuizQuestionRepository questionRepository, QuizOptionRepository optionRepository, QuizAttemptRepository quizAttemptRepository, CertificateRepository certificateRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.materialRepository = materialRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.certificateRepository = certificateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (courseRepository.count() > 0) {
            return;
        }

        User adminUser = userRepository.findByEmail("admin@skillsphere.com").orElseGet(() -> 
            userRepository.save(User.builder()
                    .email("admin@skillsphere.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .fullName("System Admin")
                    .role(Role.ADMIN)
                    .active(true)
                    .build())
        );

        Instructor instructor1 = getOrCreateInstructor(
                "instructor@skillsphere.com", "Instructor@123", "Dr. Sarah Jenkins",
                "Senior Computer Science Professor",
                "15+ years experience in Software Engineering, Java, and Distributed Systems.",
                "Java, Web Development, Data Structures", "SkillSphere Academy",
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=300&q=80"
        );

        Instructor instructor2 = getOrCreateInstructor(
                "alex.instructor@skillsphere.com", "Instructor@123", "Prof. Alex Rivera",
                "AI & Data Science Specialist",
                "Ph.D. in Machine Learning and Python Ecosystem Architect.",
                "Python, Machine Learning, React", "AI Research Lab",
                "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=300&q=80"
        );

        Student student1 = getOrCreateStudent(
                "student@skillsphere.com", "Student@123", "John Doe",
                "Passionate Computer Science student eager to learn full-stack development.",
                "B.Tech CSE Undergrad",
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=300&q=80"
        );

        Student student2 = getOrCreateStudent(
                "emma.student@skillsphere.com", "Student@123", "Emma Watson",
                "Software engineering enthusiast focusing on Data Science.",
                "B.Tech IT Undergrad",
                "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=300&q=80"
        );

        List<Course> coursesList = new ArrayList<>();

        coursesList.add(createCourse(
                "Java Programming Masterclass",
                "Learn core Java, Object-Oriented Programming, Multithreading, and Collections framework from scratch.",
                "Programming",
                CourseLevel.BEGINNER,
                "15 Hours",
                0.0,
                true,
                "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=800&q=80",
                "Master OOP concepts; Write clean modular Java code; Build real-world Java applications.",
                instructor1
        ));

        coursesList.add(createCourse(
                "Modern Web Development with HTML, CSS & JS",
                "Comprehensive guide to modern responsive website creation, DOM manipulation, and modern ES6 JavaScript.",
                "Web Development",
                CourseLevel.BEGINNER,
                "20 Hours",
                49.99,
                false,
                "https://images.unsplash.com/photo-1547658719-da2b51169166?auto=format&fit=crop&w=800&q=80",
                "Build responsive layouts; Implement dynamic JavaScript logic; Understand modern CSS Grid & Flexbox.",
                instructor1
        ));

        coursesList.add(createCourse(
                "Python Programming Bootcamp",
                "From basic syntax and data structures to automation scripts and modular Python packages.",
                "Programming",
                CourseLevel.BEGINNER,
                "18 Hours",
                0.0,
                true,
                "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=800&q=80",
                "Master Python syntax & logic; File handling and regex; Object-oriented programming in Python.",
                instructor2
        ));

        coursesList.add(createCourse(
                "Data Structures & Algorithms in Java",
                "Master Arrays, Linked Lists, Trees, Graphs, Sorting, and Dynamic Programming algorithms for technical interviews.",
                "Computer Science",
                CourseLevel.INTERMEDIATE,
                "25 Hours",
                59.99,
                false,
                "https://images.unsplash.com/photo-1516116211223-4c71412030f1?auto=format&fit=crop&w=800&q=80",
                "Analyze time & space complexity; Implement foundational data structures; Solve complex algorithmic problems.",
                instructor1
        ));

        coursesList.add(createCourse(
                "Machine Learning & Data Science Fundamentals",
                "Learn supervised learning, regression, classification, clustering, NumPy, Pandas, and Scikit-Learn.",
                "Data Science",
                CourseLevel.ADVANCED,
                "30 Hours",
                79.99,
                false,
                "https://images.unsplash.com/photo-1555949963-ff9fe0c870eb?auto=format&fit=crop&w=800&q=80",
                "Preprocess complex datasets; Train machine learning models; Evaluate model accuracy.",
                instructor2
        ));

        coursesList.add(createCourse(
                "React Development - The Complete Guide",
                "Build fast modern web applications with React 18, React Router, Hooks, Context API, and state management.",
                "Web Development",
                CourseLevel.INTERMEDIATE,
                "22 Hours",
                0.0,
                true,
                "https://images.unsplash.com/photo-1633356122544-f134324a6cee?auto=format&fit=crop&w=800&q=80",
                "Understand JSX & React Virtual DOM; Manage state with Hooks; Connect React frontend to Spring Boot API.",
                instructor2
        ));

        for (Course course : coursesList) {
            seedCourseContentsAndQuiz(course);
        }

        Course javaCourse = coursesList.get(0);
        Enrollment enrollment1 = Enrollment.builder()
                .student(student1)
                .course(javaCourse)
                .enrolledAt(LocalDateTime.now().minusDays(5))
                .progressPercentage(100)
                .completed(true)
                .completedAt(LocalDateTime.now().minusDays(1))
                .build();
        enrollmentRepository.save(enrollment1);

        List<CourseModule> javaModules = moduleRepository.findByCourseIdOrderByModuleNumberAsc(javaCourse.getId());
        for (CourseModule mod : javaModules) {
            for (LearningMaterial mat : mod.getMaterials()) {
                MaterialProgress mp = MaterialProgress.builder()
                        .enrollment(enrollment1)
                        .material(mat)
                        .completedAt(LocalDateTime.now().minusDays(2))
                        .build();
                progressRepository.save(mp);
            }
        }

        Quiz javaQuiz = quizRepository.findFirstByCourseId(javaCourse.getId()).orElse(null);
        if (javaQuiz != null) {
            QuizAttempt attempt = QuizAttempt.builder()
                    .student(student1)
                    .quiz(javaQuiz)
                    .score(5)
                    .totalPossibleScore(5)
                    .percentage(100.0)
                    .passed(true)
                    .attemptedAt(LocalDateTime.now().minusDays(1))
                    .build();
            quizAttemptRepository.save(attempt);
        }

        Certificate cert = Certificate.builder()
                .certificateNumber("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .student(student1)
                .course(javaCourse)
                .instructor(javaCourse.getInstructor())
                .enrollment(enrollment1)
                .issueDate(LocalDateTime.now().minusDays(1))
                .build();
        certificateRepository.save(cert);

        Course webCourse = coursesList.get(1);
        Enrollment enrollment2 = Enrollment.builder()
                .student(student1)
                .course(webCourse)
                .enrolledAt(LocalDateTime.now().minusDays(3))
                .progressPercentage(50)
                .completed(false)
                .build();
        enrollmentRepository.save(enrollment2);
    }

    private Course createCourse(String title, String description, String category, CourseLevel level,
                                 String duration, Double price, Boolean isFree, String imageUrl,
                                 String learningObjectives, Instructor instructor) {
        Course course = Course.builder()
                .title(title)
                .description(description)
                .category(category)
                .level(level)
                .duration(duration)
                .price(price)
                .isFree(isFree)
                .imageUrl(imageUrl)
                .learningObjectives(learningObjectives)
                .status(CourseStatus.PUBLISHED)
                .instructor(instructor)
                .build();
        return courseRepository.save(course);
    }

    private void seedCourseContentsAndQuiz(Course course) {
        for (int m = 1; m <= 3; m++) {
            CourseModule module = CourseModule.builder()
                    .course(course)
                    .moduleNumber(m)
                    .title("Module " + m + ": Fundamentals of " + course.getTitle() + " - Part " + m)
                    .description("Core concepts, practical demonstrations, and exercises for module " + m)
                    .build();
            moduleRepository.save(module);

            List<LearningMaterial> materials = new ArrayList<>();
            materials.add(LearningMaterial.builder()
                    .module(module)
                    .type(MaterialType.VIDEO)
                    .title("Video Lecture: Introduction to Module " + m)
                    .description("Watch this interactive video explanation of core concepts.")
                    .contentUrlOrText("https://www.youtube.com/embed/dQw4w9WgXcQ")
                    .orderIndex(1)
                    .build());

            materials.add(LearningMaterial.builder()
                    .module(module)
                    .type(MaterialType.TEXT)
                    .title("Reading Notes & Architecture Overview")
                    .description("Comprehensive text guide covering key principles and code patterns.")
                    .contentUrlOrText("### Key Learning Points\n\n- Understand syntax and semantics\n- Apply modular design principles\n- Clean error handling and logging practices.")
                    .orderIndex(2)
                    .build());

            materials.add(LearningMaterial.builder()
                    .module(module)
                    .type(m % 2 == 0 ? MaterialType.PDF : MaterialType.LINK)
                    .title(m % 2 == 0 ? "Downloadable PDF Cheat Sheet" : "Official Documentation Reference")
                    .description("Supplementary reference materials for quick lookups.")
                    .contentUrlOrText(m % 2 == 0 ? "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf" : "https://docs.oracle.com/en/java/")
                    .orderIndex(3)
                    .build());

            materialRepository.saveAll(materials);
        }

        Quiz quiz = Quiz.builder()
                .course(course)
                .title(course.getTitle() + " Assessment Quiz")
                .description("Test your knowledge across all modules. Score 50% or higher to pass.")
                .passingScorePercentage(50)
                .timeLimitMinutes(20)
                .build();
        quizRepository.save(quiz);

        for (int q = 1; q <= 5; q++) {
            QuizQuestion question = QuizQuestion.builder()
                    .quiz(quiz)
                    .questionText("Question " + q + ": What is a primary concept covered in " + course.getTitle() + " (Topic " + q + ")?")
                    .marks(1)
                    .orderIndex(q)
                    .build();
            questionRepository.save(question);

            List<QuizOption> options = new ArrayList<>();
            options.add(QuizOption.builder().question(question).optionText("Incorrect option A").isCorrect(false).build());
            options.add(QuizOption.builder().question(question).optionText("Correct primary option for " + course.getTitle()).isCorrect(true).build());
            options.add(QuizOption.builder().question(question).optionText("Incorrect option C").isCorrect(false).build());
            options.add(QuizOption.builder().question(question).optionText("Incorrect option D").isCorrect(false).build());

            optionRepository.saveAll(options);
        }
    }

    private Instructor getOrCreateInstructor(String email, String password, String name, String title, String bio, String expertise, String org, String avatar) {
        User user = userRepository.findByEmail(email).orElseGet(() -> 
            userRepository.save(User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .fullName(name)
                    .role(Role.INSTRUCTOR)
                    .active(true)
                    .build())
        );
        return instructorRepository.findByUserId(user.getId()).orElseGet(() -> 
            instructorRepository.save(Instructor.builder()
                    .user(user)
                    .title(title)
                    .bio(bio)
                    .expertise(expertise)
                    .organization(org)
                    .avatarUrl(avatar)
                    .build())
        );
    }

    private Student getOrCreateStudent(String email, String password, String name, String bio, String qual, String avatar) {
        User user = userRepository.findByEmail(email).orElseGet(() -> 
            userRepository.save(User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .fullName(name)
                    .role(Role.STUDENT)
                    .active(true)
                    .build())
        );
        return studentRepository.findByUserId(user.getId()).orElseGet(() -> 
            studentRepository.save(Student.builder()
                    .user(user)
                    .bio(bio)
                    .qualification(qual)
                    .avatarUrl(avatar)
                    .build())
        );
    }
}
