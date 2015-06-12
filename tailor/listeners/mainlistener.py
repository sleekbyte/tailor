from tailor.swift.swiftlistener import SwiftListener
from tailor.types.location import Location
from tailor.utils.charformat import is_upper_camel_case
from tailor.utils.sourcefile import construct_too_long


class MainListener(SwiftListener):

    def __init__(self, printer, max_lengths):
        self.__printer = printer
        self.__max_lengths = max_lengths

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

    def enterClassBody(self, ctx):
        self.__verify_construct_length(
            ctx, 'Class', self.__max_lengths.max_class_length)

    def enterClosureExpression(self, ctx):
        self.__verify_construct_length(
            ctx, 'Closure', self.__max_lengths.max_closure_length)

    def enterFunctionBody(self, ctx):
        self.__verify_construct_length(
            ctx, 'Function', self.__max_lengths.max_function_length)

    def enterStructBody(self, ctx):
        self.__verify_construct_length(
            ctx, 'Struct', self.__max_lengths.max_struct_length)

    def __verify_upper_camel_case(self, ctx, err_msg):
        construct_name = ctx.getText()
        if not is_upper_camel_case(construct_name):
            self.__printer.error(err_msg, ctx)

    def __verify_not_semicolon_terminated(self, ctx):
        line = ctx.getText()
        if line.endswith(';'):
            self.__printer.error(
                'Statement should not terminate with a semicolon',
                loc=Location(ctx.stop.line, ctx.stop.column + 1))

    def __verify_construct_length(self, ctx, construct_type, max_length):
        if construct_too_long(ctx, max_length):
            self.__printer.error(
                construct_type + ' is over maximum line limit (' +
                str(ctx.stop.line - ctx.start.line) + '/' + str(max_length) +
                ')', ctx)
