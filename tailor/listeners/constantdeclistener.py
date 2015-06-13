from tailor.swift.swiftlistener import SwiftListener
from tailor.swift.swiftparser import SwiftParser
from tailor.utils import charformat


class ConstantDecListener(SwiftListener):

    def __init__(self, printer):
        self.__printer = printer

    def enterIdentifier(self, ctx):
        constant_name = ctx.getText()
        constant_decl_ctx = self.__get_constant_declaration(ctx)
        if (self.__is_global(constant_decl_ctx)
                or self.__inside_class(constant_decl_ctx)
                or self.__inside_struct(constant_decl_ctx)):
            if not (charformat.is_upper_camel_case(constant_name)
                    or charformat.is_lower_camel_case(constant_name)):
                self.__printer.error('Global constant should be either' +
                                     ' lowerCamelCase or UpperCamelCase',
                                     ctx=ctx)
        else:
            if not charformat.is_lower_camel_case(constant_name):
                self.__printer.error('Constant should be in lowerCamelCase',
                                     ctx=ctx)

    def __get_constant_declaration(self, ctx):
        if ctx.parentCtx:
            if isinstance(ctx.parentCtx,
                          SwiftParser.ConstantDeclarationContext):
                return ctx.parentCtx
            else:
                return self.__get_constant_declaration(ctx.parentCtx)
        else:
            return None

    def __is_global(self, ctx):
        parent = self.__get_nth_parent(ctx, 3)
        return parent and isinstance(parent, SwiftParser.TopLevelContext)

    def __inside_class(self, ctx):
        root_ctx = self.__get_nth_parent(self.__get_declaration_root(ctx), 1)
        return root_ctx and isinstance(root_ctx,
                                       SwiftParser.ClassDeclarationContext)

    def __inside_struct(self, ctx):
        root_ctx = self.__get_nth_parent(self.__get_declaration_root(ctx), 1)
        return root_ctx and isinstance(root_ctx,
                                       SwiftParser.StructDeclarationContext)

    @staticmethod
    # pylint: disable=invalid-name
    def __get_nth_parent(ctx, n):
        if ctx is None:
            return None
        while n != 0:
            n -= 1
            ctx = ctx.parentCtx
            if ctx is None:
                return None
        return ctx

    @staticmethod
    def __get_declaration_root(ctx):
        if ctx is None:
            return None
        while True:
            ctx = ctx.parentCtx
            if not (isinstance(ctx, SwiftParser.DeclarationsContext)
                    or isinstance(ctx, SwiftParser.DeclarationContext)):
                break
        return ctx
