[![Tailor](https://cloud.githubusercontent.com/assets/1350704/9867389/18ae2e06-5b3b-11e5-9b37-72a3e9621b9c.png)](https://tailor.sh)

<p align="center">
  <a href="https://travis-ci.org/sleekbyte/tailor">
    <img src="https://travis-ci.org/sleekbyte/tailor.svg?branch=master" alt="Build Status">
  </a>
  <a href="https://coveralls.io/github/sleekbyte/tailor">
    <img src="https://coveralls.io/repos/github/sleekbyte/tailor/badge.svg" alt="Code Coverage">
  </a>
  <a href="https://codeclimate.com/github/sleekbyte/tailor">
    <img src="https://codeclimate.com/github/sleekbyte/tailor/badges/gpa.svg" alt="Code Climate">
  </a>
</p>

<p align="center">
  <a href="https://github.com/sleekbyte/tailor/wiki">Wiki</a>
  •
  <a href="#installation">Installation</a>
  •
  <a href="#usage">Usage</a>
  •
  <a href="#features">Features</a>
  •
  <a href="#developers">Developers</a>
  •
  <a href="#license">License</a>
</p>

[Tailor][] is a cross-platform [static analysis][] and [lint][] tool for source code written in Apple's [Swift][] programming language. It analyzes your code to ensure consistent styling and help avoid bugs.

[static analysis]: https://en.wikipedia.org/wiki/Static_program_analysis
[lint]: https://en.wikipedia.org/wiki/Lint_(software)

## [Tailor][]. Cross-platform static analyzer and linter for [Swift][].

[Tailor]: https://tailor.sh
[Swift]: https://swift.org

Tailor supports Swift 3.0.1 out of the box and helps enforce style guidelines outlined in the [The Swift Programming Language][], [GitHub][], [Ray Wenderlich][], and [Coursera][] style guides. It supports cross-platform usage and can be run on Mac OS X via your shell or integrated with Xcode, as well as on Linux and Windows.

[The Swift Programming Language]: https://developer.apple.com/library/ios/documentation/Swift/Conceptual/Swift_Programming_Language/
[GitHub]: https://github.com/github/swift-style-guide
[Ray Wenderlich]: https://github.com/raywenderlich/swift-style-guide
[Coursera]: https://github.com/coursera/swift-style-guide

Tailor parses Swift source code using the primary Java target of [ANTLR](http://www.antlr.org):

> ANTLR is a powerful parser generator [ . . . ] widely used in academia and industry to build all sorts of languages, tools, and frameworks.

— [About the ANTLR Parser Generator](http://www.antlr.org/about.html)

# Getting Started

## Installation

Requires Java (JRE or JDK) Version 8 or above: [Java SE Downloads](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

### [Homebrew](http://brew.sh), [Linuxbrew](http://linuxbrew.sh)


```bash
brew install tailor
```

### Mac OS X (10.10+), Linux

```bash
curl -fsSL https://tailor.sh/install.sh | sh
```

### Windows (10+)

```powershell
iex (new-object net.webclient).downloadstring('https://tailor.sh/install.ps1')
```

### Manually

You may also download Tailor via [GitHub Releases](https://github.com/sleekbyte/tailor/releases), extract the archive, and symlink the `tailor/bin/tailor` shell script to a location in your `$PATH`.

### Continuous Integration

If your continuous integration server supports [Homebrew](http://brew.sh) installation, you may use the following snippet:

```yaml
before_install:
  - brew update
  - brew install tailor
```

In other cases, use this snippet:

Replace `${TAILOR_RELEASE_ARCHIVE}` with the URL of the release you would like to install, e.g. `https://github.com/sleekbyte/tailor/releases/download/v0.1.0/tailor.tar`.

```yaml
before_script:
  - wget ${TAILOR_RELEASE_ARCHIVE} -O /tmp/tailor.tar
  - tar -xvf /tmp/tailor.tar
  - export PATH=$PATH:$PWD/tailor/bin/
```

## Usage

Run Tailor with a list of files and directories to analyze, or via Xcode.

```bash
$ tailor [options] [--] [[file|directory] ...]
```

Help for Tailor is accessible via the `[-h|--help]` option.

```
$ tailor -h
Usage: tailor [options] [--] [[file|directory] ...]

Perform static analysis on Swift source files.

Invoking Tailor with at least one file or directory will analyze all Swift files at those paths. If
no paths are provided, Tailor will analyze all Swift files found in '$SRCROOT' (if defined), which
is set by Xcode when run in a Build Phase. Tailor may be set up as an Xcode Build Phase
automatically with the --xcode option.

Options:
 -c,--config=<path/to/.tailor.yml>             specify configuration file
    --debug                                    print ANTLR error messages when parsing error occurs
    --except=<rule1,rule2,...>                 run all rules except the specified ones
 -f,--format=<xcode|json|cc|html>              select an output format
 -h,--help                                     display help
    --invert-color                             invert colorized console output
 -l,--max-line-length=<0-999>                  maximum Line length (in characters)
    --list-files                               display Swift source files to be analyzed
    --max-class-length=<0-999>                 maximum Class length (in lines)
    --max-closure-length=<0-999>               maximum Closure length (in lines)
    --max-file-length=<0-999>                  maximum File length (in lines)
    --max-function-length=<0-999>              maximum Function length (in lines)
    --max-name-length=<0-999>                  maximum Identifier name length (in characters)
    --max-severity=<error|warning (default)>   maximum severity
    --max-struct-length=<0-999>                maximum Struct length (in lines)
    --min-name-length=<1-999>                  minimum Identifier name length (in characters)
    --no-color                                 disable colorized console output
    --only=<rule1,rule2,...>                   run only the specified rules
    --purge=<1-999>                            reduce memory usage by clearing DFA cache after
                                               specified number of files are parsed
    --show-rules                               show description for each rule
 -v,--version                                  display version
    --xcode=<path/to/project.xcodeproj>        add Tailor Build Phase Run Script to Xcode Project
```

# Features

* [Enabling and Disabling Rules](#enabling-and-disabling-rules)
* [Cross-Platform](#cross-platform)
* [Automatic Xcode Integration](#automatic-xcode-integration)
* [Colorized Output](#colorized-output)
* [Warnings, Errors, and Failing the Build](#warnings-errors-and-failing-the-build)
* [Disable Violations within Source Code](#disable-violations-within-source-code)
* [Configuration](#configuration)
* [Formatters](#formatters)

## Enabling and Disabling Rules

Rule identifiers and "preferred/not preferred" code samples may be found on the [Rules](https://github.com/sleekbyte/tailor/wiki/Rules) page.

Rules may be individually disabled (blacklist) or enabled (whitelist) via the `--except` and `--only` command-line flags.

### Except

```bash
tailor --except=brace-style,trailing-whitespace main.swift
```

### Only

```bash
tailor --only=redundant-parentheses,terminating-semicolon main.swift
```

## Cross-Platform

Tailor may be used on Mac OS X via your shell or integrated with Xcode, as well as on Linux and Windows.

### Linux

![Tailor on Ubuntu](https://cloud.githubusercontent.com/assets/1350704/9894130/2b959794-5bee-11e5-9ed2-84d035895239.png)

### Windows

![Tailor on Windows](https://cloud.githubusercontent.com/assets/1791760/9913016/2ff0e9a8-5cc8-11e5-8722-d5a6f9d84027.PNG)

## Automatic Xcode Integration

Tailor can be integrated with Xcode projects using the `--xcode` option.

```bash
tailor --xcode /path/to/demo.xcodeproj/
```

This adds the following Build Phase Run Script to your project's default target.
![Run Script](https://cloud.githubusercontent.com/assets/1350704/11074861/5bae6b24-87f2-11e5-8167-4328b9b01174.png)

Tailor's output will be displayed inline within the Xcode Editor Area and as a list in the Log Navigator.
![Xcode messages](https://cloud.githubusercontent.com/assets/1350704/11017260/b79cb162-8599-11e5-94fa-e7cf77fdc657.png)

### Configure Xcode to Analyze Code Natively (⇧⌘B)

1. Add a new configuration, say `Analyze`,  to the project
<img width="1020" alt="screen shot 2016-11-30 at 12 29 34 am" src="https://cloud.githubusercontent.com/assets/1791760/20745188/fba98222-b694-11e6-8212-038333b259b3.png">

2. Modify the active scheme's `Analyze` phase to use the new build configuration created above
<img width="899" alt="screen shot 2016-11-30 at 12 37 08 am" src="https://cloud.githubusercontent.com/assets/1791760/20745259/57e71acc-b695-11e6-9671-092f89029467.png">

3. Tweak the build phase run script to run Tailor **only** when analyzing the project (⇧⌘B)
```bash
if [ "${CONFIGURATION}" = "Analyze" ]; then
    if hash tailor 2>/dev/null; then
        tailor
    else
        echo "warning: Please install Tailor from https://tailor.sh"
    fi
fi
```

## Colorized Output

Tailor uses the following color schemes to format CLI output:

* **Dark theme** (enabled by default)
  ![Dark theme](https://cloud.githubusercontent.com/assets/1791760/9807444/fde82de6-5870-11e5-9e20-05a9d736e136.png)

* **Light theme** (enabled via `--invert-color` option)
  ![Light theme](https://cloud.githubusercontent.com/assets/1791760/9807312/129ce45e-586f-11e5-8e26-fe818af0ec09.png)

* **No color theme** (enabled via `--no-color` option)
  ![No color](https://cloud.githubusercontent.com/assets/1791760/9807318/261811d4-586f-11e5-9010-0e627431bbb9.png)

## Warnings, Errors, and Failing the Build

`--max-severity` can be used to control the maximum severity of violation messages. It can be set to `error` or `warning` (by default, it is set to `warning`). Setting it to `error` allows you to distinguish between lower and higher priority messages. It also fails the build in Xcode, if any errors are reported (similar to how a compiler error fails the build in Xcode). With `max-severity` set to `warning`, all violation messages are warnings and the Xcode build will never fail.

This setting also affects Tailor's exit code on the command-line, a failing build will `exit 1` whereas having warnings only will `exit 0`, allowing Tailor to be easily integrated into pre-commit hooks.

## Disable Violations within Source Code

Violations on a specific line may be disabled with a **trailing** single-line comment.

```swift
import Foundation; // tailor:disable
```

Additionally, violations in a given block of code can be disabled by **enclosing the block** within `tailor:off` and `tailor:on` comments.

```swift
// tailor:off
import Foundation;
import UIKit;
import CoreData;
// tailor:on

class Demo() {
  // Define public members here
}
```

### Note

* `// tailor:off` and `// tailor:on` comments must be paired

## Configuration

The behavior of Tailor can be customized via the `.tailor.yml` configuration file. It enables you to

* include/exclude certain files and directories from analysis
* enable and disable specific analysis rules
* specify output format
* specify CLI output color scheme

You can tell Tailor which configuration file to use by specifying its file path via the `--config` CLI option. By default, Tailor will look for the configuration file in the directory where you will run Tailor from.

The file follows the [YAML 1.1](http://www.yaml.org/spec/1.1/) format.

### Including/Excluding files

Tailor checks all files found by a recursive search starting from the directories given as command line arguments. However, it only analyzes Swift files that end in `.swift`. If you would like Tailor to analyze specific files and directories, you will have to add entries for them under `include`. Files and directories can also be ignored through `exclude`.

Here is an example that might be used for an iOS project:

```yaml
include:
    - Source            # Inspect all Swift files under "Source/"
exclude:
    - '**Tests.swift'   # Ignore Swift files that end in "Tests"
    - Source/Carthage   # Ignore Swift files under "Source/Carthage/"
    - Source/Pods       # Ignore Swift files under "Source/Pods/"
```

#### Notes

* Files and directories are specified relative to where `tailor` is run from
* Paths to directories or Swift files provided explicitly via CLI will cause the `include`/`exclude` rules specified in `.tailor.yml` to be ignored
* *Exclude* is given higher precedence than *Include*
* Tailor recognizes the [Java Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob) syntax

### Enabling/Disabling rules

Tailor allows you to individually disable (blacklist) or enable (whitelist) rules via the `except` and `only` labels.

Here is an example showcasing how to enable certain rules:

```yaml
# Tailor will solely check for violations to the following rules
only:
    - upper-camel-case
    - trailing-closure
    - forced-type-cast
    - redundant-parentheses
```

Here is an example showcasing how to disable certain rules:

```yaml
# Tailor will check for violations to all rules except for the following ones
except:
    - parenthesis-whitespace
    - lower-camel-case
```

#### Notes

* *only* is given precedence over *except*
* Rules that are explicitly included/excluded via CLI will cause the `only`/`except` rules specified in `.tailor.yml` to be ignored

### Specifying output format

Tailor allows you to specify the output format (`xcode`/`json`) via the `format` label.

Here is an example showcasing how to specify the output format:

```yaml
# The output format will now be in JSON
format: json
```

#### Note

* The output format explicitly specified via CLI will cause the output format defined in `.tailor.yml` to be ignored

### Specifying CLI output color scheme

Tailor allows you to specify the CLI output color schemes  via the `color` label.  To disable colored output, set `color` to `disable`. To invert the color scheme, set `color` to `invert`.

Here is an example showcasing how to specify the CLI output color scheme:

```yaml
# The CLI output will not be colored
color: disable
```

#### Note

* The CLI output color scheme explicitly specified via CLI will cause the output color scheme  defined in `.tailor.yml` to be ignored

## Formatters

Tailor's output format may be customized via the `-f`/`--format` option. The Xcode formatter is selected by default.

### Xcode Formatter (default)

The default `xcode` formatter outputs violation messages according to the format expected by Xcode to be displayed inline within the Xcode Editor Area and as a list in the Log Navigator. This format is also as human-friendly as possible on the console.

```
$ tailor main.swift

********** /main.swift **********
/main.swift:1:    warning: [multiple-imports] Imports should be on separate lines
/main.swift:1:18: warning: [terminating-semicolon] Statements should not terminate with a semicolon
/main.swift:3:05: warning: [constant-naming] Global Constant should be either lowerCamelCase or UpperCamelCase
/main.swift:5:07: warning: [redundant-parentheses] Conditional clause should not be enclosed within parentheses
/main.swift:7:    warning: [terminating-newline] File should terminate with exactly one newline character ('\n')

Analyzed 1 file, skipped 0 files, and detected 5 violations (0 errors, 5 warnings).
```

### [JSON](http://www.json.org) Formatter

The `json` formatter outputs an array of violation messages for each file, and a `summary` object indicating the parsing results and the violation counts.

```
$ tailor -f json main.swift
{
  "files": [
    {
      "path": "/main.swift",
      "violations": [
        {
          "severity": "warning",
          "rule": "constant-naming",
          "location": {
            "line": 1,
            "column": 5
          },
          "message": "Global Constant should be either lowerCamelCase or UpperCamelCase"
        }
      ],
      "parsed": true
    }
  ],
  "summary": {
    "violations": 1,
    "warnings": 1,
    "analyzed": 1,
    "errors": 0,
    "skipped": 0
  }
}
```

### HTML Formatter

The `html` formatter outputs a complete HTML document that should be written to a file.

```
tailor -f html main.swift > tailor.html
```

![HTML format](https://cloud.githubusercontent.com/assets/1350704/14971733/236fd5b0-10a3-11e6-9a02-fa1e70702e47.png)

# Developers

Please review the [guidelines for contributing](https://github.com/sleekbyte/tailor/blob/master/.github/CONTRIBUTING.md) to this repository.

## Development Environment

* [Java Version 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradle](https://gradle.org) (optional, `./gradlew` may be used instead)
* [Ruby 2.0.0+](https://www.ruby-lang.org/en/)
* [Bundler](http://bundler.io)

# External Tools and Libraries

## Development & Runtime

Tool                                                                    | License
----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------
[ANTLR 4.5](https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Home) | [The BSD License](http://www.antlr.org/license.html)
[Apache Commons CLI](http://commons.apache.org/proper/commons-cli/)     | [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
[Jansi](https://github.com/fusesource/jansi)                            | [Apache License, Version 2.0](https://github.com/fusesource/jansi/blob/master/license.txt)
[Xcodeproj](https://github.com/CocoaPods/Xcodeproj)                     | [MIT](https://github.com/CocoaPods/Xcodeproj/blob/master/LICENSE)
[SnakeYAML](https://bitbucket.org/asomov/snakeyaml)                     | [Apache License, Version 2.0](https://bitbucket.org/asomov/snakeyaml/raw/8939e0aa430d25b3b49b353508b23e072dd02171/LICENSE.txt)
[Gson](https://github.com/google/gson)                                  | [Apache License, Version 2.0](https://github.com/google/gson/blob/master/LICENSE)
[Mustache.java](https://github.com/spullara/mustache.java)              | [Apache License, Version 2.0](https://github.com/spullara/mustache.java/blob/master/LICENSE)

## Development Only

Tool                                                         | License
------------------------------------------------------------ | ----------------------------------------------------------------------------------------
[Gradle](https://gradle.org)                                 | [Apache License, Version 2.0](http://gradle.org/license/)
[Travis CI](https://travis-ci.org)                           | [Free for Open Source Projects](https://travis-ci.com/plans)
[Mockito](http://mockito.org)                                | [MIT](https://code.google.com/p/mockito/wiki/License)
[JUnit](http://junit.org)                                    | [Eclipse Public License 1.0](http://junit.org/license)
[Java Hamcrest](http://hamcrest.org/JavaHamcrest/)           | [The BSD 3-Clause License](http://opensource.org/licenses/BSD-3-Clause)
[FindBugs](http://findbugs.sourceforge.net)                  | [GNU Lesser General Public License](http://findbugs.sourceforge.net/manual/license.html)
[Checkstyle](http://checkstyle.sourceforge.net)              | [GNU Lesser General Public License](http://checkstyle.sourceforge.net/license.html)
[PMD](http://pmd.sourceforge.net)                            | [BSD-style](http://pmd.sourceforge.net/pmd-5.3.2/license.html)
[JaCoCo](http://eclemma.org/jacoco/)                         | [Eclipse Public License v1.0](http://eclemma.org/license.html)
[Coveralls](https://coveralls.io)                            | [Free for Open Source](https://coveralls.io/pricing)
[Bundler](http://bundler.io)                                 | [MIT](https://github.com/bundler/bundler/blob/master/LICENSE.md)
[Codacy](https://www.codacy.com)                             | [Free for Open Source](https://www.codacy.com/pricing)
[System Rules](http://stefanbirkner.github.io/system-rules/) | [Common Public License 1.0](https://stefanbirkner.github.io/system-rules/license.html)
[Ronn](https://github.com/rtomayko/ronn)                     | [MIT](https://github.com/rtomayko/ronn/blob/master/COPYING)

# License

Tailor is released under the MIT license. See [LICENSE.md](https://github.com/sleekbyte/tailor/blob/master/LICENSE.md) for details.
