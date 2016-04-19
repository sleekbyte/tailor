### [colon-whitespace](https://github.com/sleekbyte/tailor/issues/89)

There should be no whitespace preceding the colon, exactly one whitespace after the colon for:
* [`var`](#variable-declarations), [`class`](#class-struct-protocol-and-extension-declarations), [`struct`](#class-struct-protocol-and-extension-declarations), [`protocol`](#class-struct-protocol-and-extension-declarations), [`extension`](#class-struct-protocol-and-extension-declarations), [`func`](#function-declarations), and [`tuple`](#tuple-declarations) declarations
* [`dict`](#dictionary-literals-and-types) literals and types
* [`case`](#case-statements) statements

However, for [conditional expressions](#conditional-expressions) there should be a single whitespace before and after the colon.
#### Variable declarations

*Preferred*

```
var x: Int = 2
```

*Not Preferred*

```
var x : Int
var y:   String
```

#### Dictionary literals and types

*Preferred*

```
var x = [ 'key1': 1, 'key2': 2 ]
var y: [ Int: String ]
```

*Not Preferred*

```
var x = [ 'key1' : 1, 'key2':  3]
var y: [ Int :    String ]
```

#### Case statements

*Preferred*

```
switch character {
case "a": doSomething(a);
default: alert();
}
```

*Not Preferred*

```
switch character {
case "a" : doSomething(a);
default:     alert();
}
```

#### Class, Struct, Protocol, and Extension declarations

*Preferred*

```
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

```
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

```
var y = (key: 1, value: 2)
```

*Not Preferred*

```
var y = (key:1, value : 2)
```

#### Function declarations

*Preferred*

```
func someFunction<T: SomeClass, U: SomeProtocol>(someT: T, someU: U) {
}
```

*Not Preferred*

```
func someFunction<T : SomeClass, U:SomeProtocol>(someT: T, someU: U) {
}
```

#### Conditional expressions

*Preferred*

```
var x = condition ? a : b
```

*Not Preferred*

```
var x = condition ? a: b
var x = condition ? a   : b
```
