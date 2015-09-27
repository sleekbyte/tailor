[![Tailor](https://cloud.githubusercontent.com/assets/1350704/9867389/18ae2e06-5b3b-11e5-9b37-72a3e9621b9c.png)](http://tailor.sh)

[![Build Status](https://travis-ci.org/sleekbyte/tailor.svg?branch=master)](https://travis-ci.org/sleekbyte/tailor)

# [Tailor](http://tailor.sh). Static analyzer for [Swift](https://developer.apple.com/swift/).
Tailor supports Swift 2 out of the box and helps enforce style guidelines outlined in the [The Swift Programming Language](https://developer.apple.com/library/ios/documentation/Swift/Conceptual/Swift_Programming_Language/), [GitHub](https://github.com/github/swift-style-guide), [Ray Wenderlich](https://github.com/raywenderlich/swift-style-guide), [Jamie Forrest](https://github.com/jamieforrest/swift-style-guide), and [Coursera](https://github.com/coursera/swift-style-guide) style guides. It supports cross-platform usage and can be run on Windows and Linux.

**See the [wiki](https://github.com/sleekbyte/tailor/wiki) for full documentation.**

# Getting Started

## Installation

Requires Java (JRE or JDK) Version 8 or above: [Java SE Downloads](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Mac OS X (10.10+), Linux (Ubuntu, etc.)

```bash
curl -fsSL http://tailor.sh/install.sh | sh
```

Windows (10+)

```powershell
iex (new-object net.webclient).downloadstring('http://tailor.sh/install.ps1')
```

You may also download Tailor via [GitHub Releases](https://github.com/sleekbyte/tailor/releases), then unzip, and symlink the `tailor/bin/tailor` shell script to a location in your `$PATH`.

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
    --debug                                    print ANTLR error messages when parsing error occurs
    --except=<rule1,rule2,...>                 run all rules except the specified ones
 -h,--help                                     display help
    --invert-color                             invert colorized console output
 -l,--max-line-length=<0-999>                  maximum Line length (in characters)
    --max-class-length=<0-999>                 maximum Class length (in lines)
    --max-closure-length=<0-999>               maximum Closure length (in lines)
    --max-file-length=<0-999>                  maximum File length (in lines)
    --max-function-length=<0-999>              maximum Function length (in lines)
    --max-name-length=<0-999>                  maximum Identifier name length (in characters)
    --max-severity=<error|warning (default)>   maximum severity
    --max-struct-length=<0-999>                maximum Struct length (in lines)
    --no-color                                 disable colorized console output
    --only=<rule1,rule2,...>                   run only the specified rules
    --show-rules                               show description for each rule
 -v,--version                                  display version
    --xcode=<path/to/project.xcodeproj>        add Tailor Build Phase Run Script to Xcode Project
```

# Features
* [Rules](https://github.com/sleekbyte/tailor/wiki/Rules)
* [Cross-Platform](#cross-platform)
* [Automatic Xcode Integration](#automatic-xcode-integration)
* [Colourized Output](#colourized-output)
* [Warnings, Errors, and Failing the Build](#warnings-errors-and-failing-the-build)
* [Disable Violations within Source Code](#disable-violations-within-source-code)

### Cross-Platform
Tailor may be used on Mac OS X via your shell or integrated with Xcode, as well as on Windows and Linux. Great news for when the Swift compiler comes to Linux later this year!

#### Windows
<img width="918" alt="windows" src="https://cloud.githubusercontent.com/assets/1791760/9913016/2ff0e9a8-5cc8-11e5-8722-d5a6f9d84027.PNG">

#### Linux
![Tailor on Ubuntu](https://cloud.githubusercontent.com/assets/1350704/9894130/2b959794-5bee-11e5-9ed2-84d035895239.png)

### Automatic Xcode Integration
Tailor can be integrated with Xcode projects using the `--xcode` option.
```bash
tailor --xcode /path/to/demo.xcodeproj/
```
This adds the following Build Phase Run Script to your project's default target.
![run-script](https://cloud.githubusercontent.com/assets/1791760/9933657/f7f29216-5d69-11e5-90f6-705a76473cb7.png)


### Colourized Output
Tailor uses the following colour schemes to format CLI output:

* **Dark theme** (enabled by default)
<img width="962" alt="dark-color" src="https://cloud.githubusercontent.com/assets/1791760/9807444/fde82de6-5870-11e5-9e20-05a9d736e136.png">
* **Light theme** (enabled via `--invert-color` option)
<img width="962" alt="light-color" src="https://cloud.githubusercontent.com/assets/1791760/9807312/129ce45e-586f-11e5-8e26-fe818af0ec09.png">
* **No colour theme** (enabled via `--no-color` option)
<img width="962" alt="no-color" src="https://cloud.githubusercontent.com/assets/1791760/9807318/261811d4-586f-11e5-9010-0e627431bbb9.png">

### Warnings, Errors, and Failing the Build
`--max-severity` can be used to control the maximum severity of violation messages. It can be set to `error` or `warning` (by default, it is set to `warning`). Setting it to `error` allows you to distinguish between lower and higher priority messages. It also fails the build in Xcode, if any errors are reported (similar to how a compiler error fails the build in Xcode). With `max-severity` set to `warning`, all violation messages are warnings and the Xcode build will never fail.

This setting also affects Tailor's exit code on the command-line, a failing build will `exit 1` whereas having warnings only will `exit 0`, allowing Tailor to be easily integrated into pre-commit hooks.

### Disable Violations within Source Code
Violations on a specific line may be disabled with a **trailing** single-line comment.
```swift
import Foundation; // tailor:disable
```

# Developers
Please review the [guidelines for contributing](https://github.com/sleekbyte/tailor/blob/master/CONTRIBUTING.md) to this repository.

## Development Environment
* [Java Version 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradle](https://gradle.org) (optional, `./gradlew` may be used instead)

# External Tools and Libraries

### Development & Runtime
| Tool  | License |
| ------------- | ------------- |
| [ANTLR 4.5](https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Home)  | [The BSD License](http://www.antlr.org/license.html) |
| [Apache Commons CLI](http://commons.apache.org/proper/commons-cli/) | [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) |
| [Jansi](https://github.com/fusesource/jansi) | [Apache License, Version 2.0](https://github.com/fusesource/jansi/blob/master/license.txt) |
| [Xcodeproj](https://github.com/CocoaPods/Xcodeproj) | [MIT](https://github.com/CocoaPods/Xcodeproj/blob/master/LICENSE) |
| [SnakeYAML](https://bitbucket.org/asomov/snakeyaml) | [Apache License, Version 2.0](https://bitbucket.org/asomov/snakeyaml/raw/8939e0aa430d25b3b49b353508b23e072dd02171/LICENSE.txt) |


### Development Only
| Tool  | License |
| ------------- | ------------- |
| [Gradle](https://gradle.org)  | [Apache License, Version 2.0](http://gradle.org/license/) |
| [Travis CI](https://travis-ci.org)| [Free for Open Source Projects] (https://travis-ci.com/plans) |
| [Mockito](http://mockito.org) | [MIT](https://code.google.com/p/mockito/wiki/License) |
| [JUnit](http://junit.org) | [Eclipse Public License 1.0](http://junit.org/license) |
| [Java Hamcrest](http://hamcrest.org/JavaHamcrest/) | [The BSD 3-Clause License](http://opensource.org/licenses/BSD-3-Clause) |
| [FindBugs](http://findbugs.sourceforge.net) | [GNU Lesser General Public License](http://findbugs.sourceforge.net/manual/license.html) |
| [Checkstyle](http://checkstyle.sourceforge.net) | [GNU Lesser General Public License](http://checkstyle.sourceforge.net/license.html) |
| [PMD](http://pmd.sourceforge.net) | [BSD-style](http://pmd.sourceforge.net/pmd-5.3.2/license.html) |
| [Bundler](http://bundler.io) | [MIT](https://github.com/bundler/bundler/blob/master/LICENSE.md) |

# License
Tailor is released under the MIT license. See [LICENSE.md](https://github.com/sleekbyte/tailor/blob/master/LICENSE.md) for details.
