-- Course Instances
-- CS101 in 2024, semester 1
INSERT INTO course_instances (course_id, academic_year, semester, additional_info) 
SELECT c.id, 2024, 1, 'Monday and Wednesday, 10:00-11:30 AM' 
FROM courses c WHERE c.course_id = 'CS101';

-- CS201 in 2024, semester 1
INSERT INTO course_instances (course_id, academic_year, semester, additional_info) 
SELECT c.id, 2024, 1, 'Tuesday and Thursday, 2:00-3:30 PM' 
FROM courses c WHERE c.course_id = 'CS201';

-- MATH101 in 2024, semester 1
INSERT INTO course_instances (course_id, academic_year, semester, additional_info) 
SELECT c.id, 2024, 1, 'Monday, Wednesday, Friday 9:00-10:00 AM' 
FROM courses c WHERE c.course_id = 'MATH101';
