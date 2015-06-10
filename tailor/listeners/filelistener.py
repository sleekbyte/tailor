from tailor.utils import sourcefile


class FileListener:
    # pylint: disable=too-few-public-methods

    def __init__(self, printer, filepath, max_lengths):
        self.__printer = printer
        self.__filepath = filepath
        self.__max_lengths = max_lengths

    def verify(self):
        self.__verify_file_length(self.__max_lengths.max_file_length)
        self.__verify_line_lengths(self.__max_lengths.max_line_length)

    def __verify_file_length(self, max_file_length):
        if sourcefile.file_too_long(self.__filepath, max_file_length):
            self.__printer.error(
                'File is over maximum line limit (' +
                str(sourcefile.num_lines_in_file(self.__filepath)) + '/' +
                str(max_file_length) + ')', loc=(max_file_length + 1, 0))

    def __verify_line_lengths(self, max_line_length):
        for line in sourcefile.lines_too_long(
                self.__filepath, max_line_length):
            self.__printer.error('Line is over maximum character limit (' +
                                 str(line[1]) + '/' + str(max_line_length) +
                                 ')', loc=(line[0], max_line_length + 1))
