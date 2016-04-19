### [upper-camel-case](https://github.com/sleekbyte/tailor/issues/8)
[`class`](#class-names), [`enum`](#enumeration-types), [enum value](#enumeration-values), [`struct`](#struct-names), and [`protocol`](#protocol-names) names should follow *UpperCamelCase* naming convention : several words are joined together, and the first letter of every word is capitalized.

#### Class names

*Preferred*
```swift
class SomeClass {
	// class definition goes here
}

class SomeClass : SomeSuperClass {
	// class definition goes here
}
```
*Not Preferred*
```swift
class invalidClassName {
	// class definition goes here
}

class inval1dCla$$Name : SomeSuperClass {
	// class definition goes here
}
```
#### Enumeration types

*Preferred*
```swift
enum SomeEnumeration {
	// enumeration definition goes here
}
```
*Not Preferred*
```swift
enum S0me_Enumer4t!on {
	// enumeration definition goes here
}
```

#### Enumeration values

*Preferred*
```swift
enum CompassPoint {
	case North
	case South
	case East
	case West
}
```

*Not Preferred*
```swift
enum CompassPoint {
	case N0rth
	case Sou-th
	case EAST
	case We_$t
}
```

#### Struct names

*Preferred*
```swift
struct SomeStructure {
	// structure definition goes here
}
```
*Not Preferred*
```swift
struct Some-Structure {
	// structure definition goes here
}
```

#### Protocol names

*Preferred*
```swift
protocol SomeProtocol {
	// protocol definition goes here
}
```
*Not Preferred*
```swift
protocol someprotocol {
	// protocol definition goes here
}
```
