from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import isUpperCamelCase


class MainListener(SwiftListener):

    def enterClassName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Class names should be in UpperCamelCase')

    def enterEnumName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Enum names should be in UpperCamelCase')

    def enterEnumCaseName(self, ctx):
        pass

    def enterStructName(self, ctx):
        pass

    @staticmethod
    def __verify_upper_camel_case(ctx, err_msg):
        construct_name = ctx.getText()
        if not isUpperCamelCase(construct_name):
            print('Line', str(ctx.start.line) + ':', err_msg)
