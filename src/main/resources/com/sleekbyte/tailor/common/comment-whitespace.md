### [comment-whitespace](https://github.com/sleekbyte/tailor/issues/89)

Prefer _at least one_ whitespace character after a comment opening symbol (`//`, `///`, `/*`, or `/**`) and _at least one_ whitespace character before a comment closing symbol (`*/`).

*Preferred*

```
// This is a comment

/// This is a documentation comment

/* This is a
multi-line comment */

/* This is a
multi-line comment
*/

/** This is a
documentation multi-line
comment
*/
```

*Not Preferred*

```
//This is a comment

///This is a documentation comment

/*This is a
multi-line comment*/

/**This is a multi-line
documentation comment */
```
