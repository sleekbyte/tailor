### [leading-whitespace](https://github.com/sleekbyte/tailor/issues/115)
Verify that source files begin with a non-whitespace character.

*Preferred*
```swift
1 import Foundation
```

*Not Preferred*
```swift
1 ¬
2 import Foundation
```
```swift
1 ••import Foundation
```
