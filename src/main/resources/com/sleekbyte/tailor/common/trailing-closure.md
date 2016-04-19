### [trailing-closure](https://github.com/sleekbyte/tailor/issues/307)

Closures that are the last argument of a function should be passed into the function using [trailing closure](https://developer.apple.com/library/ios/documentation/Swift/Conceptual/Swift_Programming_Language/Closures.html#//apple_ref/doc/uid/TP40014097-CH11-ID94) syntax.

*Preferred*

```
reversed = names.sort { s1, s2 in return s1 > s2 }
```

*Not Preferred*

```
reversed = names.sort({ s1, s2 in return s1 > s2 })
```
