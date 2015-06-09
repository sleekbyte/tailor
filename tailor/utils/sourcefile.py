def num_lines_in_file(filepath):
    with open(filepath) as file:
        return len(file.readlines())


def file_too_long(filepath, max_lines):
    return 0 < max_lines < num_lines_in_file(filepath)
