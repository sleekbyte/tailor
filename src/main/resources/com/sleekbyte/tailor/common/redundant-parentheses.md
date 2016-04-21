### [redundant-parentheses](https://github.com/sleekbyte/tailor/issues/72)
Control flow constructs (`if`, `else if`, `switch`, `for`, `while`, `repeat-while`, and `guard` statements), Exception handling constructs (`throw`, and `do/catch` statements), and Initializers (`array`, `dictionary`, `initializer patterns`) should not be enclosed in parentheses.

Additionally, method calls with no parameters and a trailing closure should not have empty parentheses following the method name.

#### Control flow constructs

- if, else if statement

*Preferred*

```
if SomeCondition {

} else if SomeOtherCondition {
}
```

*Not Preferred*

```
if (SomeCondition) {

} else if (SomeOtherCondition) {
}
```

- switch statement

*Preferred*

```
switch SomeData {
	default:
		break
}
```

*Not Preferred*

```
switch (SomeData) {
	default:
		break
}
```

- for loop

*Preferred*

```
for var i = 0; i < 10; i+=1 {

}
```

*Not Preferred*

```
for (var i = 0; i < 10; i+=1) {

}
```

- while loop

*Preferred*

```
while SomeCondition {

}
```

*Not Preferred*

```
while (SomeCondition) {

}
```

- repeat-while loop

*Preferred*

```
repeat {

} while SomeCondition
```

*Not Preferred*

```
repeat {

} while (SomeCondition)
```

- guard clause

*Preferred*

```
guard true else {   }
```

*Not Preferred*

```
guard (true) else {   }
```

#### Exception handling constructs
- do/catch statement

*Preferred*

```
do  {

} catch SomeException {

}
```

*Not Preferred*

```
do  {

} catch (SomeException) {

}
```

- throw statement

*Preferred*

```
throw SomeException
```

*Not Preferred*

```
throw (SomeException)
```

#### Initializers

- array items

*Preferred*

```
var shoppingList: [String] = ["Eggs", "Milk"]
```

*Not Preferred*

```
var shoppingList: [String] = [("Eggs"), ("Milk")]
```

- dictionary items

*Preferred*

```
var airports: [String: String] = ["YYZ": "Toronto Pearson", "DUB": "Dublin"]
```

*Not Preferred*

```
var airports: [String: String] = [("YYZ"): ("Toronto Pearson"), ("DUB"): ("Dublin")]
```

- initializer patterns

*Preferred*

```
var x: Int = 2
var y: String = "Sleekbyte"
var x = 2
```

*Not Preferred*

```
var x: Int = (2)
var y: String = ("Sleekbyte")
var x = (2)
```

#### Method calls

*Preferred*

```
items.map {
  item in item.transform()
}
```

*Not Preferred*

```
items.map() {
  item in item.transform()
}
```
