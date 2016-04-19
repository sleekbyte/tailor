### [min-name-length](https://github.com/sleekbyte/tailor/issues/90)
Enforce a character limit on the minimum length of each construct name for classes, enums, enumcases, structs, protocols, elements, functions, labels, setters, typealiases, types, variables, and constants.

```
tailor [--min-name-length <x>]
```

For example, limiting names to at least 3 characters would trigger a violation for the following single character constant name:

```
let a = 42
```
