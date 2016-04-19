### [arrow-whitespace](https://github.com/sleekbyte/tailor/issues/89)

Prefer a single space before and after '->'.

#### Function and Closure declarations

*Preferred*

```swift
func onePlusTwo() -> Int {
  return 1 + 2
}

names.map() {
  (name) -> Int in
  return 1
}
```

*Not Preferred*

```swift
func onePlusTwo()->Int {
  return 1 + 2
}

names.map() {
  (name)  ->  Int in
  return 1
}

class SomeClass: SomeSuperClass{
}
```

#### Subscript declarations
*Preferred*

```swift
struct TimesTable {
    let multiplier: Int

    subscript(index: Int) -> Int {
        return multiplier * index
    }
}
```

*Not Preferred*

```swift
struct TimesTable {
    let multiplier: Int

    subscript(index: Int)-> Int {
        return multiplier * index
    }
}

struct SomeStruct : SomeParentStruct   {
}
```

#### Function Types

*Preferred*

```swift
func something() -> (Int, Int) -> (Int) {
  // do something
}
```

*Not Preferred*

```swift
func something() -> (Int, Int)-> (Int) {
  // do something
}

func something() -> (Int, Int)  -> (Int){
  // do something
}
```
