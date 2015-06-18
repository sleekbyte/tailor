from tailor.types.location import Location
from tailor.utils.sourcefile import file_too_long, lines_too_long, \
    num_lines_in_file


class FileListener:
    # pylint: disable=too-few-public-methods

    def __init__(self, printer, filepath, max_lengths):
        self.__printer = printer
        self.__filepath = filepath
        self.__max_lengths = max_lengths

    def verify(self):
        self.__verify_file_length(self.__max_lengths.max_file_length)
        self.__verify_line_lengths(self.__max_lengths.max_line_length)

    def __verify_file_length(self, max_lines):
        if file_too_long(self.__filepath, max_lines):
            self.__printer.error('File is over maximum line limit (' +
                                 str(num_lines_in_file(self.__filepath)) +
                                 '/' + str(max_lines) + ')',
                                 # Mark error on first character of next line
                                 # TODO: Use printer method without column
                                 loc=Location(max_lines + 1, 1))

    def __verify_line_lengths(self, max_line_length):
        for line in lines_too_long(self.__filepath, max_line_length):
            self.__printer.error('Line is over maximum character limit (' +
                                 str(line.column) + '/' +
                                 str(max_line_length) + ')',
                                 # Mark error on first character beyond limit
                                 loc=Location(line.line, max_line_length + 1))
