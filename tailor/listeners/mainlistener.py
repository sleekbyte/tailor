from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import isUpperCamelCase, is_lower_camel_case

class MainListener(SwiftListener):

    def enterClassName(self, ctx):
        className = ctx.getText()
        if not isUpperCamelCase(className):
            print('Line', str(ctx.start.line) + ':', 'Class names should be in UpperCamelCase')

    def enterFunctionName(self, ctx):
        self.__verify_lower_camel_case(ctx, 'Function names should be in lowerCamelCase')

    def enterVariableName(self, ctx):
        self.__verify_lower_camel_case(ctx, 'Variable names should be in lowerCamelCase')

    def __verify_lower_camel_case(ctx, error_msg):
        name = ctx.getText()
        if not is_lower_camel_case(name):
            print('Line', str(ctx.start.line) + ':', error_msg)