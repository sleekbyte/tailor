### [terminating-semicolon](https://github.com/sleekbyte/tailor/issues/10)

Swift does not require a semicolon after each statement in your code unless you wish to combine multiple statements on a single line. Do not write multiple statements on a single line separated with semicolons.

#### Imports
*Preferred*
```swift
import Foundation
```

*Not Preferred*
```swift
import Foundation;
```


#### Enums and enum cases
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
	case North;
	case South;
	case East;
	case West;
};
```

#### Protocols
*Preferred*
```swift
protocol SomeProtocol {
	var SomeMethod: String { get }
	func SomeMethod()
	func SomeMethod(f: Int)
	func SomeMethod(bar: String, baz: Double)
}
```

*Not Preferred*
```swift
protocol SomeProtocol {
	var SomeMethod: String { get };
	func SomeMethod();
	func SomeMethod(f: Int);
	func SomeMethod(bar: String, baz: Double);
};
```

#### Extensions
*Preferred*
```swift
extension SomeType {

}
```

*Not Preferred*
```swift
extension SomeType {

};
```

#### Structs
*Preferred*
```swift
struct DemoStruct {
        var x: String // variables
}
```

*Not Preferred*
```swift
struct DemoStruct {
        var x: String // variables
};
```

#### Classes
*Preferred*
```swift
class SomeClass {
	let b = 2 // constants
}
```

*Not Preferred*
```swift
class SomeClass {
	let b = 2 // constants
};
```

#### Loops
*Preferred*
```swift
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
```swift
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
