### [operator-whitespace](https://github.com/sleekbyte/tailor/issues/89)

Prefer single space around operator in operator declarations.

*Preferred*

```
infix operator -+* { precedence 70 associativity right }
```

*Not Preferred*

```
infix operator-+* { precedence 70 associativity right }

infix operator -+*  { precedence 70 associativity right }

infix operator -+*{ precedence 70 associativity right }

infix operator  -+* { precedence 70 associativity right }
```
