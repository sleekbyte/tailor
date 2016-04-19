### [max-struct-length](https://github.com/sleekbyte/tailor/issues/14)
Enforce a line limit on the lengths of struct bodies.

```
tailor [--max-struct-length <x>]
```

For example, limiting structs to 2 lines would trigger a violation for the following 3 line struct:

```
1 struct ThreeLineStruct {
2     // This struct spans from lines 1-4.
3
4 }
```
