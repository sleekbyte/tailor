### [parentheses-whitespace](https://github.com/sleekbyte/tailor/issues/87)
There should be no whitespace immediately before/after an opening parenthesis `(` and before the closing parenthesis `)`.

#### Functions
*Preferred*

```
func sum(a: Int, b: Int) -> Int {
  return a + b;
}

print("Hello, World!")
```

*Not Preferred*

```
func sum ( a: Int, b: Int ) -> Int {
  return a + b;
}

print( "Hello, World!" )
```

#### Tuples
*Preferred*

```
let tuple = (5, 2)
```

*Not Preferred*

```
let tuple = ( 5, 2 )
```

#### Conditionals
*Preferred*

```
if (someCondition) {
  ...
}
```

*Not Preferred*

```
if ( someCondition ) {
  ...
}
```

#### Initializers
*Preferred*

```
class SomeClass {
  init() {
  }
}
```

*Not Preferred*

```
class SomeClass {
  init ( ) {
  }
}
```
