import unittest

from tailor.utils import charformat

class MyTestCase(unittest.TestCase):

    def testIsUpperCamelCase(self):
        self.assertFalse(charformat.isUpperCamelCase('helloWorld'))

    def is_lower_camel_case_test_invalid_input(self):
        self.assertFalse(charformat.is_lower_camel_case('not%lower%camel%case'))
        self.assertFalse(charformat.is_lower_camel_case('NotLowerCamelCase'))
        self.assertFalse(charformat.is_lower_camel_case('not_lower_camel_case'))

    def is_lower_camel_case_test_valid_input(self):
        self.assertTrue(charformat.is_lower_camel_case('isLowerCamelCase'))

if __name__ == '__main__':
    unittest.main()
