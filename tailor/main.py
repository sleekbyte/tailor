"""Perform static analysis on a Swift source file."""

import argparse
import os
import sys

PARENT_PATH = os.path.join(os.path.abspath(os.path.dirname(__file__)), '..')
sys.path.append(PARENT_PATH)

from antlr4 import FileStream, CommonTokenStream, ParseTreeWalker

from tailor.listeners.filelistener import FileListener
from tailor.listeners.mainlistener import MainListener
from tailor.output.printer import Printer
from tailor.swift.swiftlexer import SwiftLexer
from tailor.swift.swiftparser import SwiftParser
from tailor.types.maxlengths import MaxLengths


def parse_args():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('infile', type=os.path.abspath,
                        help='Swift source file')
    parser.add_argument('-l', '--max-file-length', type=int, default=0,
                        help='maximum file length (in lines)')
    parser.add_argument('--max-class-length', type=int, default=0,
                        help='maximum class length (in lines)')
    parser.add_argument('--max-closure-length', type=int, default=0,
                        help='maximum closure length (in lines)')
    parser.add_argument('--max-function-length', type=int, default=0,
                        help='maximum function length (in lines)')
    parser.add_argument('--max-struct-length', type=int, default=0,
                        help='maximum struct length (in lines)')
    return parser.parse_args()


def main():
    args = parse_args()
    max_lengths = MaxLengths(args.max_class_length,
                             args.max_closure_length,
                             args.max_function_length,
                             args.max_struct_length)

    printer = Printer(filepath=args.infile)
    lexer = SwiftLexer(FileStream(args.infile))
    stream = CommonTokenStream(lexer)
    parser = SwiftParser(stream)
    tree = parser.topLevel()
    listener = MainListener(printer, max_lengths)
    walker = ParseTreeWalker()
    walker.walk(listener, tree)

    file_listener = FileListener(printer, args.infile)
    file_listener.verify(args.max_file_length)

if __name__ == '__main__':
    main()
