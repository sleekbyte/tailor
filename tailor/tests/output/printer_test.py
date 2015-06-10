import sys
import unittest

from io import StringIO
from unittest.mock import Mock

from tailor.output.printer import Printer
from tailor.types.location import Location


class MyTestCase(unittest.TestCase):

    def setUp(self):
        self.printer = Printer('abc.swift')

        self.old_stdout = sys.stdout
        sys.stdout = self.mystdout = StringIO()     # redirect stdout

    def tearDown(self):
        sys.stdout = self.old_stdout    # restore stdout

    def printer_test_warn_ctx(self):
        ctx = Mock()
        ctx.start.line = 10
        ctx.start.column = 42
        self.printer.warn('this is a warning', ctx)
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:10:42: warning: this is a warning')

    def printer_test_error_ctx(self):
        ctx = Mock()
        ctx.start.line = 20
        ctx.start.column = 36
        self.printer.error('this is an error', ctx)
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:20:36: error: this is an error')

    def printer_test_warn_loc(self):
        self.printer.warn('this is a warning', loc=Location(10, 42))
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:10:42: warning: this is a warning')

    def printer_test_error_loc(self):
        self.printer.error('this is an error', loc=Location(20, 36))
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:20:36: error: this is an error')

    def printer_test_warn_no_line_or_column(self):
        self.printer.warn('this is a warning')
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:1:1: warning: this is a warning')

    def printer_test_error_no_line_or_column(self):
        self.printer.error('this is an error')
        self.assertRegex(self.mystdout.getvalue(),
                         r'^.+abc\.swift:1:1: error: this is an error')


if __name__ == '__main__':
    unittest.main()
