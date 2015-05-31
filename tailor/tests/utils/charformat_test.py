import unittest

from tailor.utils import charformat

class MyTestCase(unittest.TestCase):

    def is_upper_camel_case_test_lower_camel_case_name(self):
        self.assertFalse(charformat.is_upper_camel_case('helloWorld'))


if __name__ == '__main__':
    unittest.main()
