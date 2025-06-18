-- Sample data for initial database setup

-- Courses
INSERT INTO courses (course_id, title, description) VALUES ('CS101', 'Introduction to Computer Science', 'Foundational concepts of computer science including algorithms, data structures, and problem solving');
INSERT INTO courses (course_id, title, description) VALUES ('CS201', 'Data Structures', 'Study of fundamental data structures and their applications');
INSERT INTO courses (course_id, title, description) VALUES ('CS301', 'Algorithms', 'Design and analysis of algorithms, complexity theory');
INSERT INTO courses (course_id, title, description) VALUES ('MATH101', 'Calculus I', 'Introduction to differential and integral calculus');
INSERT INTO courses (course_id, title, description) VALUES ('MATH201', 'Linear Algebra', 'Study of vector spaces, linear transformations, matrices and determinants');

-- Prerequisites
-- CS201 requires CS101
INSERT INTO course_prerequisites (course_id, prerequisite_id) 
SELECT c.id, p.id FROM courses c, courses p 
WHERE c.course_id = 'CS201' AND p.course_id = 'CS101';

-- CS301 requires CS201
INSERT INTO course_prerequisites (course_id, prerequisite_id) 
SELECT c.id, p.id FROM courses c, courses p 
WHERE c.course_id = 'CS301' AND p.course_id = 'CS201';
