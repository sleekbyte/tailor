func simpleMax<T: Comparable>(x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}

func simpleMaxWithWhitespace < T: Comparable >(x: T, _ y: T) -> T {
    if x < y {
        return y
    }
    return x
}

func allItemsMatch<
    C1: Container, C2: Container
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable >
    (someContainer: C1, _ anotherContainer: C2) -> Bool {}

func allItemsMatch < C1: Container, C2: Container
    where C1.ItemType == C2.ItemType, C1.ItemType: Equatable>
    (someContainer: C1, _ anotherContainer: C2) -> Bool {}


func <<<T>(inout left: [T], right: [T]) -> [T] {
    left.extend(right)
    return left
}

func << <T>(inout left: [T], right: [T]) -> [T] {
    left.extend(right)
    return left
}

func <|< <A>(lhs: A, rhs: A) -> A
