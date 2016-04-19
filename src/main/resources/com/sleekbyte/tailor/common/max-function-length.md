### [max-function-length](https://github.com/sleekbyte/tailor/issues/14)
Enforce a line limit on the lengths of function bodies.

```
tailor [--max-function-length <x>]
```

For example, limiting functions to 2 lines would trigger a violation for the following 3 line function:

```
1 func helloWorld() {
2     // This function spans from lines 1-4.
3     println("Hello, world!")
4 }
```
