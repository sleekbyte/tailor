### [terminating-semicolon](https://github.com/sleekbyte/tailor/issues/10)

Swift does not require a semicolon after each statement in your code unless you wish to combine multiple statements on a single line. Do not write multiple statements on a single line separated with semicolons.

#### Imports
*Preferred*

```
import Foundation
```

*Not Preferred*

```
import Foundation;
```


#### Enums and enum cases
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
	case North;
	case South;
	case East;
	case West;
};
```

#### Protocols
*Preferred*

```
protocol SomeProtocol {
	var SomeMethod: String { get }
	func SomeMethod()
	func SomeMethod(f: Int)
	func SomeMethod(bar: String, baz: Double)
}
```

*Not Preferred*

```
protocol SomeProtocol {
	var SomeMethod: String { get };
	func SomeMethod();
	func SomeMethod(f: Int);
	func SomeMethod(bar: String, baz: Double);
};
```

#### Extensions
*Preferred*

```
extension SomeType {

}
```

*Not Preferred*

```
extension SomeType {

};
```

#### Structs
*Preferred*

```
struct DemoStruct {
        var x: String // variables
}
```

*Not Preferred*

```
struct DemoStruct {
        var x: String // variables
};
```

#### Classes
*Preferred*

```
class SomeClass {
	let b = 2 // constants
}
```

*Not Preferred*

```
class SomeClass {
	let b = 2 // constants
};
```

#### Loops
*Preferred*

```
// while loop
while true {

}

// for loop
for ; ; {
}

// repeat while
repeat {

} while true
```

*Not Preferred*

```
// while loop
while true {

};

// for loop
for ; ; {
};

// repeat while
repeat {

} while true;
```
