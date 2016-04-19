### [parentheses-whitespace](https://github.com/sleekbyte/tailor/issues/87)
There should be no whitespace immediately before/after an opening parenthesis `(` and before the closing parenthesis `)`.

#### Functions
*Preferred*
```swift
func sum(a: Int, b: Int) -> Int {
  return a + b;
}

print("Hello, World!")
```

*Not Preferred*
```swift
func sum ( a: Int, b: Int ) -> Int {
  return a + b;
}

print( "Hello, World!" )
```

#### Tuples
*Preferred*
```swift
let tuple = (5, 2)
```

*Not Preferred*
```swift
let tuple = ( 5, 2 )
```

#### Conditionals
*Preferred*
```swift
if (someCondition) {
  ...
}
```

*Not Preferred*
```swift
if ( someCondition ) {
  ...
}
```

#### Initializers
*Preferred*
```swift
class SomeClass {
  init() {
  }
}
```

*Not Preferred*
```swift
class SomeClass {
  init ( ) {
  }
}
```
