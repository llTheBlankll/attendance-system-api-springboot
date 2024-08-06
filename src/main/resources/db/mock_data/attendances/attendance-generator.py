import datetime
import random
import psycopg2
import numpy


# Enum
class Status:
    LATE = "LATE"
    ON_TIME = "ON_TIME"
    ABSENT = "ABSENT"

student_list: list[int] = []

def connect():
    """ Connect to the PostgreSQL database server """
    try:
        # connecting to the PostgreSQL server
        with psycopg2.connect(database="attendance_system_api",
                              user="nytri",
                              password="Asharia2100",
                              host="localhost",
                              port=5432) as conn:
            if conn is None:
                print("Something happened!")
                exit(1)
            else:
                print('Connected to the PostgreSQL server.')
                return conn
    except (psycopg2.DatabaseError, Exception) as error:
        print(error)


def between_time(start: str, end: str) -> str:
    start_time = datetime.datetime.strptime(start, "%H:%M")
    end_time = datetime.datetime.strptime(end, "%H:%M")
    time_diff = end_time - start_time
    new_time = start_time + datetime.timedelta(minutes=random.randint(0, int(time_diff.total_seconds() // 60)))
    return new_time.strftime("%H:%M")

def main():
    current_date = datetime.date(2020, 1, 1)
    # Get the days of the current_date minus the specified date
    to_date = current_date - datetime.date(2025, 1, 1)
    print(to_date.days)

    script_file = open("attendance.csv", "w")
    count = 0
    loop_count = abs(to_date.days)
    # Create CSV File
    script_file.write("student_id, status, date, time, time_out\n")

    # Loop for n days
    # O(n * m) time complexity
    for date in range(loop_count):
        for student in student_list:
            status: str = numpy.random.choice([Status.LATE, Status.ON_TIME, Status.ABSENT], p=[0.3, 0.65, 0.05])
            if status == Status.ABSENT:
                new_statement: str = f"{student},{status},{current_date},,"
            elif status == Status.ON_TIME:
                new_statement = f"{student},{status},{current_date.strftime('%Y-%m-%d')},{between_time('6:00', '7:00')},{between_time('14:00', '16:00')}"
            elif status == Status.LATE:
                new_statement = f"{student},{status},{current_date.strftime('%Y-%m-%d')},{between_time('7:00', '9:00')},{between_time('15:00', '17:00')}"
            else:
                new_statement = f"{student},{status},{current_date.strftime('%Y-%m-%d')},{between_time('9:00', '10:00')} {between_time('17:00', '19:00')}"
            script_file.write(new_statement + "\n")
        current_date += datetime.timedelta(days=1)
        count += 1
        print("Current Date: " + str(current_date))
        print(f"Day {count}/{abs(loop_count)} has been generated successfully!")
    print("Attendance has been generated successfully!")
    script_file.close()

if __name__ == '__main__':
    # Get the list of students first.
    conn = connect()
    with conn.cursor() as cursor:
        cursor.execute("select lrn from students;")
        student_list = [student[0] for student in cursor.fetchall()]
    conn.close()
    main()