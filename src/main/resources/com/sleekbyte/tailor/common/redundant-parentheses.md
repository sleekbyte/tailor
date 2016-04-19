### [redundant-parentheses](https://github.com/sleekbyte/tailor/issues/72)
[Control flow constructs](#control-flow-constructs) (`if`, `else if`, `switch`, `for`, `while`, `repeat-while`, and `guard` statements), [Exception handling constructs](#exception-handling-constructs) (`throw`, and `do/catch` statements), and [Initializers](#initializers) (`array`, `dictionary`, `initializer patterns`) should not be enclosed in parentheses.

Additionally, method calls with no parameters and a trailing closure should not have empty parentheses following the method name.

#### Control flow constructs

- if, else if statement

*Preferred*
```swift
if SomeCondition {

} else if SomeOtherCondition {
}
```

*Not Preferred*
```swift
if (SomeCondition) {

} else if (SomeOtherCondition) {
}
```

- switch statement

*Preferred*
```swift
switch SomeData {
	default:
		break
}
```

*Not Preferred*
```swift
switch (SomeData) {
	default:
		break
}
```

- for loop

*Preferred*
```swift
for var i = 0; i < 10; i+=1 {

}
```

*Not Preferred*
```swift
for (var i = 0; i < 10; i+=1) {

}
```

- while loop

*Preferred*
```swift
while SomeCondition {

}
```

*Not Preferred*
```swift
while (SomeCondition) {

}
```

- repeat-while loop

*Preferred*
```swift
repeat {

} while SomeCondition
```

*Not Preferred*
```swift
repeat {

} while (SomeCondition)
```

- guard clause

*Preferred*
```swift
guard true else {   }
```

*Not Preferred*
```swift
guard (true) else {   }
```

#### Exception handling constructs
- do/catch statement

*Preferred*
```swift
do  {

} catch SomeException {

}
```

*Not Preferred*
```swift
do  {

} catch (SomeException) {

}
```

- throw statement

*Preferred*
```swift
throw SomeException
```

*Not Preferred*
```swift
throw (SomeException)
```

#### Initializers

- array items

*Preferred*
```swift
var shoppingList: [String] = ["Eggs", "Milk"]
```

*Not Preferred*
```swift
var shoppingList: [String] = [("Eggs"), ("Milk")]
```

- dictionary items

*Preferred*
```swift
var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin"]
```

*Not Preferred*
```swift
var airports: [String: String] = [("YYZ"): ("Toronto Pearson"), ("DUB"): ("Dublin")]
```

- initializer patterns

*Preferred*
```swift
var x: Int = 2
var y: String = "Sleekbyte"
var x = 2
```

*Not Preferred*
```swift
var x: Int = (2)
var y: String = ("Sleekbyte")
var x = (2)
```

#### Method calls

*Preferred*
```swift
items.map {
  item in item.transform()
}
```

*Not Preferred*
```swift
items.map() {
  item in item.transform()
}
```
