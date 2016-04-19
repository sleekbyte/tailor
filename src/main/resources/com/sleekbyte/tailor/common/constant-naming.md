### [constant-naming](https://github.com/sleekbyte/tailor/issues/12)
Global constants should follow either *UpperCamelCase* or *lowerCamelCase* naming conventions. Local constants should follow *lowerCamelCase* naming conventions.

*Preferred*
```swift
let MaxHeight = 42
let maxHeight = 42
```

*Not Preferred*
```swift
let max_height = 42
```
