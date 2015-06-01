from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import is_upper_camel_case


class MainListener(SwiftListener):

    def enterClassName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Class names should be in UpperCamelCase')

    def enterEnumName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Enum names should be in UpperCamelCase')

    def enterEnumCaseName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Enum case names should be in UpperCamelCase')

    def enterStructName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Struct names should be in UpperCamelCase')

    def enterProtocolName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Protocol names should be in UpperCamelCase')

    @staticmethod
    def __verify_upper_camel_case(ctx, err_msg):
        construct_name = ctx.getText()
        if not is_upper_camel_case(construct_name):
            print('Line', str(ctx.start.line) + ':', err_msg)
