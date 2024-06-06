# Open the source file in read mode and the destination file in write mode
with open('Attendances-1.sql', 'r') as src_file, open('modified_Attendances-1.sql', 'w') as dest_file:
    # Read the source file line by line
    for line in src_file:
        # Check if the line ends with a semicolon and remove it
        if line.endswith(';\n'):
            line = line[:-2] + ' '
        # Append 'ON CONFLICT (date) DO NOTHING;' at the end of each line
        new_line = line + 'ON CONFLICT (student_id, date) DO NOTHING;\n'
        # Write the modified line to the destination file
        dest_file.write(new_line)