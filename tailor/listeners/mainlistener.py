from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import is_upper_camel_case, is_lower_camel_case

class MainListener(SwiftListener):

    def __init__(self, printer):
        self.__printer = printer

    def enterClassName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Class names should be in UpperCamelCase')

    def enterFunctionName(self, ctx):
        self.__verify_lower_camel_case(ctx, 'Function names should be in lowerCamelCase')

    def enterVariableName(self, ctx):
        self.__verify_lower_camel_case(ctx, 'Variable names should be in lowerCamelCase')

    def enterEnumName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Enum names should be in UpperCamelCase')

    def enterEnumCaseName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Enum case names should be in UpperCamelCase')

    def enterStructName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Struct names should be in UpperCamelCase')

    def enterProtocolName(self, ctx):
        self.__verify_upper_camel_case(ctx, 'Protocol names should be in UpperCamelCase')

    def __verify_upper_camel_case(self, ctx, err_msg):
        construct_name = ctx.getText()
        if not is_upper_camel_case(construct_name):
            self.__printer.error(ctx, err_msg)

    def __verify_lower_camel_case(ctx, err_msg):
        name = ctx.getText()
        if not is_lower_camel_case(name):
            self.__printer.error(ctx, err_msg)
