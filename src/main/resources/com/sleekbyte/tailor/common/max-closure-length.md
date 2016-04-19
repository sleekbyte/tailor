### [max-closure-length](https://github.com/sleekbyte/tailor/issues/14)
Enforce a line limit on the lengths of closure bodies.

```
tailor [--max-closure-length <x>]
```

For example, limiting closures to 2 lines would trigger a violation for the following 3 line closure:

```
1 reversed = names.sort({ (s1: String, s2: String) -> Bool in
2     // This closure spans from lines 1-4.
3     return s1 > s2
4 })
```
