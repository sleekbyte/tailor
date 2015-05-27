import sys

from antlr4 import FileStream, CommonTokenStream, ParseTreeWalker
from swift.swiftlexer import SwiftLexer
from swift.swiftparser import SwiftParser
from listener.mainlistener import MainListener

def main(argv):
    input = FileStream(argv[1])
    lexer = SwiftLexer(input)
    stream = CommonTokenStream(lexer)
    parser = SwiftParser(stream)
    tree = parser.topLevel()
    listener = MainListener()
    walker = ParseTreeWalker()
    walker.walk(listener, tree)


if __name__ == '__main__':
    main(sys.argv)
