import unittest

from tailor.utils import charformat

class MyTestCase(unittest.TestCase):

    def is_upper_camel_case_test_upper_camel_case_name(self):
        self.assertTrue(charformat.is_upper_camel_case('HelloWorld'))

    def is_upper_camel_case_test_lower_camel_case_name(self):
        self.assertFalse(charformat.is_upper_camel_case('helloWorld'))

    def is_upper_camel_case_test_blank_name(self):
        self.assertFalse(charformat.is_upper_camel_case(''))

    def is_upper_camel_case_test_snake_case_name(self):
        self.assertFalse(charformat.is_upper_camel_case('Hello_World'))

    def is_upper_camel_case_test_numeric_name(self):
        self.assertFalse(charformat.is_upper_camel_case('1ello_world'))

    def is_upper_camel_case_test_special_character_name(self):
        self.assertFalse(charformat.is_upper_camel_case('!ello_world'))

    def is_lower_camel_case_test_invalid_lower_camel_case(self):
        self.assertFalse(charformat.is_lower_camel_case(''))
        self.assertFalse(charformat.is_lower_camel_case('_notLowerCamelCase'))
        self.assertFalse(charformat.is_lower_camel_case('NotLowerCamelCase'))
        self.assertFalse(charformat.is_lower_camel_case('not_lower_camel_case'))

    def is_lower_camel_case_test_valid_lower_camel_case(self):
        self.assertTrue(charformat.is_lower_camel_case('isLowerCamelCase'))
        self.assertTrue(charformat.is_lower_camel_case('isLow3rCam3lCas3'))

if __name__ == '__main__':
    unittest.main()
