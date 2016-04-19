### [terminating-newline](https://github.com/sleekbyte/tailor/issues/122)
Verify that source files terminate with exactly one `\n` character. This ensures that the last line of the file is valid according to the [POSIX standard](http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_206). Also see [No Newline at End of File](https://robots.thoughtbot.com/no-newline-at-end-of-file) for more information.

Swift source files should terminate with exactly 1 `\n` character, not 0 nor more than 1.

*Preferred*

```
let myConstant = 42¬
<EOF>
```

*Not Preferred*

```
let myConstant = 42<EOF>
```

```
let myConstant = 42¬
¬
¬
<EOF>
```
