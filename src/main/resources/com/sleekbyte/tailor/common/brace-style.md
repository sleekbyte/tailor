### [brace-style](https://github.com/sleekbyte/tailor/issues/86)
Definitions of
- `class`
- `struct`
- `function`
- Control flow constructs (`if`, `else if`, `else`, `switch`, `for`, `while`, `repeat-while`)
- `init`
- `protocol`
- `enum`
- `closure`
- Getters and Setters (`set`, `get`)
- `extension`

should follow the [One True Brace Style (1TBS)](https://en.wikipedia.org/wiki/Indent_style#Variant:_1TBS):
each construct has its opening brace one the same line along with the same indentation level as its header, the statements within the braces are indented, and the closing brace at the end is on the same indentation level as the header of the function at a line of its own. Braces are not omitted for a control statement with only a single statement in its scope. Every opening brace must also have one space preceding it.

#### Classes

*Preferred*

```
class SomeClass {
}

class SomeClass: SomeSuperClass {
}
```

*Not Preferred*

```
class SomeClass
{
}

class SomeClass: SomeSuperClass{
}
```

#### Structs

*Preferred*

```
struct SomeStruct {
}

struct SomeStruct : SomeParentStruct {
}
```

*Not Preferred*

```
struct SomeStruct
{
}

struct SomeStruct : SomeParentStruct  {
}
```

#### Functions

*Preferred*

```
func someMethod() {
}

func someOtherFunction () -> () {
}
```

*Not Preferred*

```
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

```
if SomeCondition {

} else if someOtherCondition {
} else {
}
```

*Not Preferred*

```
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

```
switch SomeData {
	default:
		break
}
```

*Not Preferred*

```
switch SomeData
{
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
for var i = 0; i < 10; i+=1
{

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
while SomeCondition
{

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
repeat
{

} while SomeCondition
```

#### Initializers

*Preferred*

```
init(someParameter:Double, someOtherParameter:Double) {
   self.someMember = someParameter
   self.someOtherMember = someOtherParameter
}
```

*Not Preferred*

```
init(someParameter:Double, someOtherParameter:Double)
{
   self.someMember = someParameter
   self.someOtherMember = someOtherParameter
}
```

#### Protocols

*Preferred*

```
protocol SomeProtocol {

}

protocol SomeOtherProtocol : X {

}
```

*Not Preferred*

```
protocol SomeProtocol
{

}

protocol SomeOtherProtocol : X
{
}

```

#### Enums

*Preferred*

```
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

```
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

```
func someFunction () -> () {
// closure
}
```

*Not Preferred*

```
func someFunction () -> ()
{
// closure
}
```

#### Setters and Getters

- set

*Preferred*

```
set {
    oldValue = newValue / 2
}
```

*Not Preferred*

```
set
{
    oldValue = newValue / 2
}
```

- get

*Preferred*

```
get {
    return value * 2
}
```

*Not Preferred*

```
get
{
    return value * 2
}
```

#### Extensions

*Preferred*

```
extension someExtension {
}
```

*Not Preferred*

```
extension someExtension
{
}
```
