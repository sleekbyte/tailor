from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import is_upper_camel_case


class MainListener(SwiftListener):

    def __init__(self, printer):
        self.__printer = printer

    def enterClassName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Class names should be in UpperCamelCase')

    def enterEnumName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Enum names should be in UpperCamelCase')

    def enterEnumCaseName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Enum case names should be in UpperCamelCase')

    def enterStructName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Struct names should be in UpperCamelCase')

    def enterProtocolName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Protocol names should be in UpperCamelCase')

    def enterStatement(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterDeclaration(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterLoopStatement(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterBranchStatement(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterLabeledStatement(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterControlTransferStatement(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterUnionStyleEnumMember(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def enterProtocolMemberDeclaration(self, ctx):
        self.__verify_not_semicolon_terminated(ctx)

    def __verify_upper_camel_case(self, ctx, err_msg):
        construct_name = ctx.getText()
        if not is_upper_camel_case(construct_name):
            self.__printer.error(err_msg, ctx)

    def __verify_not_semicolon_terminated(self, ctx):
        line = ctx.getText()
        if line.endswith(';'):
            self.__printer.error(
                'Statement should not terminate with a semicolon',
                loc=(ctx.stop.line, ctx.stop.column + 1))
