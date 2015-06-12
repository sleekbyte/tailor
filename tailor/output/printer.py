import os

from tailor.types.location import Location


class Printer:

    def __init__(self, filepath):
        self.__filepath = os.path.abspath(filepath)

    def warn(self, warn_msg, ctx=None, loc=Location(1, 1)):
        self.__print('warning', warn_msg, ctx, loc)

    def error(self, err_msg, ctx=None, loc=Location(1, 1)):
        self.__print('error', err_msg, ctx, loc)

    def __print(self, classification, msg, ctx, loc):
        if ctx is not None:
            print(self.__filepath + ':' + str(ctx.start.line) + ':' +
                  str(ctx.start.column + 1) + ': ' + classification +
                  ': ' + msg)
        else:
            print(self.__filepath + ':' + str(loc.line) + ':' +
                  str(loc.column) + ': ' + classification + ': ' + msg)
