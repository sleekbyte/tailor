### [brace-style](https://github.com/sleekbyte/tailor/issues/86)
Definitions of
- [`class`](#classes)
- [`struct`](#structs)
- [`function`](#functions)
- [Control flow constructs](#control-flow-constructs) (`if`, `else if`, `else`, `switch`, `for`, `while`, `repeat-while`)
- [`init`](#initializers)
- [`protocol`](#protocols)
- [`enum`](#enums)
- [`closure`](#closures)
- [Getters and Setters](#getters-and-setters) (`set`, `get`)
- [`extension`](#extensions)

should follow the [One True Brace Style (1TBS)](https://en.wikipedia.org/wiki/Indent_style#Variant:_1TBS):
each construct has its opening brace one the same line along with the same indentation level as its header, the statements within the braces are indented, and the closing brace at the end is on the same indentation level as the header of the function at a line of its own. Braces are not omitted for a control statement with only a single statement in its scope. Every opening brace must also have one space preceding it.

#### Classes

*Preferred*
```swift
class SomeClass {
}

class SomeClass: SomeSuperClass {
}
```
*Not Preferred*
```swift
class SomeClass
{
}

class SomeClass: SomeSuperClass{
}
```

#### Structs

*Preferred*
```swift
struct SomeStruct {
}

struct SomeStruct : SomeParentStruct {
}
```
*Not Preferred*
```swift
struct SomeStruct
{
}

struct SomeStruct : SomeParentStruct  {
}
```

#### Functions

*Preferred*
```swift
func someMethod() {
}

func someOtherFunction () -> () {
}
```

*Not Preferred*
```swift
func someMethod()
{
}

func someOtherFunction () -> ()
{
}
```

#### Control flow constructs

- if, else if, and else statement

*Preferred*
```swift
if SomeCondition {

} else if someOtherCondition {
} else {
}
```

*Not Preferred*
```swift
if SomeCondition
{

}
else if someOtherCondition
{
}
else
{
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
switch SomeData
{
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
for var i = 0; i < 10; i+=1
{

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
while SomeCondition
{

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
repeat
{

} while SomeCondition
```

#### Initializers

*Preferred*
```swift
init(someParameter:Double, someOtherParameter:Double) {
   self.someMember = someParameter
   self.someOtherMember = someOtherParameter
}
```

*Not Preferred*
```swift
init(someParameter:Double, someOtherParameter:Double)
{
   self.someMember = someParameter
   self.someOtherMember = someOtherParameter
}
```

#### Protocols

*Preferred*
```swift
protocol SomeProtocol {

}

protocol SomeOtherProtocol : X {

}
```

*Not Preferred*
```swift
protocol SomeProtocol
{

}

protocol SomeOtherProtocol : X
{
}

```

#### Enums

*Preferred*
```swift
enum SomeEnum {
    case A, B, C, D
}

enum SomeEnum {
    case A
    case B
    case C
    case D
}

enum SomeEnum: Int {
    case A, B, C = 5, D
}
```

*Not Preferred*
```swift
enum SomeEnum
{
    case A, B, C, D
}

enum SomeEnum
{
    case A
    case B
    case C
    case D
}

enum SomeEnum: Int
{
    case A, B, C = 5, D
}
```

#### Closures

*Preferred*
```swift
func someFunction () -> () {
// closure
}
```

*Not Preferred*
```swift
func someFunction () -> ()
{
// closure
}
```

#### Setters and Getters

- set

*Preferred*
```swift
set {
    oldValue = newValue / 2
}
```

*Not Preferred*
```swift
set
{
    oldValue = newValue / 2
}
```

- get

*Preferred*
```swift
get {
    return value * 2
}
```

*Not Preferred*
```swift
get
{
    return value * 2
}
```

#### Extensions

*Preferred*
```swift
extension someExtension {
}
```

*Not Preferred*
```swift
extension someExtension
{
}
```
