### [colon-whitespace](https://github.com/sleekbyte/tailor/issues/89)

There should be no whitespace preceding the colon, exactly one whitespace after the colon for:
* [`var`](#variable-declarations), [`class`](#class-struct-protocol-and-extension-declarations), [`struct`](#class-struct-protocol-and-extension-declarations), [`protocol`](#class-struct-protocol-and-extension-declarations), [`extension`](#class-struct-protocol-and-extension-declarations), [`func`](#function-declarations), and [`tuple`](#tuple-declarations) declarations
* [`dict`](#dictionary-literals-and-types) literals and types
* [`case`](#case-statements) statements

However, for [conditional expressions](#conditional-expressions) there should be a single whitespace before and after the colon.
#### Variable declarations

*Preferred*
```swift
var x: Int = 2
```
*Not Preferred*
```swift
var x : Int
var y:   String
```
#### Dictionary literals and types

*Preferred*
```swift
var x = [ 'key1': 1, 'key2': 2 ]
var y: [ Int: String ]
```

*Not Preferred*
```swift
var x = [ 'key1' : 1, 'key2':  3]
var y: [ Int :    String ]
```

#### Case statements

*Preferred*
```swift
switch character {
case "a": doSomething(a);
default: alert();
}
```

*Not Preferred*
```swift
switch character {
case "a" : doSomething(a);
default:     alert();
}
```

#### Class, Struct, Protocol, and Extension declarations

*Preferred*
```swift
class ClassName: BaseClass {
}

struct StructName: BaseStruct {
}

protocol ProtocolName: AnotherProtocol {
}

extension TypeName: ProtocolName {
}
```

*Not Preferred*
```swift
class ClassName : BaseClass {
}

struct StructName:  BaseStruct {
}

protocol ProtocolName:AnotherProtocol {
}

extension TypeName : ProtocolName {
}
```

#### Tuple declarations

*Preferred*
```swift
var y = (key: 1, value: 2)
```

*Not Preferred*
```swift
var y = (key:1, value : 2)
```

#### Function declarations

*Preferred*
```swift
func someFunction<T: SomeClass, U: SomeProtocol>(someT: T, someU: U) {
}
```

*Not Preferred*
```swift
func someFunction<T : SomeClass, U:SomeProtocol>(someT: T, someU: U) {
}
```

#### Conditional expressions

*Preferred*
```swift
var x = condition ? a : b
```

*Not Preferred*
```swift
var x = condition ? a: b
var x = condition ? a   : b
```
