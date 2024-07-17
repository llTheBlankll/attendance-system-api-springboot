## Generating Mock Attendance for Testing and Development
- Populate the Students table of your attendance_system
- Run the attendance-generator.py
- Use PostGreSQL \copy
```
\copy attendance_system (student_id, status, date, time, time_out) FROM './attendance.csv' DELIMITER ',' CSV HEADER;
```