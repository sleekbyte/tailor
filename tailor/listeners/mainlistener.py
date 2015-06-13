from antlr4 import ParseTreeWalker

from tailor.listeners.constantdeclistener import ConstantDecListener
from tailor.swift.swiftlistener import SwiftListener
from tailor.types.location import Location
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

    def enterConstantDeclaration(self, ctx):
        walker = ParseTreeWalker()
        for pattern_initializer in \
                ctx.patternInitializerList().patternInitializer():
            pattern = pattern_initializer.pattern()
            self.__evaluate_pattern(pattern, walker)

    def enterValueBindingPattern(self, ctx):
        if ctx.start.text == 'let':
            walker = ParseTreeWalker()
            self.__evaluate_pattern(ctx.pattern(), walker)

    def enterParameter(self, ctx):
        for child in ctx.children:
            if child.getText() == 'var':
                return
        walker = ParseTreeWalker()
        if ctx.externalParameterName():
            self.__walk_constant_dec_listener(walker,
                                              ctx.externalParameterName())
        self.__walk_constant_dec_listener(walker, ctx.localParameterName())

    def __walk_constant_dec_listener(self, walker, tree):
        walker.walk(ConstantDecListener(self.__printer), tree)

    def __evaluate_pattern(self, pattern, walker):
        if pattern.identifierPattern():
            self.__walk_constant_dec_listener(walker,
                                              pattern.identifierPattern())
        elif pattern.tuplePattern() and \
                pattern.tuplePattern().tuplePatternElementList():
            self.__evaluate_tuple_pattern(pattern.tuplePattern(), walker)
        elif pattern.enumCasePattern() and \
                pattern.enumCasePattern().tuplePattern():
            self.__evaluate_tuple_pattern(
                pattern.enumCasePattern().tuplePattern(), walker)
        elif pattern.pattern():
            self.__evaluate_pattern(pattern.pattern(), walker)
        elif pattern.expressionPattern():
            self.__walk_constant_dec_listener(walker,
                                              pattern.expressionPattern()
                                              .expression().prefixExpression())

    def __evaluate_tuple_pattern(self, tuple_pattern, walker):
        for tuple_pattern_element in \
                tuple_pattern.tuplePatternElementList().tuplePatternElement():
            self.__evaluate_pattern(tuple_pattern_element.pattern(), walker)

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
