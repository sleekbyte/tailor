"""Perform static analysis on a Swift source file."""

import argparse
import os
import sys

PARENT_PATH = os.path.join(os.path.abspath(os.path.dirname(__file__)), '..')
sys.path.append(PARENT_PATH)

from antlr4 import FileStream, CommonTokenStream, ParseTreeWalker

from tailor.listeners.mainlistener import MainListener
from tailor.output.printer import Printer
from tailor.swift.swiftlexer import SwiftLexer
from tailor.swift.swiftparser import SwiftParser


def parse_args():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('infile', type=os.path.abspath,
                        help='Swift source file')
    parser.add_argument('-l', '--max-lines', type=int, default=0,
                        help='maximum file line length')
    return parser.parse_args()


def main():
    args = parse_args()

    printer = Printer(filepath=args.infile)
    lexer = SwiftLexer(FileStream(args.infile))
    stream = CommonTokenStream(lexer)
    parser = SwiftParser(stream)
    tree = parser.topLevel()
    listener = MainListener(printer)
    walker = ParseTreeWalker()
    walker.walk(listener, tree)


if __name__ == '__main__':
    main()
