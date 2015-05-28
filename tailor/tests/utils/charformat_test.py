import unittest

from tailor.utils import charformat

class MyTestCase(unittest.TestCase):

    def testIsUpperCamelCase(self):
        self.assertFalse(charformat.isUpperCamelCase('helloWorld'))


if __name__ == '__main__':
    unittest.main()
