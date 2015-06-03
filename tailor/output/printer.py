import os


class Printer:

    def __init__(self, filepath):
        self.__filepath = os.path.abspath(filepath)

    def warn(self, ctx, warn_msg):
        self.__print(ctx, 'warning', warn_msg)

    def error(self, ctx, err_msg):
        self.__print(ctx, 'error', err_msg)

    def __print(self, ctx, classification, message):
        print(self.__filepath + ':' + str(ctx.start.line) + ':' +
              str(ctx.start.column) + ': ' + classification + ': ' + message)
