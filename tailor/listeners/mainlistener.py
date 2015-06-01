from tailor.swift.swiftlistener import SwiftListener
from tailor.utils.charformat import isUpperCamelCase

class MainListener(SwiftListener):

    def __init__(self, printer):
        self.__printer = printer

    def enterClassName(self, ctx):
        className = ctx.getText()
        if not isUpperCamelCase(className):
            self.__printer.warn(ctx, 'Class Names should be in UpperCamelCase')
