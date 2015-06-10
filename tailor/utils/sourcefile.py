def num_lines_in_file(filepath):
    with open(filepath) as file:
        return len(file.readlines())


def file_too_long(filepath, max_length):
    return 0 < max_length < num_lines_in_file(filepath)


def construct_too_long(ctx, max_length):
    return 0 < max_length < (ctx.stop.line - ctx.start.line)
