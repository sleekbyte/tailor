import unittest
from unittest.mock import Mock

from tailor.output.printer import Printer
from io import StringIO
import sys


class MyTestCase(unittest.TestCase):

    def setUp(self):
        self.printer = Printer('abc.swift')

        self.old_stdout = sys.stdout
        sys.stdout = self.mystdout = StringIO()     # redirect stdout

    def printer_test_warn(self):
        ctx = Mock()
        ctx.start.line = 10
        ctx.start.column = 42
        self.printer.warn(ctx, 'this is a warning')
        self.assertRegex(self.mystdout.getvalue(), r'^.+abc\.swift:10:42: warning: this is a warning')

    def printer_test_error(self):
        ctx = Mock()
        ctx.start.line = 20
        ctx.start.column = 36
        self.printer.error(ctx, 'this is an error')
        self.assertRegex(self.mystdout.getvalue(),r'^.+abc\.swift:20:36: error: this is an error')

    def tearDown(self):
        sys.stdout = self.old_stdout        # restore stdout

if __name__ == '__main__':
    unittest.main()
