### [max-file-length](https://github.com/sleekbyte/tailor/issues/14)
Enforce a line limit on the length of each file.

```
tailor [--max-file-length <x>]
```

For example, limiting files to 2 lines would trigger a violation for the following 3 line file:

```
1 let myHello = "Hello,"
2 let myWorld = " world!"
3 println(myHello + myWorld)
```
