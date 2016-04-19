### [redundant-optional-binding](https://github.com/sleekbyte/tailor/issues/289)

Optional binding lists should not have consecutive `var`/`let` bindings.  All constants must be preceded by at most one `let` binding.  All variables must be preceded by only one `var` binding.

*Preferred*

```
if var a = a, b = b, c = c where c != 0 {
    print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
}

if let a = a, b = b, var c = c where c != 0 {
    print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
}
```

*Not Preferred*

```
if var a = a, var b = b, var c = c where c != 0 {
    print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
}

if let a = a, let b = b, var c = c where c != 0 {
    print("(a + b) / c = \((a + b) / c)")     // (a + b) / c = 5
}
```
