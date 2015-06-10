import builtins
import unittest
from unittest.mock import Mock, mock_open, patch

from tailor.utils import sourcefile


class SourceFileLengthTestCase(unittest.TestCase):

    def setUp(self):
        self.patcher = patch.object(
            builtins, 'open', mock_open(read_data='\n' * 100))
        self.patcher.start()
        self.addCleanup(self.patcher.stop)

    @patch.object(builtins, 'open', mock_open(read_data=''))
    def num_lines_in_file_test_empty_file(self):
        self.assertEqual(0, sourcefile.num_lines_in_file('0Lines'))

    def num_lines_in_file_test_normal_file(self):
        self.assertEqual(100, sourcefile.num_lines_in_file('100Lines'))

    def file_too_long_test_negative_max_length(self):
        self.assertFalse(sourcefile.file_too_long('100Lines', max_length=-10))

    def file_too_long_test_zero_max_length(self):
        self.assertFalse(sourcefile.file_too_long('100Lines', max_length=0))

    def file_too_long_test_file_over_limit(self):
        self.assertTrue(sourcefile.file_too_long('100Lines', max_length=50))

    def file_too_long_test_file_equal_to_limit(self):
        self.assertFalse(sourcefile.file_too_long('100Lines', max_length=100))

    def file_too_long_test_file_under_limit(self):
        self.assertFalse(sourcefile.file_too_long('100Lines', max_length=101))


class SourceFileConstructTestCase(unittest.TestCase):

    def construct_too_long_test_max_length_zero_or_less(self):
        ctx = Mock()
        ctx.start.line = 1
        ctx.stop.line = 10
        self.assertFalse(sourcefile.construct_too_long(ctx, -10))
        self.assertFalse(sourcefile.construct_too_long(ctx, 0))

    def construct_too_long_test_construct_under_limit(self):
        ctx = Mock()
        ctx.start.line = 1
        ctx.stop.line = 10
        self.assertFalse(sourcefile.construct_too_long(ctx, 15))

    def construct_too_long_test_construct_equal_to_limit(self):
        ctx = Mock()
        ctx.start.line = 1
        ctx.stop.line = 10
        self.assertFalse(sourcefile.construct_too_long(ctx, 9))

    def construct_too_long_test_construct_over_limit(self):
        ctx = Mock()
        ctx.start.line = 1
        ctx.stop.line = 10
        self.assertTrue(sourcefile.construct_too_long(ctx, 5))


class SourceFileLineLengthTestCase(unittest.TestCase):

    def setUp(self):
        mopen = mock_open(read_data=''.join(['a' * 100 + '\n'] * 5))
        # __iter__ and __next__ must be provided for iteration over mock file
        mopen.return_value.__iter__ = lambda self: self
        mopen.return_value.__next__ = lambda self: self.readline()
        self.patcher = patch.object(builtins, 'open', mopen)
        self.patcher.start()
        self.addCleanup(self.patcher.stop)

    def lines_too_long_test_negative_max_length(self):
        self.assertListEqual(
            [], sourcefile.lines_too_long('100Characters', -10))

    def lines_too_long_test_zero_max_length(self):
        self.assertListEqual([], sourcefile.lines_too_long('100Characters', 0))

    def lines_too_long_test_zero_lines_over_limit(self):
        self.assertListEqual(
            [(0, 100), (1, 100), (2, 100), (3, 100), (4, 100)],
            sourcefile.lines_too_long('100Characters', 50))

if __name__ == '__main__':
    unittest.main()
