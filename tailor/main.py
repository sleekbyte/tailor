import os
import sys

parent_path = os.path.join(os.path.abspath(os.path.dirname(__file__)), '..')
sys.path.append(parent_path)

from antlr4 import FileStream, CommonTokenStream, ParseTreeWalker

from tailor.listeners.mainlistener import MainListener
from tailor.output.printer import Printer
from tailor.swift.swiftlexer import SwiftLexer
from tailor.swift.swiftparser import SwiftParser

def main(argv):
    input = FileStream(argv[1])
    printer = Printer(filepath=argv[1])
    lexer = SwiftLexer(input)
    stream = CommonTokenStream(lexer)
    parser = SwiftParser(stream)
    tree = parser.topLevel()
    listener = MainListener(printer)
    walker = ParseTreeWalker()
    walker.walk(listener, tree)


if __name__ == '__main__':
    main(sys.argv)
