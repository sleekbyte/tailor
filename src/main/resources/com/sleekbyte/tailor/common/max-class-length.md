### [max-class-length](https://github.com/sleekbyte/tailor/issues/14)
Enforce a line limit on the lengths of class bodies.

```
tailor [--max-class-length <x>]
```

For example, limiting classes to 2 lines would trigger a violation for the following 3 line class:

```
1 class ThreeLineClass {
2     // This class spans from lines 1-4.
3
4 }
```
