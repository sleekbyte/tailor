#!/usr/bin/env sh

set -ex

wget http://antlr.org/download/antlr-4.5-complete.jar
alias antlr4='java -jar ../antlr-4.5-complete.jar'

script/antlr
