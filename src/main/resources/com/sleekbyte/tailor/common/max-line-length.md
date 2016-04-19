### [max-line-length](https://github.com/sleekbyte/tailor/issues/13)
Enforce a character limit on the length of each line.

```
tailor [-l <x>|--max-line-length <x>]
```

For example, limiting lines to 10 characters would trigger a violation for the following 14 character line:

```
let hello = 42
```
