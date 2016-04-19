### [angle-bracket-whitespace](https://github.com/sleekbyte/tailor/issues/87)

There should be no whitespace immediately before/after an opening chevron `<` and before the closing chevron `>`.

#### Generics

*Preferred*
```swift
func simpleMax<T: Comparable>(x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}
```

*Not Preferred*
```swift
func simpleMax < T: Comparable >(x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}
```

#### Requirement list

*Preferred*
```swift
func allItemsMatch<
    C1: Container, C2: Container
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable>
    (someContainer: C1, _ anotherContainer: C2) -> Bool {}
```

*Not Preferred*
```swift
func allItemsMatch <
    C1: Container, C2: Container
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable >
    (someContainer: C1, _ anotherContainer: C2) -> Bool {}
```
