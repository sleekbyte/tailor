from antlr4 import ParseTreeWalker

from tailor.listeners.constantdeclistener import ConstantDecListener
from tailor.swift.swiftlistener import SwiftListener
from tailor.types.location import Location
from tailor.utils.charformat import is_upper_camel_case
from tailor.utils.sourcefile import construct_too_long, name_too_long


class MainListener(SwiftListener):
    # pylint: disable=too-many-public-methods

    def __init__(self, printer, max_lengths):
        self.__printer = printer
        self.__max_lengths = max_lengths

    def enterClassName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Class names should be in UpperCamelCase')
        self.__verify_name_length(
            ctx, 'Class', self.__max_lengths.max_name_length)

    def enterEnumName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Enum names should be in UpperCamelCase')
        self.__verify_name_length(
            ctx, 'Enum', self.__max_lengths.max_name_length)

    def enterEnumCaseName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Enum case names should be in UpperCamelCase')
        self.__verify_name_length(
            ctx, 'Enum Case', self.__max_lengths.max_name_length)

    def enterStructName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Struct names should be in UpperCamelCase')
        self.__verify_name_length(
            ctx, 'Struct', self.__max_lengths.max_name_length)

    def enterProtocolName(self, ctx):
        self.__verify_upper_camel_case(
            ctx, 'Protocol names should be in UpperCamelCase')
        self.__verify_name_length(
            ctx, 'Protocol', self.__max_lengths.max_name_length)

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

    def enterElementName(self, ctx):
        self.__verify_name_length(
            ctx, 'Element', self.__max_lengths.max_name_length)

    def enterExternalParameterName(self, ctx):
        self.__verify_name_length(
            ctx, 'External Parameter', self.__max_lengths.max_name_length)

    def enterFunctionName(self, ctx):
        self.__verify_name_length(
            ctx, 'Function', self.__max_lengths.max_name_length)

    def enterLabelName(self, ctx):
        self.__verify_name_length(
            ctx, 'Label', self.__max_lengths.max_name_length)

    def enterLocalParameterName(self, ctx):
        self.__verify_name_length(
            ctx, 'Local Parameter', self.__max_lengths.max_name_length)

    # TODO: Fix grammar for setter
    def enterSetterName(self, ctx):
        self.__verify_name_length(
            ctx, 'Setter', self.__max_lengths.max_name_length)

    def enterTypealiasName(self, ctx):
        self.__verify_name_length(
            ctx, 'Typealias', self.__max_lengths.max_name_length)

    def enterTypeName(self, ctx):
        self.__verify_name_length(
            ctx, 'Type', self.__max_lengths.max_name_length)

    # TODO: Fix grammar for variable names, ensure constants are checked
    def enterVariableName(self, ctx):
        self.__verify_name_length(
            ctx, 'Variable', self.__max_lengths.max_name_length)

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

    def __verify_name_length(self, ctx, construct_type, max_length):
        if name_too_long(ctx, max_length):
            self.__printer.error(
                construct_type + ' name is over maximum character limit (' +
                str(len(ctx.getText())) + '/' + str(max_length) + ')', ctx)
