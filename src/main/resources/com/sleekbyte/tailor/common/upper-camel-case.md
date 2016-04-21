### [upper-camel-case](https://github.com/sleekbyte/tailor/issues/8)
`class`, `enum`, enum value, `struct`, and `protocol` names should follow *UpperCamelCase* naming convention : several words are joined together, and the first letter of every word is capitalized.

#### Class names

*Preferred*

```
class SomeClass {
	// class definition goes here
}

class SomeClass : SomeSuperClass {
	// class definition goes here
}
```

*Not Preferred*

```
class invalidClassName {
	// class definition goes here
}

class inval1dCla$$Name : SomeSuperClass {
	// class definition goes here
}
```

#### Enumeration types

*Preferred*

```
enum SomeEnumeration {
	// enumeration definition goes here
}
```

*Not Preferred*

```
enum S0me_Enumer4t!on {
	// enumeration definition goes here
}
```

#### Enumeration values

*Preferred*

```
enum CompassPoint {
	case North
	case South
	case East
	case West
}
```

*Not Preferred*

```
enum CompassPoint {
	case N0rth
	case Sou-th
	case EAST
	case We_$t
}
```

#### Struct names

*Preferred*

```
struct SomeStructure {
	// structure definition goes here
}
```

*Not Preferred*

```
struct Some-Structure {
	// structure definition goes here
}
```

#### Protocol names

*Preferred*

```
protocol SomeProtocol {
	// protocol definition goes here
}
```

*Not Preferred*

```
protocol someprotocol {
	// protocol definition goes here
}
```
