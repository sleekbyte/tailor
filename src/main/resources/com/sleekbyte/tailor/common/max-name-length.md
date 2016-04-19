### [max-name-length](https://github.com/sleekbyte/tailor/issues/13)
Enforce a character limit on the maximum length of each construct name for classes, enums, enumcases, structs, protocols, elements, functions, labels, setters, typealiases, types, variables, and constants.

```
tailor [--max-name-length <x>]
```

For example, limiting names to 10 characters would trigger a violation for the following 12 character constant name:

```
let my12CharName = 42
```
